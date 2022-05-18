package org.hbs.gaya.bo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hbs.gaya.dao.RentalInvoiceDao;
import org.hbs.gaya.dao.SequenceDao;
import org.hbs.gaya.model.RentalInvoice;
import org.hbs.gaya.model.RentalInvoice.EInvoiceStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceBoImpl implements InvoiceBo
{
	private static final long	serialVersionUID	= 4767826522188506770L;

	@Autowired
	RentalInvoiceDao			invoiceDao;

	@Autowired
	SequenceDao					sequenceDao;

	@Override
	public String generateInvoice(String invoiceId, boolean isContinue) throws Exception
	{
		RentalInvoice rentalInvoice = invoiceDao.getById(invoiceId);
		String invoiceNoDisp = "";
		if (rentalInvoice != null && rentalInvoice.getInvoiceNo() == null)
		{
			rentalInvoice.setEndDate(LocalDateTime.now());
			rentalInvoice.setInvoiceDate(LocalDateTime.now());
			rentalInvoice.setInvoiceStatus(EInvoiceStatus.Payable);
			if (isContinue)
				rentalInvoice.setActive(false);
			rentalInvoice.setInvoiceNo(sequenceDao.create("InvoiceNo"));

			rentalInvoice = invoiceDao.save(rentalInvoice);

			invoiceNoDisp = rentalInvoice.getInvoiceNo() + " | " + "&#x20B9; " + new BigDecimal(rentalInvoice.getInvoiceAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).toString() + " | "
					+ rentalInvoice.getInvoiceStatus();

			if (isContinue)
			{
				rentalInvoice.setInvoiceStatus(EInvoiceStatus.Pending);
				rentalInvoice = rentalInvoice.getInvoiceClone();
				rentalInvoice.setInvoiceId(sequenceDao.create("Invoice"));
				invoiceDao.saveAndFlush(rentalInvoice);
			}
		}
		return invoiceNoDisp;
	}

}
