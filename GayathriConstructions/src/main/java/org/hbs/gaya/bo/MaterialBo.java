package org.hbs.gaya.bo;

import java.io.Serializable;
import java.util.List;

import org.hbs.gaya.model.Material;

public interface MaterialBo extends Serializable{

	List<Material> getAllMaterials();
	List<Material> searchMaterial(String materialName);
}
