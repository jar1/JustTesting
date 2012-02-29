package tests;

import static org.junit.Assert.*;
import org.junit.Test;

public class TestClass1
{
	@Test
	public void TestCase1() 
	{
		assertNotNull( System.currentTimeMillis() );		
	}
	
	@Test
	public void TestCase2() 
	{
		assertNotNull( System.currentTimeMillis() );		
	}
}
