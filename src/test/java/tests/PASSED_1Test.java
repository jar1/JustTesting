package tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

public class PASSED_1Test
{
	@Test
	public void testCurrentTimePASSED() 
	{
		assertNotNull( System.currentTimeMillis() );		
	}
}
