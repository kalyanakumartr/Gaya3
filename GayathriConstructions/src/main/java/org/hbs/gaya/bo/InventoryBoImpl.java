package org.hbs.gaya.bo;

import java.util.List;
import java.util.Optional;

import org.hbs.gaya.dao.InventoryDao;
import org.hbs.gaya.model.Inventory;
import org.hbs.gaya.util.EBusinessKey.EKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryBoImpl implements InventoryBo
{

	private static final long serialVersionUID = -8462653328322046420L;

	@Autowired
	private InventoryDao inventoryDao;

	@Override
	public List<Inventory> searchInventory(String searchParam)
	{
		return inventoryDao.searchInventory(searchParam);
	}

	@Override
	public Inventory save(Inventory inventory)
	{
		inventory.setInventoryId(EKey.timeline("IN"));
		return inventoryDao.save(inventory);
	}

	@Override
	public Inventory getInventory(String inventoryId) throws Exception
	{
		Optional<Inventory> inventoryOpt = inventoryDao.findById(inventoryId);
		
		if(inventoryOpt.isPresent())
			return inventoryOpt.get();
		else
			throw new Exception("Inventory Not Exists For Given Inventory Id.");
	}

}
