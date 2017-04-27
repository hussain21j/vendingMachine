package com.vendingMachine.util;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AppartmentTest {
	private Appartment appartment;
	Bathroom bathRoomMock;
	@Before
	public void setUp(){
		bathRoomMock = EasyMock.createMock(Bathroom.class);
		appartment = new Appartment(bathRoomMock, 100);
		
		
	}
	
	@Test
	public void testGetTotalAppartmentArea(){
		EasyMock.expect(bathRoomMock.getArea()).andReturn((long) 50);
		EasyMock.replay(bathRoomMock);
		
		Assert.assertEquals(150, appartment.getTotalAppartmentArea());
	}
}
