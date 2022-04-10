package org.hbs.gaya.dao;

import java.util.List;

import org.hbs.gaya.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialDao extends JpaRepository<Material, String>
{
	@Query("Select mat From Material mat Where mat.materialName like %:materialName%")
	List<Material> searchMaterial(String materialName);
}
