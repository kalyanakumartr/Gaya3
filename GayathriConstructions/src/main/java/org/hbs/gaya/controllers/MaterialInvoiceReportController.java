package org.hbs.gaya.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hbs.gaya.bo.InvoiceBo;
import org.hbs.gaya.bo.RentalBo;
import org.hbs.gaya.model.PaymentHistory;
import org.hbs.gaya.model.Rental;
import org.hbs.gaya.model.RentalInvoice;
import org.hbs.gaya.model.RentalInvoice.EInvoiceStatus;
import org.hbs.gaya.model.RentalItem;
import org.hbs.gaya.util.CommonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

	private DateTimeFormatter	dtFormat	= DateTimeFormatter.ofPattern("dd-MMM-yyyy hh:mm a");
	private DateTimeFormatter	dFormat		= DateTimeFormatter.ofPattern("dd-MMM-yyyy");

	@Autowired
	RentalBo					rentalBo;

	@Autowired
	InvoiceBo					invoiceBo;

	@GetMapping(value = "/reports")
	public String viewReportsPage()
	{
		return "reports";
	}

	@PostMapping(value = "/calculateInvoice")
	@ResponseBody
	public String calculateInvoice(HttpServletRequest request)
	{
		return "failure";
	}

	@PostMapping(value = "/payRentalAmount", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String payRentalAmount(@RequestBody Map<String, String> payload)
	{
		Double paymentAmt = Double.parseDouble(payload.get("paymentAmount"));
		Double discountAmt = Double.parseDouble(payload.get("discountAmount"));
		Rental rental = rentalBo.getRentalById(payload.get("rentalId"));
		Double totalPayment = paymentAmt + discountAmt;
		Double invoiceBalance = 0.0; 
		if (CommonValidator.isNotNullNotEmpty(rental))
		{
			for (RentalInvoice rI : rental.getRentalInvoiceSet())
			{
				if (totalPayment > 0 && rI.getInvoiceStatus() != EInvoiceStatus.Settled)
				{
					if (rI.getInvoiceBalance() == totalPayment)
					{
						rI.setPaymentAmount(rI.getPaymentAmount() + totalPayment);
						if (rI.getInvoiceStatus() == EInvoiceStatus.Payable)
							rI.setInvoiceStatus(EInvoiceStatus.Settled);

						rental.getPaymentSet().add(PaymentHistory.builder()//
								.paymentAmount(paymentAmt)//
								.discountAmount(discountAmt)//
								.paymentDate(LocalDateTime.now())//
								.rentalInvoice(rI)//DonotChange Order
								.rental(rental)//DonotChange Order
								.build());
					}
					else if (rI.getInvoiceBalance() < totalPayment)
					{
						invoiceBalance = rI.getInvoiceBalance();//Mandate
						totalPayment = totalPayment - invoiceBalance;
						rI.setPaymentAmount(rI.getPaymentAmount() + invoiceBalance);
						if (rI.getInvoiceStatus() == EInvoiceStatus.Payable)
							rI.setInvoiceStatus(EInvoiceStatus.Settled);
						
						rental.getPaymentSet().add(PaymentHistory.builder()//
								.paymentAmount(invoiceBalance)//
								.discountAmount(0.0)//
								.paymentDate(LocalDateTime.now())//
								.rentalInvoice(rI)//DonotChange Order
								.rental(rental)//DonotChange Order
								.build());
					}
					else
					{
						rI.setPaymentAmount(rI.getPaymentAmount() + totalPayment);

						rental.getPaymentSet().add(PaymentHistory.builder()//
								.paymentAmount(discountAmt > 0 ? totalPayment-discountAmt : totalPayment)//
								.discountAmount(discountAmt)//
								.paymentDate(LocalDateTime.now())//
								.rentalInvoice(rI)//DonotChange Order
								.rental(rental)//DonotChange Order
								.build());
						totalPayment = 0.0;//DonotChange Order
					}
				}
			}
			rentalBo.saveOrUpdate(rental);
			return "success";
		}
		return "failure";
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

		return "failure";
	}

	@PostMapping(value = "/viewRentalInvoice/{rentalId}")
	public String viewRentalInvoice(ModelMap modal, @PathVariable("rentalId") String rentalOrInvoiceId)
	{
		Rental rental = rentalBo.getRentalById(rentalOrInvoiceId);
		RentalInvoice rentalInvoice = null;

		if (rental == null)
		{
			rental = rentalBo.getRentalByInvoiceId(rentalOrInvoiceId);
			rentalInvoice = rental.getRentalInvoiceSet().stream().filter(p -> p.getInvoiceId().equals(rentalOrInvoiceId)).findFirst().get();
		}
		else
			rentalInvoice = rental.getActiveInvoice();

		LocalDateTime endDate = rentalInvoice.getEndDate() == null ? LocalDateTime.now() : rentalInvoice.getEndDate();
		LocalDateTime invoiceDate = rentalInvoice.getInvoiceDate() == null ? LocalDateTime.now() : rentalInvoice.getInvoiceDate();

		long noOfDays = rentalInvoice.getStartDate().until(endDate, ChronoUnit.DAYS);

		rental.getCustomer().setAddress(rental.getCustomer().getAddress().toUpperCase());

		// double totalRentAmount = 0.0;
		double totalCostAmount = 0.0;

		for (RentalItem rItem : rental.getRentalItemSet())
		{
			rItem.setRentalCost(rItem.getTotalCost() * noOfDays);
			// totalRentAmount = totalRentAmount + rItem.getRentalCost();
			totalCostAmount = totalCostAmount + rItem.getTotalCost();
		}

		modal.addAttribute("items", rental.getRentalItemSet());
		modal.addAttribute("invoice", rental.getRentalInvoiceSet());
		modal.addAttribute("advanceAmount$", rental.getAdvanceAmount$());
		modal.addAttribute("totalAmount$", rental.getCurrency(rentalInvoice.getInvoiceAmount()));
		modal.addAttribute("totalCostAmount$", rental.getCurrency(totalCostAmount));
		modal.addAttribute("customer", rental.getCustomer());
		modal.addAttribute("startDate$", rentalInvoice.getStartDate().format(dtFormat));
		modal.addAttribute("endDate$", endDate.format(dtFormat));
		modal.addAttribute("rentedDate$", rental.getRentedDate().format(dtFormat));
		modal.addAttribute("invoiceId", rentalInvoice.getInvoiceId());
		modal.addAttribute("invoiceNo", rentalInvoice.getInvoiceNo() == null ? "[Yet To Generate]" : rentalInvoice.getInvoiceNo());
		modal.addAttribute("invoiceNoList", rental.getInvoiceNoList());
		modal.addAttribute("invoiceDate$", invoiceDate.format(dFormat));
		modal.addAttribute("noOfDays", noOfDays);
		modal.addAttribute("disableCreateInvoice", CommonValidator.isNotNullNotEmpty(rentalInvoice.getInvoiceNo()) || noOfDays == 0);

		return "fragments/invoice";
	}
}
