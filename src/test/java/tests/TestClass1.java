package tests;

import static org.junit.Assert.*;
import org.junit.Test;

public class TestClass1
{
	@Test
	public void TestCase11() 
	{
		assertNotNull( System.currentTimeMillis() );		
	}
	
	@Test
	public void TestCase12() 
	{
		assertNull( System.currentTimeMillis() );		
	}
}
