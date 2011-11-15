package tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

public class PassedFailedPassed_3Tests
{
	@Test
	public void testCurrentTimeFirstTestPASSED() 
	{
		assertNotNull( System.currentTimeMillis() );		
	}
	
	@Test
	public void testCurrentTimeSecondTestFAILED() 
	{
		assertFalse( System.currentTimeMillis() );		
	}
	
	@Test
	public void testCurrentTimeThirdTestPASSED() 
	{
		assertNotNull( System.currentTimeMillis() );		
	}	
}