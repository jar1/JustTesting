package tests;

import org.testng.Assert;
import org.testng.annotations.Test;


public class TestCurrentTime _1Tests_F
{
	@Test
	public void testCurrentTimeFAILED() 
	{
		Assert.assertNull( System.currentTimeMillis() );		
	}
}
