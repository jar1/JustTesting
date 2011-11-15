package tests;

import org.testng.Assert;
import org.testng.annotations.Test;


public class TestCurrentTime_3Tests_PFP
{
	@Test
	public void testCurrentTimeFirstTestPASSED() 
	{
		Assert.assertNotNull( System.currentTimeMillis() );		
	}
	
	@Test
	public void testCurrentTimeSecondTestFAILED() 
	{
		Assert.assertNull( System.currentTimeMillis() );		
	}
	
	@Test
	public void testCurrentTimeThirdTestPASSED() 
	{
		Assert.assertNotNull( System.currentTimeMillis() );		
	}	
}
