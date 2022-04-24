package org.hbs.gaya.bo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import org.hbs.gaya.model.Rental;
import org.hbs.gaya.model.RentalItem;

public interface RentalBo extends Serializable
{
	List<Rental> searchRental(String search);

	void calculateDayRental();

	List<RentalItem> searchRentalItem(String rentalId);

}
