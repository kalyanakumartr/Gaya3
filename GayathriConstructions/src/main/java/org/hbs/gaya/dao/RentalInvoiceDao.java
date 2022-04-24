package org.hbs.gaya.dao;

import org.hbs.gaya.model.RentalInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalInvoiceDao extends JpaRepository<RentalInvoice, String>
{

}
