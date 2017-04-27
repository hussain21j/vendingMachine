package com.vendingMachine.ui;

import com.vendingMachine.service.VendingMachine;
import com.vendingMachine.service.impl.VendingMachineImpl;
import com.vendingMachine.model.Coin;
import com.vendingMachine.model.Item;

public class ClientVendingMachine {
	public static void main(String[] args) {
		System.out.println("Hello World");
		VendingMachine vendingMachine = new VendingMachineImpl();
		//vendingMachine.initialization();
		vendingMachine.displayInventory();

		vendingMachine.insertCoin(Coin.QUARTER);

		vendingMachine.selectItem(Item.PEPSI);
		vendingMachine.buyItemAndGetChange();
		System.out.println("Good Day ! Come Again");

	}
}
