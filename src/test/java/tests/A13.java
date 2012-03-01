package tests;

import static org.junit.Assert.*;
import org.junit.Test;

public class A13
{
	@Test
	public void a1() 
	{
		assertNotNull( System.currentTimeMillis() );		
	}
	
	@Test
	public void a2() 
	{
		assertNull( System.currentTimeMillis() );		
	}
	
	@Test
	public void a3() 
	{
		assertNotNull( System.currentTimeMillis() );		
	}
}
