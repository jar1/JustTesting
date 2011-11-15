package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PassedFailedPassed_3Tests
{
	@Test
	public void testCurrentTimeFirstTestPASSED() 
	{
		Assert.assertNotNull( System.currentTimeMillis() );		
	}
	
	@Test
	public void testCurrentTimeSecondTestFAILED() 
	{
		Assert.assertFalse( System.currentTimeMillis() );		
	}
	
	@Test
	public void testCurrentTimeThirdTestPASSED() 
	{
		Assert.assertNotNull( System.currentTimeMillis() );		
	}	
}