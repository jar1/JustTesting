package tests;

import static org.junit.Assert.*;
import org.junit.Test;

public class C1
{
	@Test
	public void c1() 
	{
		assertNotNull( System.currentTimeMillis() );		
	}
	
	@Test
	public void c2() 
	{
		assertNull( System.currentTimeMillis() );		
	}
}
