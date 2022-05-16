package org.hbs.gaya.controllers;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.hbs.gaya.bo.RentalBo;
import org.hbs.gaya.dao.CustomerDao;
import org.hbs.gaya.dao.SequenceDao;
import org.hbs.gaya.model.Customer;
import org.hbs.gaya.model.Rental;
import org.hbs.gaya.model.RentalInvoice;
import org.hbs.gaya.model.Users;
import org.hbs.gaya.model.Rental.ERentalStatus;
import org.hbs.gaya.model.RentalInvoice.EInvoiceStatus;
import org.hbs.gaya.util.CommonValidator;
import org.hbs.gaya.util.ConstUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CustomerController implements Serializable
{
	private static final long	serialVersionUID	= 4326256676370001415L;

	@Autowired
	SequenceDao					sequenceDao;

	@Autowired
	CustomerDao					customerDao;

	@Autowired
	RentalBo					rentalBo;

	@GetMapping(value = "/showCustomer")
	public String showCustomer()
	{
		return "add_rentals";
	}

	@PostMapping(value = "/addCustomer")
	@ResponseBody
	public String addCustomerRental(HttpSession session, @RequestBody Map<String, String> payload)
	{
		Customer customer = customerDao.save(Customer.builder()//
				.address(payload.get("address"))//
				.alternateNo(payload.get("alternateNo"))//
				.mobileNo(payload.get("mobileNo"))//
				.customerName(payload.get("customerName"))//
				.customerId(sequenceDao.create("Customer"))//
				.build());
		

		if (CommonValidator.isNotNullNotEmpty(customer))
		{
			
			Rental rental = Rental.builder()//
					.advanceAmount(0.0)//
					.rentalId(sequenceDao.create("Rental"))//
					.rentalStatus(ERentalStatus.None)//
					.closureStatus(ERentalStatus.None)//
					.rentedDate(LocalDateTime.now())//
					.customer(customer)//
					.createdDate(LocalDateTime.now())//
					.createdUser(Users.builder().employeeId("System").build()).build(); //(String) session.getAttribute("employeeId")

			RentalInvoice rentalInvoice = RentalInvoice.builder()//
					.invoiceId(sequenceDao.create("RentalInvoice"))//
					.startDate(LocalDateTime.now())//
					.calculatedDate(LocalDateTime.now())//
					.active(true)//
					.invoiceStatus(EInvoiceStatus.None)//
					.rental(rental).build();
			
			rentalInvoice.setMasterInvoice(rentalInvoice);
			rental.getRentalInvoiceSet().add(rentalInvoice);
			
			rentalBo.saveOrUpdate(rental);
		}

	return ConstUtil.SUCCESS;
}

}
