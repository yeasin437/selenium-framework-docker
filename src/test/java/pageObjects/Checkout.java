package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Checkout extends BasePage{
	public Checkout(WebDriver driver)
	{
		super(driver);
	}
@FindBy(xpath="//h2[normalize-space()='My Account']") WebElement lnkCheckout;
@FindBy(xpath="//a[@class='list-group-item'][normalize-space()='Logout']") WebElement lnkLogout;

//public String getCheckoutText()
//{
//	return lnkCheckout.getText();
//}
public boolean isMyAccountPageExists()
{
	try {
		return lnkCheckout.isDisplayed();
	}
	catch(Exception e)
	{
		return false;
	}
}
public void clickLogout()
{
	lnkLogout.click();
}
}
