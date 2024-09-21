package testCases;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pageObjects.Checkout;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import testBase.BaseClass;

public class TC002_AccountLoginTest extends BaseClass{
	
	@Test(groups= {"Master"})
	public void verity_account_login()
	{

		try
		{
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			hp.clickLogin();
			
			LoginPage lp = new LoginPage(driver);
			//try to get from property file
			lp.setEmail("abc3517@gmail.com");
			lp.setPassword("1234");
			lp.clickLogin();
			
	    	Checkout cp = new Checkout(driver);
		//	String text = cp.getCheckoutText();
			//Assert.assertEquals(text, "My Account");
	    	boolean targetPage = cp.isMyAccountPageExists();
	    	Assert.assertEquals(targetPage, true,"Login failed");
		}
		catch(Exception e)
		{
			Assert.fail();
		}
		
	
	}

}
