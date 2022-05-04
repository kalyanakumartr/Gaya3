package org.hbs.gaya.dao;

import java.util.List;

import org.hbs.gaya.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerDao extends JpaRepository<Customer, String>
{
	@Query("Select C From Customer C where C.customerName like %:search%  or C.mobileNo like %:search% or C.alternateNo like %:search%")
	List<Customer> searchCustomer(String search);

}
