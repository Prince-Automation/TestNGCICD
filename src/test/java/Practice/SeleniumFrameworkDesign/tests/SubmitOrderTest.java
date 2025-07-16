package Practice.SeleniumFrameworkDesign.tests;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Practice.SeleniumFrameworkDesign.TestComponents.BaseTest;
import Practice.SeleniumFrameworkDesign.pageobjects.CartPage;
import Practice.SeleniumFrameworkDesign.pageobjects.CheckoutPage;
import Practice.SeleniumFrameworkDesign.pageobjects.ConfirmationPage;
import Practice.SeleniumFrameworkDesign.pageobjects.LandingPage;
import Practice.SeleniumFrameworkDesign.pageobjects.OrderPage;
import Practice.SeleniumFrameworkDesign.pageobjects.ProductCatalogue;
import io.github.bonigarcia.wdm.WebDriverManager;

public class SubmitOrderTest extends BaseTest {

	String productName = "ZARA COAT 3";
	
	@Test(dataProvider = "getData", groups = {"Purchase"})
	public void submitOrder(HashMap<String, String> input) throws IOException{
		// TODO Auto-generated method stub

		

		ProductCatalogue productCatalogue = landingpage.loginApplication(input.get("email"), input.get("password"));
		
		List<WebElement> products = productCatalogue.getProductList();

		productCatalogue.addProductToCart(input.get("productName"));

		CartPage cartpage = productCatalogue.goToCartPage();

		Boolean match = cartpage.verifyProductDisplay(input.get("productName"));
		Assert.assertTrue(match);
		CheckoutPage checkoutPage = cartpage.goToCheckout();

//		driver.findElement(By.cssSelector(".totalRow button")).click();

		String countryName = "india";

		checkoutPage.selectCountry(countryName);

//		driver.findElement(By.cssSelector("[placeholder='Select Country']")).sendKeys(countryName);

		ConfirmationPage confirmationPage = checkoutPage.submitOrder();
//		
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
//		
//		
//		
//		List <WebElement> Countries = driver.findElements(By.cssSelector(".ta-results button span"));
//		WebElement selectedCountry = Countries.stream().filter(country -> country.getText().equalsIgnoreCase(countryName)).findFirst().orElse(null);
//		
////		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
//		selectedCountry.click();

//		driver.findElement(By.cssSelector(".action__submit")).click();

		String confirmationMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmationMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));

	}
	
	@Test(dependsOnMethods = {"submitOrder"})
	public void OrderHistoryTest() {
		ProductCatalogue productCatalogue = landingpage.loginApplication("princec@gmail.com", "Prince@123");
		OrderPage orderpage = productCatalogue.goToOrdersPage();
		Assert.assertTrue(orderpage.verifyOrderDisplay(productName));
		
	}
	
	
	@DataProvider
	public Object[][] getData() throws IOException {
		
		List<HashMap<String, String>> data = getJsonDatatoMap(System.getProperty("user.dir")
				+ "\\src\\test\\java\\Practice\\SeleniumFrameworkDesign\\data\\PurchaseOrder.json");
		
		return new Object[][] {{data.get(0)},{data.get(1)}};
		
	
	}
	
	
//	@DataProvider
//	public Object[][] getData() {
//		
//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("email","princec@gmail.com");
//		map.put("password", "Prince@123");
//		map.put("productName", "ZARA COAT 3");
//		
//		HashMap<String, String> map1 = new HashMap<String, String>();
//		map1.put("email","princegeneric@gmail.com");
//		map1.put("password", "Princegeneric@123");
//		map1.put("productName", "ADIDAS ORIGINAL");
//		
//		return new Object[][] {{map},{map1}};
//		
//	
//	}
	
//	@DataProvider
//	public Object[][] getData() {
//		return new Object[][] {{"princec@gmail.com","Prince@123", "ZARA COAT 3"},{"princegeneric@gmail.com","Princegeneric@123", "ADIDAS ORIGINAL"} };
//	}

}
