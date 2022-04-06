package org.hbs.gaya.bo;

import java.io.Serializable;
import java.util.List;

import org.hbs.gaya.model.Inventory;
import org.springframework.stereotype.Service;

@Service
public interface InventoryBo extends Serializable
{
	List<Inventory> searchInventory(String searchParam);

	Inventory save(Inventory inventory);

	Inventory getInventory(String inventoryId) throws Exception;
}
