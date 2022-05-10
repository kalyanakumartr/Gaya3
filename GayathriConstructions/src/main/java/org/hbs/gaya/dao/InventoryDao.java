package org.hbs.gaya.dao;

import java.util.List;

import org.hbs.gaya.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryDao extends JpaRepository<Inventory, String>
{
	@Query("Select I From Inventory I Where I.material.materialName Like %:search% Or I.material.numberCode = :search")
	List<Inventory> searchInventory(@Param("search") String searchParam);

	@Query("Select I From Inventory I Where I.material.materialId = :materialId")
	Inventory findByMaterialId(@Param("materialId") String materialId);
}
