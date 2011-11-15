package tests;

import org.testng.Assert;
import org.testng.annotations.Test;


public class TestCurrentTime_1Tests_P
{
	@Test
	public void testCurrentTimePASSED() 
	{
		Assert.assertNotNull( System.currentTimeMillis() );		
	}
}
