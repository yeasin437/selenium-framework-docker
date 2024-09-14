package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass{
	
	@Test
	public void verify_account_registraion()
	{
		HomePage hp = new HomePage(driver);
		hp.clickMyAccount();
		hp.clickRegister();
		
		AccountRegistrationPage regpage = new AccountRegistrationPage(driver);
		//regpage.setFirstName("Ysf");
		regpage.setFirstName(randomString().toUpperCase());
		regpage.setLastName("dfg");
		//regpage.setEmail("brooklyn3517@gmail.com");
		regpage.setEmail(randomString()+"@gmail.com");
		//regpage.setTelephone("1234");
		regpage.setTelephone(randomNumber());
//		regpage.setPassword("1234");
//		regpage.setConfirmPassword("1234");
		String password = randomAlphaNumeric();
		regpage.setPassword(password);
		regpage.setConfirmPassword(password);
		regpage.setPrivacyPolicy();
		regpage.clickCOntinue();
		String confirmsg =  regpage.getConfirmationMessage();
		Assert.assertEquals(confirmsg, "Your Account Has Been Created!");
		
	
	}
	

}
