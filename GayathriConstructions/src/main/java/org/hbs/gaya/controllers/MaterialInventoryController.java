package org.hbs.gaya.controllers;

import java.util.List;

import org.hbs.gaya.bo.InventoryBo;
import org.hbs.gaya.model.Inventory;
import org.hbs.gaya.model.Material;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

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
