package Practice.SeleniumFrameworkDesign.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import Practice.SeleniumFrameworkDesign.AbstractComponent.AbstractComponent;

public class CheckoutPage extends AbstractComponent {
	WebDriver driver;

	public CheckoutPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}


	@FindBy(css = "[placeholder='Select Country']")
	WebElement country;


	@FindBy(css = ".action__submit")
	WebElement submitbutton;
	
	
	By results = By.cssSelector(".ta-results");
	By countries = By.cssSelector(".ta-results button span");


	public void selectCountry(String countryName) {
		country.sendKeys(countryName);

//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
		waitForElementToAppear(results);
		List <WebElement> Countries = driver.findElements(countries);
		WebElement selectedCountry = Countries.stream().filter(country -> country.getText().equalsIgnoreCase(countryName)).findFirst().orElse(null);
		selectedCountry.click();

	}
	
	public ConfirmationPage submitOrder() {
		submitbutton.click();
		return new ConfirmationPage(driver);
	}



}
