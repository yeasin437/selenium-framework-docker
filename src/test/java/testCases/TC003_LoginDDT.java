package testCases;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.Checkout;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import testBase.BaseClass;
import uitlies.DataProviders;
public class TC003_LoginDDT extends BaseClass{
	
	@Test(dataProvider="LoginData",dataProviderClass=DataProviders.class,groups="datadriven") //not in same class the data provider method
	//we have to add additional attribute
	public void verify_LoginDOT(String email,String pwd,String exp)
	{
		
		try
		{
		HomePage hp = new HomePage(driver);
		hp.clickMyAccount();
		hp.clickLogin();
		
		LoginPage lp = new LoginPage(driver);
		lp.setEmail(email);
		lp.setPassword(pwd);
		lp.clickLogin();
		
		Checkout cp = new Checkout(driver);
		boolean targetPage = cp.isMyAccountPageExists();
		
		if(exp.equalsIgnoreCase("Valid"))
		{
			if(targetPage==true)
			{
				cp.clickLogout();
				Assert.assertTrue(true);
				
			}
			else
			{
				Assert.assertTrue(false);
			}
		}
		else if(exp.equalsIgnoreCase("Invalid"))
		{
			if(targetPage==true)
			{
				cp.clickLogout();
				Assert.assertTrue(false);
			}
			else
			{
				Assert.assertTrue(true);
			}
		}
		}
		catch(Exception e)
		{
			Assert.fail();
		}
	}
}
