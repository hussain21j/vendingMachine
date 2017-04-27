package com.vendingMachine.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.easymock.EasyMock;
import org.easymock.EasyMockRunner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.vendingMachine.model.Coin;
import com.vendingMachine.model.Inventory;
import com.vendingMachine.model.Item;
import com.vendingMachine.service.impl.VendingMachineImpl;

/**
 * This is the Unit test case class for the vending machine service class
 * 
 * @author Hussain
 * @version 1.0
 */
public class VendingMachineTest {
	// Create the logger instance
	private Logger logger = Logger.getLogger(VendingMachine.class);

	private VendingMachine vendingMachine;
	private Inventory<Coin> cashInventoryMock;
	private Inventory<Item> itemInventoryMock;
	private long userBalance;
	private Item item;
	private long totalSales;
	private List<Coin> coinList;

	@Before
	public void setUp() {
		logger.info("Start setUp");
		cashInventoryMock = EasyMock.createMock(Inventory.class);
		itemInventoryMock = EasyMock.createMock(Inventory.class);
		userBalance = 100;
		totalSales = 500;
		coinList = new ArrayList<Coin>();
		vendingMachine = new VendingMachineImpl(cashInventoryMock, itemInventoryMock, userBalance, totalSales,
				item.COKE);
		logger.info("finished setUp");
	}

	@Test
	public void TesthasSufficientUserBalance() {
		logger.info("Start testing the TesthasSufficientUserBalancemethod");
		Assert.assertEquals(true, vendingMachine.hasSufficientUserBalance());
		logger.info("finished testing the TesthasSufficientUserBalancemethod");
	}

	@Test
	public void TestGetChange() {
		logger.info("Start setUp");
		EasyMock.expect(cashInventoryMock.hasItem(Coin.QUARTER)).andStubReturn(true); //define what to return when called
		EasyMock.replay(cashInventoryMock); //set the object
		coinList.add(Coin.QUARTER);
		Assert.assertArrayEquals(coinList.toArray(), vendingMachine.getChange(25).toArray());
		logger.info("Start setUp");
	}

}
