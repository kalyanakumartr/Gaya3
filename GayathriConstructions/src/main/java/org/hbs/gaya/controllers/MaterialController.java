package org.hbs.gaya.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hbs.gaya.dao.MaterialDao;
import org.hbs.gaya.dao.UsersDao;
import org.hbs.gaya.model.Material;
import org.hbs.gaya.util.DataTableParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller

public class MaterialController {

	@Autowired
	MaterialDao matDao;
	
	@Autowired
	private UsersDao usersDao;
	
	@GetMapping(value = "/material")
	public String viewMaterialPage()
	{
		return "material";
	}

	@PostMapping(value = "/addMaterial" , produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String addMaterialPage( Material material)
	{
		System.out.println(material+"material"+ material.getMaterialName());
		material.setCreatedUser(usersDao.getLoginDetails("1234567890").get(0));
		material.setModifiedUser(usersDao.getLoginDetails("1234567890").get(0));
		material.setStatus(true);
		material.setDisplayOrder(1);
		matDao.save(material);
		return "material";	
	}
	@PostMapping(value = "/editMaterial" , produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String editMaterialPage( Material material)
	{
		Material materialtemp = matDao.getById(material.getMaterialId());
		materialtemp.setMaterialName(material.getMaterialName());
		materialtemp.setDescription(material.getDescription());
		materialtemp.setNumberCode(material.getNumberCode());
		matDao.save(materialtemp);
		return "material";	
	}
	@PostMapping(value = "/deleteMaterial" , produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String deleteMaterialPage( Material material)
	{
		Material materialtemp = matDao.getById(material.getMaterialId());
		materialtemp.setStatus(false);
		matDao.save(materialtemp);
		return "material";	
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
