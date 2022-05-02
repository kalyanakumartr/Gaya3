package org.hbs.gaya.dao;

import org.hbs.gaya.model.Rental;
import org.hbs.gaya.model.RentalInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalInvoiceDao extends JpaRepository<RentalInvoice, String>
{
	@Query(value="SELECT RI.invoiceNo FROM rental_invoice RI Order By RI.invoiceNo Desc Limit 1", nativeQuery = true)
	String getLastInvoiceId();
	

	@Query("Select RI.rental From RentalInvoice RI Where RI.invoiceId = :invoiceId")
	Rental getRentalByInvoiceId(@Param("invoiceId")  String rentalOrInvoiceId);
}
