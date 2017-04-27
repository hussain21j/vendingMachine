package com.vendingMachine.model;

import java.util.HashMap;
import java.util.Map;

/**
 * This is generic class for any inventory
 * @author Hussain
 * @param <T> T is used as generic for any inventory
 * @version 1.0
 */
public class Inventory<T> {
	private Map<T, Integer> inventory = new HashMap<T, Integer>();

	public int getQuantity(T item) {
		Integer value = inventory.get(item);
		return value == null ? 0 : value;
	}

	public void add(T item) {
		int count = inventory.get(item);
		inventory.put(item, count + 1);
	}

	public void put(T item, int quantity) {
		inventory.put(item, quantity);
	}

	public boolean removeItemFromInventory(T item) {
		boolean removeStatus = false;
		int itemCount;
		if (hasItem(item)) {
			itemCount = inventory.get(item);
			inventory.put(item, itemCount - 1);
			removeStatus = true;
		}
		return removeStatus;
	}

	public boolean hasItem(T item) {
		return getQuantity(item) > 0;
	}

}
