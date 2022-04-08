package org.hbs.gaya.controllers;

import org.hbs.gaya.bo.InventoryBo;
import org.hbs.gaya.model.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MaterialInventoryController
{
	@Autowired
	InventoryBo inventoryBo;

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

	@GetMapping("/searchInventory")
	public String search(Model model)
	{
		if (model != null)
			model.addAttribute("inventoryList", inventoryBo.searchInventory(String.valueOf(model.getAttribute("search"))));

		return "inventory_search";
	}

	@GetMapping("/updateInventory")
	public String updateInventory(Model model)
	{
		if (model != null)
		{	try
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
