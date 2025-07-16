package Practice.SeleniumFrameworkDesign.pageobjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Practice.SeleniumFrameworkDesign.AbstractComponent.AbstractComponent;

public class ProductCatalogue extends AbstractComponent {
	
	WebDriver driver;
	
	public ProductCatalogue(WebDriver driver) {
		super(driver);// created constructor
		// initialization
		this.driver = driver; 
		PageFactory.initElements(driver, this); // inorder to create the driver.findElement for FindBy Page factory
	}


	
	//	List <WebElement> products = driver.findElements(By.cssSelector(".mb-3"));

	//PageFactory
	@FindBy(css=".mb-3")
	List<WebElement> products; //for list of Web Elements
	
	@FindBy(css=".ng-animating")
	WebElement spinnerngAnimation;
	
	By productsByLocator = By.cssSelector(".mb-3");
	By addToCart = By.cssSelector(".card-body button:last-of-type");
	By toastMessage = By.cssSelector("#toast-container");
	
	public List<WebElement> getProductList() {
		waitForElementToAppear(productsByLocator);
		return products;
	}
	
	public WebElement getProductByname (String productName) {
		return getProductList().stream().filter(product -> product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
				
	}
	
	public void addProductToCart(String productName) {
		WebElement prod = getProductByname(productName);
		prod.findElement(addToCart).click();
//		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//		waitForElementToAppear(toastMessage);
//		waitForElementToDiappear(spinnerngAnimation);
		
	}

}
