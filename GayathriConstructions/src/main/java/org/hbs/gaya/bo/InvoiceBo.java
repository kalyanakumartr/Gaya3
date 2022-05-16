package org.hbs.gaya.bo;

import java.io.Serializable;
import java.util.Map;

import org.springframework.ui.ModelMap;

public interface InvoiceBo extends Serializable
{
	String generateInvoice(String invoiceId, boolean isContinue) throws Exception;

	String payRentalAmount(Map<String, String> payload);

	String viewRentalInvoice(ModelMap modal, String rentalOrInvoiceId);
}
