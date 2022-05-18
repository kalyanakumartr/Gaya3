package org.hbs.gaya.controllers;

import java.util.Map;

import org.hbs.gaya.bo.InvoiceBo;
import org.hbs.gaya.bo.RentalBo;
import org.hbs.gaya.util.ConstUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MaterialInvoiceReportController
{
	@Autowired
	RentalBo	rentalBo;

	@Autowired
	InvoiceBo	invoiceBo;

	@GetMapping(value = "/reports")
	public String viewReportsPage()
	{
		return "reports";
	}

	@PostMapping(value = "/calculateInvoice/{rentalId}")
	@ResponseBody
	public ResponseEntity<?> calculateInvoice(@PathVariable("rentalId") String rentalId)
	{
		try
		{
			return new ResponseEntity<>(rentalBo.calculateDayRentalForSpecificRentalId(rentalId), HttpStatus.OK); 
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new ResponseEntity<>( e.getLocalizedMessage(), HttpStatus.BAD_REQUEST); 
		}

	}

	@PostMapping(value = "/payRentalAmount", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String payRentalAmount(@RequestBody Map<String, String> payload)
	{
		try
		{
			return invoiceBo.payRentalAmount(payload);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return ConstUtil.FAILURE;
	}

	@PostMapping(value = "/generateInvoice/{isContinue}/{invoiceId}")
	@ResponseBody
	public String generateInvoice(@PathVariable("isContinue") int isContinue, @PathVariable("invoiceId") String invoiceId)
	{
		try
		{
			return invoiceBo.generateInvoice(invoiceId, isContinue == 0);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return ConstUtil.FAILURE;
	}

	@PostMapping(value = "/viewRentalInvoice/{rentalId}")
	public String viewRentalInvoice(ModelMap modal, @PathVariable("rentalId") String rentalOrInvoiceId)
	{
		try
		{
			return invoiceBo.viewRentalInvoice(modal, rentalOrInvoiceId);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return ConstUtil.FAILURE;
	}
}
