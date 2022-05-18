package org.hbs.gaya.bo;

import java.io.Serializable;
import java.util.List;

import org.hbs.gaya.model.Rental;

public interface RentalBo extends Serializable
{
	void calculateDayRental();

	String calculateDayRentalForSpecificRentalId(String rentalId) throws Exception;

	Rental getRentalById(String rentalId);

	Rental getRentalByInvoiceId(String rentalOrInvoiceId);
	
	Rental saveOrUpdate(Rental rental);

	List<Rental> searchRental(String search, boolean includeAll);

}
