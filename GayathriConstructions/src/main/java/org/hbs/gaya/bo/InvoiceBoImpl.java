package org.hbs.gaya.bo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.hbs.gaya.dao.RentalInvoiceDao;
import org.hbs.gaya.dao.SequenceDao;
import org.hbs.gaya.model.PaymentHistory;
import org.hbs.gaya.model.Rental;
import org.hbs.gaya.model.Rental.ERentalStatus;
import org.hbs.gaya.model.RentalInvoice;
import org.hbs.gaya.model.RentalInvoice.EInvoiceStatus;
import org.hbs.gaya.model.RentalItem;
import org.hbs.gaya.util.CommonValidator;
import org.hbs.gaya.util.ConstUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class InvoiceBoImpl implements InvoiceBo
{
	private static final long	serialVersionUID	= 4767826522188506770L;
	private DateTimeFormatter	dtFormat			= DateTimeFormatter.ofPattern("dd-MMM-yyyy hh:mm a");
	private DateTimeFormatter	dFormat				= DateTimeFormatter.ofPattern("dd-MMM-yyyy");

	@Autowired
	RentalBo					rentalBo;

	@Autowired
	RentalInvoiceDao			invoiceDao;

	@Autowired
	SequenceDao					sequenceDao;

	@Override
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

		rental.getCustomer().setAddress(rental.getCustomer().getAddress().toUpperCase());

		long noOfDays = rentalInvoice.getNoOfDays$();

		modal.addAttribute("items", rentalInvoice.getItemSet());
		modal.addAttribute("invoice", rental.getRentalInvoiceSet());
		modal.addAttribute("advanceAmount$", ConstUtil.getCurrency(rental.getAdvanceAmount()));
		modal.addAttribute("totalAmount$", ConstUtil.getCurrency(rentalInvoice.getInvoiceAmount()));
		modal.addAttribute("totalCostAmount$", ConstUtil.getCurrency(rentalInvoice.getItemsTotalCost()));
		modal.addAttribute("customer", rental.getCustomer());
		modal.addAttribute("startDate$", rentalInvoice.getStartDate().format(dtFormat));
		modal.addAttribute("endDate$", rentalInvoice.getEndDate$().format(dtFormat)); // If_Null,ReturnsCurrentDate
		modal.addAttribute("rentedDate$", rental.getRentedDate().format(dtFormat));
		modal.addAttribute("invoiceId", rentalInvoice.getInvoiceId());
		modal.addAttribute("invoiceStatus", rentalInvoice.getInvoiceStatus());
		modal.addAttribute("invoiceNo", rentalInvoice.getInvoiceNo() == null ? "[Yet To Generate]" : rentalInvoice.getInvoiceNo());
		modal.addAttribute("invoiceNoList", rental.getInvoiceNoList());
		modal.addAttribute("invoiceDate$", rentalInvoice.getInvoiceDate$().format(dFormat));
		modal.addAttribute("noOfDays", noOfDays);
		modal.addAttribute("disableCreateInvoice", CommonValidator.isNotNullNotEmpty(rentalInvoice.getInvoiceNo()) || noOfDays == 0);

		return "fragments/invoice";
	}

	@Override
	public String generateInvoice(String invoiceId, boolean isContinue) throws Exception
	{
		RentalInvoice rentalInvoice = invoiceDao.getById(invoiceId);
		String invoiceNoDisp = "";
		if (rentalInvoice.getInvoiceNo() == null)
		{
			rentalInvoice.setEndDate(LocalDateTime.now());
			rentalInvoice.setInvoiceDate(LocalDateTime.now());
			rentalInvoice.setInvoiceStatus(EInvoiceStatus.Payable);
			if (isContinue)
				rentalInvoice.setActive(false);
			else
				rentalInvoice.getRental().setClosureStatus(ERentalStatus.Closed);
				
			rentalInvoice.setInvoiceNo(sequenceDao.create("InvoiceNo"));

			rentalInvoice = invoiceDao.save(rentalInvoice);

			invoiceNoDisp = rentalInvoice.getInvoiceNo() + " | " + ConstUtil.getCurrency(rentalInvoice.getPaymentAmount()) + " / " + ConstUtil.getCurrency(rentalInvoice.getInvoiceAmount()) + " | " + rentalInvoice.getInvoiceStatus();

			if (isContinue)
			{
				RentalInvoice tempInvoice = rentalInvoice.getInvoiceClone();
				tempInvoice.setInvoiceStatus(EInvoiceStatus.Pending);
				tempInvoice.setInvoiceId(sequenceDao.create("Invoice"));

				RentalItem tempItem;
				for (RentalItem item : rentalInvoice.getItemSet())
				{
					tempItem = item.clone();
					tempItem.setItemId(sequenceDao.create("RentalItem"));
					tempItem.setRentalInvoice(tempInvoice);
					tempInvoice.getItemSet().add(tempItem);
				}
				invoiceDao.saveAndFlush(tempInvoice);
			}
		}
		return invoiceNoDisp;
	}

	@Override
	public String payRentalAmount(Map<String, String> payload)
	{
		Double paymentAmt = Double.parseDouble(payload.get("paymentAmount"));
		Double discountAmt = Double.parseDouble(payload.get("discountAmount"));
		Rental rental = rentalBo.getRentalById(payload.get("rentalId"));
		Double totalPayment = paymentAmt + discountAmt;
		Double invoiceBalance = 0.0;
		LocalDateTime currDT = LocalDateTime.now();
		if (CommonValidator.isNotNullNotEmpty(rental))
		{
			for (RentalInvoice rI : rental.getRentalInvoiceSet())
			{
				if (totalPayment > 0 && rI.getInvoiceStatus() != EInvoiceStatus.Settled)
				{
					if (rI.getInvoiceBalance().doubleValue() == totalPayment.doubleValue())
					{
						rI.setPaymentAmount(rI.getPaymentAmount() + totalPayment);
						if (rI.getInvoiceStatus() == EInvoiceStatus.Payable)
							rI.setInvoiceStatus(EInvoiceStatus.Settled);

						currDT = currDT.plusSeconds(1);
						rental.getPaymentSet().add(PaymentHistory.builder()//
								.paymentAmount(paymentAmt)//
								.discountAmount(discountAmt)//
								.paymentDate(currDT)//
								.rentalInvoice(rI)// DonotChange Order
								.rental(rental)// DonotChange Order
								.build());
					}
					else if (rI.getInvoiceBalance().doubleValue() < totalPayment.doubleValue())
					{
						invoiceBalance = rI.getInvoiceBalance();// Mandate
						totalPayment = totalPayment - invoiceBalance;
						rI.setPaymentAmount(rI.getPaymentAmount() + invoiceBalance);
						if (rI.getInvoiceStatus() == EInvoiceStatus.Payable)
							rI.setInvoiceStatus(EInvoiceStatus.Settled);

						currDT = currDT.plusSeconds(1);
						rental.getPaymentSet().add(PaymentHistory.builder()//
								.paymentAmount(invoiceBalance)//
								.discountAmount(0.0)//
								.paymentDate(currDT)//
								.rentalInvoice(rI)// DonotChange Order
								.rental(rental)// DonotChange Order
								.build());
					}
					else
					{
						rI.setPaymentAmount(rI.getPaymentAmount() + totalPayment);

						currDT = currDT.plusSeconds(1);
						rental.getPaymentSet().add(PaymentHistory.builder()//
								.paymentAmount(discountAmt > 0 ? totalPayment - discountAmt : totalPayment)//
								.discountAmount(discountAmt)//
								.paymentDate(currDT)//
								.rentalInvoice(rI)// DonotChange Order
								.rental(rental)// DonotChange Order
								.build());
						totalPayment = 0.0;// DonotChange Order
					}
				}
			}
			rentalBo.saveOrUpdate(rental);
			return ConstUtil.SUCCESS;
		}
		return ConstUtil.FAILURE;
	}

}
