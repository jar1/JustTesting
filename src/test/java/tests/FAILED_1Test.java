package tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

public class FAILED_1Test
{
	@Test
	public void testCurrentTimeFAILED() 
	{
		assertFalse( System.currentTimeMillis() );		
	}
}
