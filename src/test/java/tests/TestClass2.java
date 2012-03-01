package tests;

import static org.junit.Assert.*;
import org.junit.Test;

public class TestClass2
{
	@Test
	public void TestCase21() 
	{
		assertNull( System.currentTimeMillis() );		
	}
	
	@Test
	public void TestCase22() 
	{
		assertNotNull( System.currentTimeMillis() );		
	}
}

