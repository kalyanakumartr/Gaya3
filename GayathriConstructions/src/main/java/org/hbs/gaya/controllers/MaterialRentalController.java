package org.hbs.gaya.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hbs.gaya.bo.MaterialBo;
import org.hbs.gaya.bo.RentalBo;
import org.hbs.gaya.dao.CustomerDao;
import org.hbs.gaya.model.Customer;
import org.hbs.gaya.model.Material;
import org.hbs.gaya.model.Rental;
import org.hbs.gaya.model.RentalInvoice;
import org.hbs.gaya.model.RentalItem;
import org.hbs.gaya.util.CommonValidator;
import org.hbs.gaya.util.DataTableParam;
import org.springframework.beans.factory.annotation.Autowired;
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
	@GetMapping(value = "/addRentals")
	public String addRental()
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
	
	@PostMapping(value = "/getRentalMaterials")
	public String getRentalMaterials(ModelMap modal)
	{
		
		List<Material> dataList = materialBo.getAllMaterials();

		//modal.addAttribute("items", rental.getRentalItemSet());
		modal.addAttribute("materialList", dataList);
		
		return "fragments/addrentmaterial";
	}
	/*@PostMapping("/getRentalMaterials")
	@ResponseBody
	public String getRentalMaterials()
	{
		String data = "";
		
			try
			{
				List<Material> dataList = materialBo.getAllMaterials();
				ObjectMapper o = mapperBuilder.build();
				o.registerModule(new JavaTimeModule());
				data = o.writeValueAsString(dataList);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
		return data;
	}	*/
	
}
