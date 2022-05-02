package org.hbs.gaya.bo;

import java.io.Serializable;

public interface InvoiceBo extends Serializable
{
	String generateInvoice(String invoiceId, boolean isContinue) throws Exception;
}
