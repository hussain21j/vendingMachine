package com.vendingMachine.service;

import java.util.List;

import com.vendingMachine.exceptions.NoSufficentUserBalance;
import com.vendingMachine.exceptions.NoSufficientChangeException;
import com.vendingMachine.model.Coin;
import com.vendingMachine.model.Item;
/**
 * This interface declares all the basic methods for the vending machine functioning
 * @author Hussain
 * @version
 */
public interface VendingMachine {
	void displayInventory();

	void insertCoin(Coin coin);

	void selectItem(Item item);

	void buyItemAndGetChange();

	Item getItem(Item item) throws NoSufficientChangeException, NoSufficentUserBalance;

	boolean hasSufficientChangeToReturn(long amountToBeReturned) throws NoSufficientChangeException;

	boolean hasSufficientUserBalance();

	List<Coin> getChange(long amountToBeReturned) throws NoSufficientChangeException;

	void updateCashInventory(List<Coin> returnedCoinList);
}