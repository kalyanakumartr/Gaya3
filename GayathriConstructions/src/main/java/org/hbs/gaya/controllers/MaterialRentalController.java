package org.hbs.gaya.controllers;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hbs.gaya.bo.InventoryBo;
import org.hbs.gaya.bo.MaterialBo;
import org.hbs.gaya.bo.RentalBo;
import org.hbs.gaya.dao.CustomerDao;
import org.hbs.gaya.dao.RentalItemDao;
import org.hbs.gaya.dao.SequenceDao;
import org.hbs.gaya.model.Customer;
import org.hbs.gaya.model.Inventory;
import org.hbs.gaya.model.Rental;
import org.hbs.gaya.model.Rental.ERentalStatus;
import org.hbs.gaya.model.RentalInvoice;
import org.hbs.gaya.model.RentalInvoice.EInvoiceStatus;
import org.hbs.gaya.model.RentalItem;
import org.hbs.gaya.model.Users;
import org.hbs.gaya.util.CommonValidator;
import org.hbs.gaya.util.ConstUtil;
import org.hbs.gaya.util.DataTableParam;
import org.hbs.gaya.util.LabelValueBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Controller
public class MaterialRentalController implements Serializable
{
	private static final long	serialVersionUID	= 4326256676370001415L;

	@Autowired
	CustomerDao					customerDao;

	private DateTimeFormatter	dtFormat			= DateTimeFormatter.ofPattern("dd-MMM-yyyy hh:mm a");
	@Autowired
	InventoryBo					inventoryBo;

	@Autowired
	Jackson2ObjectMapperBuilder	mapperBuilder;

	@Autowired
	MaterialBo					materialBo;

	@Autowired
	RentalBo					rentalBo;

	@Autowired
	SequenceDao					sequenceDao;

	@Autowired
	RentalItemDao				rentalItemDao;

	@PostMapping(value = "/removeItemsFromRental/{rentalId}/{itemId}")
	public String removeItemsFromRental(ModelMap modal, @PathVariable("rentalId") String rentalId, @PathVariable("itemId") String itemId)
	{
		Rental rental = rentalBo.getRentalById(rentalId);
		if (CommonValidator.isNotNullNotEmpty(itemId))
		{
			RentalInvoice rentalInvoice = rental.getActiveInvoice();
			if (CommonValidator.isNotNullNotEmpty(rentalInvoice))
			{
				Optional<RentalItem> itemOpt = rentalInvoice.getItemSet().stream().filter(p -> p.getItemId().equals(itemId)).findFirst();

				if (itemOpt.isPresent())
				{
					int balanceQty = itemOpt.get().getQuantity();
					String inventoryId = itemOpt.get().getInventory().getInventoryId();
					rentalInvoice.getItemSet().remove(itemOpt.get());
					rental = rentalBo.saveOrUpdate(rental);

					try
					{
						Inventory inventory = inventoryBo.getInventory(inventoryId);
						inventory.setAvailableQuantity(inventory.getAvailableQuantity() + balanceQty);
						inventoryBo.save(inventory);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}

			}
		}
		return getRentedItems(modal, rental);
	}

	@PostMapping(value = "/finalizeRental")
	@ResponseBody
	public String finalizeRental(@RequestBody Map<String, String> payload)
	{
		if (CommonValidator.isNotNullNotEmpty(payload.get("rentalId")))
		{
			Rental rental = rentalBo.getRentalById(payload.get("rentalId"));
			rental.setAdvanceAmount(Double.parseDouble(payload.get("advanceAmount")));
			rental.setRentalStatus(ERentalStatus.Rented);
			rental.getActiveInvoice().setInvoiceStatus(EInvoiceStatus.Pending);
			rental.getActiveInvoice().setStartDate(LocalDateTime.now());
			rentalBo.saveOrUpdate(rental);
			return ConstUtil.SUCCESS;
		}
		return ConstUtil.ERROR;
	}

	@PostMapping(value = "/addRentals")
	public String addRentals(HttpSession session, ModelMap modal, @RequestBody Map<String, String> payload) throws Exception
	{
		Rental rental;
		RentalInvoice rentalInvoice;

		if (CommonValidator.isNotNullNotEmpty(payload.get("rentalId")))
		{
			rental = rentalBo.getRentalById(payload.get("rentalId"));
			rentalInvoice = rental.getActiveInvoice();
			
			Double price = Double.parseDouble(payload.get("price"));
			Integer quantity = Integer.parseInt(payload.get("quantity"));

			Inventory inventory = inventoryBo.getInventoryByMaterial(payload.get("materialId"));
			inventory.setRentedQuantity(inventory.getRentedQuantity() + quantity);
			inventory.setAvailableQuantity(inventory.getAvailableQuantity() - quantity);

			rentalInvoice.getItemSet().add(RentalItem.builder()//
					.itemId(sequenceDao.create("RentalItem"))//
					.rental(rental).rentalInvoice(rentalInvoice).price(price)//
					.quantity(quantity)//
					.balanceQuantity(quantity)//
					.inventory(inventory)//
					.build());
			rental.setAdvanceAmount(Double.parseDouble(payload.get("advanceAmount")));
		}
		else
		{
			rental = Rental.builder()//
					.advanceAmount(0.0)//
					.rentalId(sequenceDao.create("Rental"))//
					.rentalStatus(ERentalStatus.None)//
					.closureStatus(ERentalStatus.None)//
					.rentedDate(LocalDateTime.now())//
					.customer(Customer.builder().customerId(payload.get("customerId")).build())//
					.createdDate(LocalDateTime.now())//
					.createdUser(Users.builder().employeeId("System").build()).build(); //(String) session.getAttribute("employeeId")

			rentalInvoice = RentalInvoice.builder()//
					.invoiceId(sequenceDao.create("RentalInvoice"))//
					.startDate(LocalDateTime.now())//
					.calculatedDate(LocalDateTime.now())//
					.active(true)//
					.invoiceStatus(EInvoiceStatus.None)//
					.rental(rental).build();
			
			rentalInvoice.setMasterInvoice(rentalInvoice);
			rental.getRentalInvoiceSet().add(rentalInvoice);
		}

		rental = rentalBo.saveOrUpdate(rental);

		return getRentedItems(modal, rental);
	}

	@GetMapping(value = "/failure-dashboard")
	@ResponseBody
	public String failurePage()
	{

		return ConstUtil.FAILURE;
	}

	@PostMapping(value = { "/getRentalMaterials", "/getRentalMaterials/{rentalId}" })
	public String getRentalMaterials(ModelMap modal, @PathVariable(name = "rentalId", required = false) String rentalId)
	{
		Rental rental = new Rental();
		if (CommonValidator.isNotNullNotEmpty(rentalId))
		{
			rental = rentalBo.getRentalById(rentalId);
		}

		return getRentedItems(modal, rental);
	}

	private String getRentedItems(ModelMap modal, Rental rental)
	{
		RentalInvoice rentalInvoice = rental.getActiveInvoice();

		List<String> matIdList = new ArrayList<>();
		if (CommonValidator.isNotNullNotEmpty(rentalInvoice))
		{
			matIdList = rentalInvoice.getItemSet().stream().map(p -> p.getInventory().getMaterial().getMaterialId()).collect(Collectors.toList());
			modal.addAttribute("items", rentalInvoice.getItemSet());
			modal.addAttribute("itemsTotalCost$", ConstUtil.getCurrency(rentalInvoice.getItemsTotalCost()));
		}

		List<LabelValueBean> lvBeanList = new ArrayList<LabelValueBean>();
		String value = "";
		String label = "";
		lvBeanList.add(new LabelValueBean("", "Select Material"));
		for (Inventory inventory : inventoryBo.getInventoryList())
		{
			if (!matIdList.contains(inventory.getMaterial().getMaterialId()))
			{
				value = inventory.getMaterial().getMaterialId() + "|" + inventory.getAvailableQuantity() + "|" + inventory.getRentalItemCost();
				label = inventory.getMaterial().getMaterialName() + " (" + inventory.getAvailableQuantity() + ")";

				lvBeanList.add(new LabelValueBean(value, label));
			}
		}

		modal.addAttribute("materialList", lvBeanList);
		modal.addAttribute("rentalId", rental.getRentalId());
		modal.addAttribute("advanceAmount", rental.getAdvanceAmount());
		modal.addAttribute("customerName", rental.getCustomer().getCustomerName());
		modal.addAttribute("mobileNo", rental.getCustomer().getMobileNo());
		modal.addAttribute("disableAdd", rental.getRentalStatus() != ERentalStatus.None);

		return "fragments/add_rental_material";
	}

	@PostMapping("/searchRental/{path}")
	@ResponseBody
	public String search(HttpServletRequest request, @PathVariable("path") Integer path)
	{
		String data = "";

		try
		{
			DataTableParam dtParam = DataTableParam.init(request);

			List<Rental> dataList = rentalBo.searchRental(dtParam.getGeneralSearch() == null ? "" : dtParam.getGeneralSearch(), path == 0);
			ObjectMapper o = mapperBuilder.build();
			o.registerModule(new JavaTimeModule());
			data = o.writeValueAsString(dataList);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return data;
	}

	@GetMapping(value = "/success-dashboard")
	@ResponseBody
	public String successPage()
	{

		return ConstUtil.SUCCESS;
	}

	@GetMapping(value = "/dashboard")
	public String viewDashBoardPage()
	{
		return "dashboard";
	}

	@PostMapping(value = "/viewRentalReceipt/{rentalId}")
	public String viewRentalReceipt(ModelMap modal, @PathVariable("rentalId") String rentalId)
	{

		Rental rental = rentalBo.getRentalById(rentalId);

		modal.addAttribute("items", rental.getBaseItemSet());
		modal.addAttribute("advanceAmount$", ConstUtil.getCurrency(rental.getAdvanceAmount()));
		modal.addAttribute("totalAmount$", rental.getItemsTotalAmount$());
		modal.addAttribute("customer", rental.getCustomer());
		modal.addAttribute("rentedDate", rental.getRentedDate().format(dtFormat));
		modal.addAttribute("receiptDate", LocalDateTime.now().format(dtFormat));

		return "fragments/receipt";
	}

}
