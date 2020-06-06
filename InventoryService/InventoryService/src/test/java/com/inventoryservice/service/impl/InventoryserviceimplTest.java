package com.inventoryservice.service.impl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Inventoryserviceimpl.class})
public class InventoryserviceimplTest {
	
	@InjectMocks
	Inventoryserviceimpl inventoryService;
	
	@Test
	public void testStaticMethod() throws Exception{
		
		PowerMockito.mockStatic(Inventoryserviceimpl.class);
		
		when(Inventoryserviceimpl.checkStatic("static")).thenReturn("static");
		
		String check = Inventoryserviceimpl.checkStatic("static");
		
		assertEquals(check, "static");
	}
	
	@Test
	public void testPrivateMethod() throws Exception{
		
		String value = (String) Whitebox.invokeMethod(inventoryService, "checkPrivate", "private");
		
		assertEquals("private", value);
	}
}
