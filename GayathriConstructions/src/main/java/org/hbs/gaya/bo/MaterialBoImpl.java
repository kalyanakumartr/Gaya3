package org.hbs.gaya.bo;

import java.util.List;

import org.hbs.gaya.dao.MaterialDao;
import org.hbs.gaya.model.Material;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class MaterialBoImpl implements MaterialBo
{

	private static final long serialVersionUID = -8462653328382046420L;

	@Autowired
	private MaterialDao materialDao;

	@Override
	public List<Material> getAllMaterials()
	{
		return materialDao.getAllMaterials();
	}
	@Override
	public List<Material> searchMaterial(String materialName){
		return materialDao.searchMaterial(materialName);
	}
}
