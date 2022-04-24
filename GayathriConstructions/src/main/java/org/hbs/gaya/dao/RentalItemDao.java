package org.hbs.gaya.dao;

import java.util.List;

import org.hbs.gaya.model.RentalItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalItemDao extends JpaRepository<RentalItem, String>
{
	@Query("Select RI From RentalItem RI Where RI.rental.rentalId = :rentalId")
	List<RentalItem> searchRentalItem(@Param("rentalId") String rentalId);

}
