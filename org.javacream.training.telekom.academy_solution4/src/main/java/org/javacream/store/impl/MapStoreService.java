package org.javacream.store.impl;

import java.util.HashMap;
import java.util.Map;

import org.javacream.store.api.StoreService;

public class MapStoreService implements StoreService{

	private Map<String, Map<String, Integer>> store;
	
	@Override
	public int getStock(String category, String id) {
		try {
			Integer stock = store.get(category).get(id);
			if (stock == null) {
				return 0;
			}else {
				return stock;
			}
		}
		catch(NullPointerException e) {
			return 0;
		}
	}
	
	public void setStock(String category, String id, int stock) {
		if (category == null || id == null) {
			throw new IllegalArgumentException ("category and id must not be null");
		}
		Map<String, Integer> items = store.get(category);
		if (items == null) {
			items = new HashMap<String, Integer>();
			store.put(category, items);
		}
		items.put(id, stock);
	}

	public void setStore(Map<String, Map<String, Integer>> store) {
		this.store = store;
	}
}