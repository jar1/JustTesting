package tests;

import static org.junit.Assert.*;
import org.junit.Test;

public class TestClass2
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

