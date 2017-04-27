package com.vendingMachine.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.vendingMachine.exceptions.NoSufficentUserBalance;
import com.vendingMachine.exceptions.NoSufficientChangeException;
import com.vendingMachine.model.Coin;
import com.vendingMachine.model.Inventory;
import com.vendingMachine.model.Item;
import com.vendingMachine.service.VendingMachine;

/**
 * <h1>The Vending Machine functionality</h1> This class provides the
 * functionalities for vending machine
 * <p>
 * This class contains all the funcational methods for vending machine system
 * 
 * @author Hussain
 * @version 1.0
 */
public class VendingMachineImpl implements VendingMachine {

	// Create the logger instance
	private Logger logger = Logger.getLogger(VendingMachine.class);

	// Item inventory to hold items
	private Inventory<Coin> cashInventory = new Inventory<Coin>();

	// Cash inventory to hold coins
	private Inventory<Item> itemInventory = new Inventory<Item>();

	// Balance of the user
	private long userBalance;

	// Total sales of the vending machine
	private long totalSales;

	// Item selected to purchase
	private Item itemSelected;
	
	/**
	 * Parameterized constructor for the class, can be used to inject the member variables
	 * @param cashInventory
	 * @param itemInventory
	 * @param userBalance
	 * @param totalSales
	 * @param itemSelected
	 */
	public VendingMachineImpl(Inventory<Coin> cashInventory, Inventory<Item> itemInventory,
			long userBalance, long totalSales, Item itemSelected) {
		super();
		this.cashInventory = cashInventory;
		this.itemInventory = itemInventory;
		this.userBalance = userBalance;
		this.totalSales = totalSales;
		this.itemSelected = itemSelected;
	}
	
	/**
	 * Default constructor
	 */
	public VendingMachineImpl() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Displays the coins in Cash Inventory and Items in item inventory
	 */
	@Override
	public void displayInventory() {
		logger.info("Displaying inventory");
		for (Item item : Item.values()) {
			logger.info(item + ": " + itemInventory.getQuantity(item));
		}
		System.out.println("Display cash inventory");
		for (Coin coin : Coin.values()) {
			logger.info(coin + ": " + cashInventory.getQuantity(coin));
		}
	}

	/**
	 * Accepts a cash/coin and update the user balance and cash inventory
	 * 
	 * @param coin
	 */
	@Override
	public void insertCoin(Coin coin) {
		userBalance = userBalance + coin.getDenomination();
		cashInventory.add(coin);
	}

	/**
	 * selects an item, before selecting checks if the item is available in item
	 * inventory
	 * 
	 * @param item
	 */
	@Override
	public void selectItem(Item item) {
		if (itemInventory.hasItem(item)) {
			itemSelected = item;
		} else {
			logger.info("Selected item is not available");
		}
	}

	/**
	 * Gets the item from inventory and gives back the change to the buyer This
	 * method calls other methods for different functionalities
	 */
	@Override
	public void buyItemAndGetChange() {
		logger.info("Current Balance : " + userBalance + " Chosen the product :" + itemSelected);
		List<Coin> returnedCoinList;
		try {
			Item item = getItem(itemSelected);
			totalSales = totalSales + itemSelected.getPrice();
			returnedCoinList = getChange(userBalance - itemSelected.getPrice());
			updateCashInventory(returnedCoinList);
			itemSelected = null;
			userBalance = 0;

			System.out.println("Enjoy Your :" + item);
			System.out.println("Dont Forget to pick your change !");
			for (Coin coin : returnedCoinList) {
				System.out.println(coin + ": " + coin.getDenomination());
			}
		} catch (NoSufficientChangeException e) {
			logger.info("Exception thrown :" + e.getMessage());
		} catch (NoSufficentUserBalance e) {
			logger.info("Exception thrown :" + e.getMessage());
		} catch (Exception e) {
			logger.warn(" Some Unknow Exception Occured :" + e.getMessage());
		}

	}

	/**
	 * gets a requested item form the item inventory, reduce the item count from
	 * the inventory
	 * 
	 * @param item
	 *            the item selected
	 * @return returns the same item which is passed
	 * @throws NoSufficientChangeException
	 * @throws NoSufficentUserBalance
	 */
	@Override
	public Item getItem(Item item) throws NoSufficientChangeException, NoSufficentUserBalance {
		long amountToBeReturned;
		if (hasSufficientUserBalance()) {
			amountToBeReturned = userBalance - itemSelected.getPrice();
			if (hasSufficientChangeToReturn(amountToBeReturned)) {
				itemInventory.removeItemFromInventory(item);
			}
		} else {
			throw new NoSufficentUserBalance();
		}
		return item;
	}

	/**
	 * Checks whether sufficient balance is available to buy an item
	 * 
	 * @param amountToBeReturned
	 * @return true if sufficient balance is available to buy an item else false
	 * @throws NoSufficientChangeException
	 */
	@Override
	public boolean hasSufficientChangeToReturn(long amountToBeReturned) throws NoSufficientChangeException {
		boolean hasChange = true;
		try {
			getChange(amountToBeReturned);
		} catch (NoSufficientChangeException e) {
			hasChange = false;
		}
		return hasChange;
	}

	/**
	 * Checks whether sufficient balance is available with user to buy an item
	 * 
	 * @return true user sufficient balance is available
	 */
	@Override
	public boolean hasSufficientUserBalance() {
		return (userBalance >= itemSelected.getPrice()) ? true : false;
	}

	/**
	 * gets the change to be returned back, the returned list contains all the
	 * denomination of the coins
	 * 
	 * @param amountToBeReturned
	 * @return List of coins/currency denominations
	 * @throws NoSufficientChangeException
	 */
	@Override
	public List<Coin> getChange(long amountToBeReturned) throws NoSufficientChangeException {
		List<Coin> coinList = new ArrayList<Coin>();

		while (amountToBeReturned > 0) {
			if (amountToBeReturned >= Coin.QUARTER.getDenomination() && cashInventory.hasItem(Coin.QUARTER)) {
				coinList.add(Coin.QUARTER);
				amountToBeReturned = amountToBeReturned - Coin.QUARTER.getDenomination();
				continue;
			} else if (amountToBeReturned >= Coin.DIME.getDenomination() && cashInventory.hasItem(Coin.DIME)) {
				coinList.add(Coin.DIME);
				amountToBeReturned = amountToBeReturned - Coin.DIME.getDenomination();
				continue;
			} else if (amountToBeReturned >= Coin.NICKLE.getDenomination() && cashInventory.hasItem(Coin.NICKLE)) {
				coinList.add(Coin.NICKLE);
				amountToBeReturned = amountToBeReturned - Coin.NICKLE.getDenomination();
				continue;
			} else if (amountToBeReturned >= Coin.PENNY.getDenomination() && cashInventory.hasItem(Coin.PENNY)) {
				coinList.add(Coin.PENNY);
				amountToBeReturned = amountToBeReturned - Coin.PENNY.getDenomination();
				continue;
			} else {
				throw new NoSufficientChangeException();
			}

		}
		return coinList;
	}

	/**
	 * Remove an item from cash inventory
	 * 
	 * @param returnedCoinList
	 */
	@Override
	public void updateCashInventory(List<Coin> returnedCoinList) {
		for (Coin coin : returnedCoinList) {
			cashInventory.removeItemFromInventory(coin);
		}
	}
}
