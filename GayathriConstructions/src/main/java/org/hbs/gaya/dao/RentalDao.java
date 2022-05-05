package org.hbs.gaya.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.hbs.gaya.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalDao extends JpaRepository<Rental, String>
{
	@Query("Select R From Rental R where R.customer.customerName like %:search%  or R.customer.mobileNo like %:search% or R.customer.alternateNo like %:search%")
	List<Rental> searchRental(String search);

	@Query("Select RI.rental From RentalInvoice RI Where RI.active = true And RI.rental.rentalStatus = 'Rented' And RI.calculatedDate < :calDate")
	List<Rental>  getPendingRental(@Param("calDate")  @DateTimeFormat(iso = ISO.DATE) LocalDateTime calculatedDate);


}
