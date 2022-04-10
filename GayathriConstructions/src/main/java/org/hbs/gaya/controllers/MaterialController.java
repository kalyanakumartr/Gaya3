package org.hbs.gaya.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hbs.gaya.dao.MaterialDao;
import org.hbs.gaya.model.Material;
import org.hbs.gaya.util.DataTableParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller

public class MaterialController {

	@Autowired
	MaterialDao matDao;
	
	@GetMapping(value = "/material")
	public String viewMaterialPage()
	{
		return "material";
	}

	@GetMapping(value = "/addMaterial")
	public String addMaterialPage()
	{
		return "addMaterial";	
	}

	@PostMapping("/searchMaterial")
	@ResponseBody
	public String searchMaterial( HttpServletRequest request)
	{
		try
		{
			DataTableParam dtParam = DataTableParam.init(request);
			
			System.out.println("GS : "+dtParam.getGeneralSearch());
			List<Material> dataList = matDao.searchMaterial(dtParam.getGeneralSearch()== null?"":dtParam.getGeneralSearch());
			return new ObjectMapper().writeValueAsString(dataList);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return "material";
	}

}
