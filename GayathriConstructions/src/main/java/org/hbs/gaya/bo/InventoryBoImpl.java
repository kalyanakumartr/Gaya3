package org.hbs.gaya.bo;

import java.util.List;
import java.util.Optional;

import org.hbs.gaya.dao.InventoryDao;
import org.hbs.gaya.dao.SequenceDao;
import org.hbs.gaya.model.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryBoImpl implements InventoryBo
{

	private static final long	serialVersionUID	= -8462653328322046420L;

	@Autowired
	private InventoryDao		inventoryDao;

	@Autowired
	SequenceDao					sequenceDao;

	@Override
	public List<Inventory> searchInventory(String searchParam)
	{
		return inventoryDao.searchInventory(searchParam);
	}

	@Override
	public Inventory save(Inventory inventory)
	{
		inventory.setInventoryId(sequenceDao.create("Inventory"));
		return inventoryDao.save(inventory);
	}

	@Override
	public Inventory getInventory(String inventoryId) throws Exception
	{
		Optional<Inventory> inventoryOpt = inventoryDao.findById(inventoryId);

		if (inventoryOpt.isPresent())
			return inventoryOpt.get();
		else
			throw new Exception("Inventory Not Exists For Given Inventory Id.");
	}

	@Override
	public Inventory getInventoryByMaterial(String materialId) throws Exception
	{
		Inventory inventoryOpt = inventoryDao.findByMaterialId(materialId);

		return inventoryOpt;

	}

	@Override
	public List<Inventory> getInventoryList()
	{
		return inventoryDao.findAll();
	}
}
