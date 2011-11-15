package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PASSED_1Test2
{
	@Test
	public void testCurrentTimePASSED2() 
	{
		Assert.assertNotNull( System.currentTimeMillis() );		
	}
}
