package org.hbs.gaya.controllers;

import java.util.List;

import org.hbs.gaya.dao.MaterialDao;
import org.hbs.gaya.model.Material;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

@Controller

public class MaterialController {

	@Autowired
	MaterialDao matDao;
	
	@GetMapping(value = "/material")
	public String viewMaterialPage()
	{
		return "material";
	}

	@PostMapping("/searchMaterial")
	@ResponseBody
	public String searchMaterial()
	{
		try
		{
			List<Material> dataList = matDao.findAll();
			return new ObjectMapper().writeValueAsString(dataList);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}

}
