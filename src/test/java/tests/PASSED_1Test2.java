package tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

public class PASSED_1Test2
{
	@Test
	public void testCurrentTimePASSED2() 
	{
		assertNotNull( System.currentTimeMillis() );		
	}
}
