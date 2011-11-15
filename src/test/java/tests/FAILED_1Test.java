package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class FAILED_1Test
{
	@Test
	public void testCurrentTimeFAILED() 
	{
		Assert.assertFalse( System.currentTimeMillis() );		
	}
}
