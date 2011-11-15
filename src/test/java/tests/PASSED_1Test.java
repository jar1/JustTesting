package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PASSED_1Test
{
	@Test
	public void testCurrentTimePASSED() 
	{
		Assert.assertNotNull( System.currentTimeMillis() );		
	}
}
