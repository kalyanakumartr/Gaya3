package org.hbs.gaya.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.hbs.gaya.bo.InventoryBo;
import org.hbs.gaya.bo.RentalBo;
import org.hbs.gaya.dao.MaterialDao;
import org.hbs.gaya.dao.RentalItemDao;
import org.hbs.gaya.model.Inventory;
import org.hbs.gaya.model.Rental;
import org.hbs.gaya.model.RentalItem;
import org.hbs.gaya.model.RentalItemHistory;
import org.hbs.gaya.util.ConstUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class MaterialInventoryController
{
	@Autowired
	InventoryBo		inventoryBo;

	@Autowired
	MaterialDao		matDao;

	@Autowired
	RentalBo		rentalBo;

	@Autowired
	RentalItemDao	itemDao;

	@PostMapping(value = "/viewReturnItems/{rentalId}")
	public String viewReturnItems(ModelMap modal, @PathVariable("rentalId") String rentalId)
	{

		Rental rental = rentalBo.getRentalById(rentalId);

		modal.addAttribute("items", rental.getActiveInvoice().getItemSet());
		modal.addAttribute("customer", rental.getCustomer());
		modal.addAttribute("rentalId", rentalId);

		return "fragments/return_items";
	}

	@PostMapping(value = "/returnItems", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String returnItems(@RequestBody Map<String, String> payload)
	{
		Optional<RentalItem> itemOpt;
		RentalItem item;
		Integer itemReturnCount = 0;
		for (Entry<String, String> entry : payload.entrySet())
		{
			itemOpt = itemDao.findById(entry.getKey());
			itemReturnCount = Integer.parseInt(entry.getValue());
			if (itemOpt.isPresent())
			{
				item = itemOpt.get();
				item.setBalanceQuantity(item.getBalanceQuantity() - itemReturnCount );
				item.getInventory().setAvailableQuantity(item.getInventory().getAvailableQuantity() + itemReturnCount );
				item.getInventory().setRentedQuantity(item.getInventory().getRentedQuantity() - itemReturnCount);
				item.getRentalItemHistorys().add(RentalItemHistory.builder()
					.item(item)
					.quantity(itemReturnCount)
					.returnDate(LocalDateTime.now())
					.build());
				itemDao.save(item);
			}
		}
		return ConstUtil.SUCCESS;

	}

	@GetMapping(value = "/inventory")
	public String viewInventoryPage()
	{
		return "inventory";
	}

	@GetMapping(value = "/addInventory")
	public String viewAddInventoryPage()
	{
		return "add-inventory";
	}

	@PostMapping("/addInventory")
	public String add(Inventory inventory)
	{
		inventoryBo.save(inventory);
		return "inventory_search";
	}

	@PostMapping("/searchInventory")
	@ResponseBody
	public String search()
	{
		try
		{
			List<Inventory> dataList = inventoryBo.searchInventory("%");
			return new ObjectMapper().writeValueAsString(dataList);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}

	@GetMapping("/updateInventory")
	public String updateInventory(Model model)
	{
		if (model != null)
		{
			try
			{
				model.addAttribute("inventory", inventoryBo.getInventory(String.valueOf(model.getAttribute("inventoryId"))));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return "inventory_search";
	}
}
