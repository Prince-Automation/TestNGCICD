package Practice.SeleniumFrameworkDesign.tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import Practice.SeleniumFrameworkDesign.pageobjects.CartPage;
import Practice.SeleniumFrameworkDesign.pageobjects.CheckoutPage;
import Practice.SeleniumFrameworkDesign.pageobjects.ConfirmationPage;
import Practice.SeleniumFrameworkDesign.pageobjects.LandingPage;
import Practice.SeleniumFrameworkDesign.pageobjects.ProductCatalogue;
import io.github.bonigarcia.wdm.WebDriverManager;

public class SubmitOrderTestOriginal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String productName = "ZARA COAT 3";
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();

		LandingPage landingpage = new LandingPage(driver);

		landingpage.goTo();

		ProductCatalogue productCatalogue = landingpage.loginApplication("princec@gmail.com", "Prince@123");
		List<WebElement> products = productCatalogue.getProductList();

		productCatalogue.addProductToCart(productName);

		CartPage cartpage = productCatalogue.goToCartPage();

		Boolean match = cartpage.verifyProductDisplay(productName);
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
		driver.close();

	}

}
