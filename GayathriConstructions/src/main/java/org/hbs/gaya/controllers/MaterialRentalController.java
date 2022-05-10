package org.hbs.gaya.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hbs.gaya.bo.InventoryBo;
import org.hbs.gaya.bo.MaterialBo;
import org.hbs.gaya.bo.RentalBo;
import org.hbs.gaya.dao.CustomerDao;
import org.hbs.gaya.dao.UsersDao;
import org.hbs.gaya.model.Customer;
import org.hbs.gaya.model.Material;
import org.hbs.gaya.model.Rental;
import org.hbs.gaya.model.Rental.ERentalStatus;
import org.hbs.gaya.model.RentalInvoice;
import org.hbs.gaya.model.RentalItem;
import org.hbs.gaya.util.CommonValidator;
import org.hbs.gaya.util.DataTableParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Controller
public class MaterialRentalController
{
	private DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("dd-MMM-yyyy hh:mm a");
	private DateTimeFormatter dFormat = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
	
	@Autowired
	RentalBo rentalBo;
	
	@Autowired
	MaterialBo materialBo;
	
	@Autowired
	InventoryBo inventoryBo;
	
	@Autowired
	private UsersDao usersDao;
	
	@Autowired
	CustomerDao customerDao;
	
	@Autowired
	Jackson2ObjectMapperBuilder mapperBuilder;
	
	@GetMapping(value = "/success-dashboard")
	@ResponseBody
	public String successPage()
	{

		return "success";
	}

	@GetMapping(value = "/failure-dashboard")
	@ResponseBody
	public String failurePage()
	{

		return "failure";
	}

	@PostMapping(value = "/viewRentalReceipt/{rentalId}")
	public String viewRentalReceipt(ModelMap modal, @PathVariable("rentalId") String rentalId)
	{
		
		Rental rental = rentalBo.getRentalById(rentalId);
		
		modal.addAttribute("items", rental.getRentalItemSet());
		modal.addAttribute("advanceAmount$", rental.getAdvanceAmount$());
		modal.addAttribute("totalAmount$", rental.getTotalAmount$());
		modal.addAttribute("customer", rental.getCustomer());
		modal.addAttribute("rentedDate", rental.getRentedDate().format(dtFormat));
		modal.addAttribute("receiptDate", LocalDateTime.now().format(dtFormat));

		return "fragments/receipt";
	}
	
	@GetMapping(value = "/dashboard")
	public String viewDashBoardPage()
	{
		return "dashboard";
	}

	@PostMapping("/searchRental")
	@ResponseBody
	public String search(HttpServletRequest request)
	{
		String data = "";
		
			try
			{
				DataTableParam dtParam = DataTableParam.init(request);
				
				System.out.println("GS : "+dtParam.getGeneralSearch());
				List<Rental> dataList = rentalBo.searchRental(dtParam.getGeneralSearch()== null?"":dtParam.getGeneralSearch());
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
	@PostMapping(value = "/addRentals")
	public String addRental(ModelMap modal,@Param("customerId") String customerId, @Param("rentalId") String rentalId, @Param("material") String material,@Param("rentPerUnit") String rentPerUnit,@Param("noOfUnits") String noOfUnits,@Param("total") String total) throws Exception
	{
		System.out.println(rentalId+"rentalId"+material+"material"+rentPerUnit+"rentPerUnit"+noOfUnits+"noOfUnits"+total+"total");
		RentalItem rentalItem = new RentalItem();
		rentalItem.setItemId(material);
		rentalItem.setPrice(Double.parseDouble(rentPerUnit));
		rentalItem.setQuantity(Integer.parseInt(noOfUnits));
		rentalItem.setTotalCost(Double.parseDouble(total));
		rentalItem.setInventory(inventoryBo.getInventoryByMaterial(material));
		
		Rental rental = new Rental();
		if(rentalId != null && rentalId !="" && rentalId !="0") {
			rental = rentalBo.getRentalById(rentalId);
		}else {
			String lastRentalId = rentalBo.getLastRentalId();
			rentalId = getNextID(lastRentalId);
			rental.setRentalId(rentalId);
		}

        Set rentalItemSet = new HashSet();
        rentalItem.setRental(rental);
        rentalItemSet.add(rentalItem);
		rental.setRentalItemSet(rentalItemSet);
		rental.setCreatedUser(usersDao.getLoginDetails("1234567890").get(0));
		rental.setCustomer(customerDao.getById(customerId));
		rental.setRentalStatus(ERentalStatus.Rented);
		rentalBo.saveOrUpdate(rental);
		
		getRentedItems(modal, rentalId);
		return "fragments/addrentmaterial";
	}
	@GetMapping(value = "/showCustomer")
	public String showCustomer()
	{
	
		return "addRentals";
	}
	@PostMapping(value = "/addCustomer" , produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String addMaterialPage( Customer customer)
	{
		System.out.println(customer+"customer"+ customer.getCustomerName());

		customerDao.save(customer);
		return "addRental";	
	}
	
	@PostMapping(value = "/getRentalMaterials/{rentalId}")
	public String getRentalMaterials(ModelMap modal, @PathVariable("rentalId") String rentalId)
	{
		getRentedItems(modal, rentalId);
		
		return "fragments/addrentmaterial";
	}
	

	private void getRentedItems(ModelMap modal, String rentalId) {
		Rental rental = new Rental();
		if(rentalId != null) {
			rental = rentalBo.getRentalById(rentalId);
			modal.addAttribute("items", rental.getRentalItemSet());
			// double totalRentAmount = 0.0;
			double totalCostAmount = 0.0;

			for (RentalItem rItem : rental.getRentalItemSet())
			{
				totalCostAmount = totalCostAmount + rItem.getTotalCost();
			}

			modal.addAttribute("totalCostAmount$", rental.getCurrency(totalCostAmount));
			
		}else {
			String lastId = rentalBo.getLastRentalId();
			lastId = lastId+1;
			rental.setRentalId(lastId);
			rentalId = lastId;
		}
		List<Material> dataList = materialBo.getAllMaterials();

		modal.addAttribute("rentalId$", rentalId);
		modal.addAttribute("materialList", dataList);
	}
	
	String getNextID(String lastId){
		String initialChar = lastId.substring(0, 3);
		int num = Integer.parseInt(lastId.substring(4, 7));
		num = num+1;
		String numStr =num+"";
		String numOfZero ="";
		for(int i=0;i<=(3 -numStr.length());i++) {
			numOfZero=numOfZero+'0';
		}
		String nextId = initialChar+numOfZero+num;
		return nextId;
	}
	
}
