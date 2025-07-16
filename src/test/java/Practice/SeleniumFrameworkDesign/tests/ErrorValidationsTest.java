package Practice.SeleniumFrameworkDesign.tests;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import Practice.SeleniumFrameworkDesign.TestComponents.BaseTest;
import Practice.SeleniumFrameworkDesign.TestComponents.Retry;
import Practice.SeleniumFrameworkDesign.pageobjects.CartPage;
import Practice.SeleniumFrameworkDesign.pageobjects.CheckoutPage;
import Practice.SeleniumFrameworkDesign.pageobjects.ConfirmationPage;
import Practice.SeleniumFrameworkDesign.pageobjects.ProductCatalogue;

public class ErrorValidationsTest extends BaseTest {

	@Test
	public void LoginErrorValidation() throws IOException{
		// TODO Auto-generated method stub


		landingpage.loginApplication("princec@gmail.com", "Prince@12312");
		Assert.assertEquals("Incorrect email or password.", landingpage.getErrorMessage());
		
	}
	
	@Test(groups = {"ErrorHandling"}, retryAnalyzer = Retry.class)
	public void ProductErrorValidation() throws IOException{
		// TODO Auto-generated method stub

		String productName = "ZARA COAT 3";

		ProductCatalogue productCatalogue = landingpage.loginApplication("princec@gmail.com", "Prince@123");
		
		List<WebElement> products = productCatalogue.getProductList();

		productCatalogue.addProductToCart(productName);

		CartPage cartpage = productCatalogue.goToCartPage();

		Boolean match = cartpage.verifyProductDisplay("ZARA COAT 33");
		Assert.assertFalse(match);

	}

}
