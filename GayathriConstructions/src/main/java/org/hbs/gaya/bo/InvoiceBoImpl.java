package org.hbs.gaya.bo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.hbs.gaya.dao.RentalInvoiceDao;
import org.hbs.gaya.model.RentalInvoice;
import org.hbs.gaya.util.CommonValidator;
import org.hbs.gaya.util.EBusinessKey.EKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceBoImpl implements InvoiceBo
{
	private static final String	INV					= "INV-";
	private static final String	R_DELIMIT			= "-R";
	private static final long	serialVersionUID	= 4767826522188506770L;
	private DateTimeFormatter	dFormat				= DateTimeFormatter.ofPattern("ddMMyyyy");

	@Autowired
	RentalInvoiceDao			invoiceDao;

	private String getNewInvoiceId() throws Exception
	{
		String invoiceNo = invoiceDao.getLastInvoiceId();

		if (CommonValidator.isNullOrEmpty(invoiceNo))
			return generateInvoice(1000);
		else
		{
			if (invoiceNo.contains(R_DELIMIT))
			{
				invoiceNo = invoiceNo.split(R_DELIMIT)[1];
				return generateInvoice(Long.parseLong(invoiceNo) + 1);
			}
		}
		throw new Exception("Invoice No Creation Failed");
	}

	private String generateInvoice(long sequence)
	{
		return INV + LocalDate.now().format(dFormat) + R_DELIMIT + sequence;
	}

	@Override
	public String generateInvoice(String invoiceId, boolean isContinue) throws Exception
	{
		RentalInvoice rentalInvoice = invoiceDao.getById(invoiceId);

		if (rentalInvoice != null && rentalInvoice.getInvoiceNo() == null)
		{
			rentalInvoice.setEndDate(LocalDateTime.now());
			if(isContinue)
				rentalInvoice.setActive(false);
			rentalInvoice.setInvoiceNo(getNewInvoiceId());

			rentalInvoice = invoiceDao.save(rentalInvoice);

			if (isContinue)
			{
				rentalInvoice = rentalInvoice.getInvoiceClone();
				invoiceDao.saveAndFlush(rentalInvoice);
				return "";
			}
		}
		return rentalInvoice.getInvoiceNo();
	}

}
