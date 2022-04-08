package org.hbs.gaya.dao;

import org.hbs.gaya.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialDao extends JpaRepository<Material, String>
{

}
