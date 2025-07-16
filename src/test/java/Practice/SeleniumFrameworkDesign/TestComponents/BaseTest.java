package Practice.SeleniumFrameworkDesign.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import Practice.SeleniumFrameworkDesign.pageobjects.LandingPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	
	public WebDriver driver;
	public LandingPage landingpage;

	public WebDriver initializeDriver() throws IOException 
	{
		Properties prop = new Properties(); // intialize the Properties
		// below we will shorten the Path to start from the project path and not from the system path using user.dir
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\Practice\\SeleniumFrameworkDesign\\resources\\GlobalData.properties"); // providing the Path
		prop.load(fis); // load the properties file to get the key value. but this requires a FileInput Stream
		
		// it will take the maven property else from the property file
		String browserName = System.getProperty("browser")!=null ? System.getProperty("browser") : prop.getProperty("browser");
		
//		String browserName = prop.getProperty("browser");

		if(browserName.equalsIgnoreCase("chrome")) {		
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		
		}
		
		else if(browserName.equalsIgnoreCase("firefox")) {
			// firefox
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}
		else if(browserName.equalsIgnoreCase("edge")) {
			// edge
		}
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		return driver;
	}
	
//	converting Json to Hashmap
	public List<HashMap<String, String>> getJsonDatatoMap(String filePath) throws IOException {
		// read the json to string
		String jsonContent = FileUtils.readFileToString(
				new File(filePath),
				StandardCharsets.UTF_8);

		// string to HashMap Jackson Databind
		ObjectMapper mapper = new ObjectMapper();

		List<HashMap<String, String>> data = mapper.readValue(jsonContent,
				new TypeReference<List<HashMap<String, String>>>() {});

		return data;

	}
	
	// method to get the screenshot
	public String getScreenshot(String testcaseName, WebDriver driver) throws IOException {
		TakesScreenshot ts = (TakesScreenshot)driver; // inform driver we need to take screenshot
		File source = ts.getScreenshotAs(OutputType.FILE); // save the screenshot in file format
		File destfile = new File(System.getProperty("user.dir")+"//reports//" + testcaseName + ".png");
		FileUtils.copyFile(source, destfile); // store the screenshot 
		
		return System.getProperty("user.dir")+"//reports//" + testcaseName + ".png";
	}
	
	@BeforeMethod(alwaysRun = true)// so that the pre-req run even for a group annotations
	public LandingPage launchApplication() throws IOException 
	{
		driver = initializeDriver();
		
		landingpage = new LandingPage(driver);

		landingpage.goTo();
		
		return landingpage;
	}
	
	@AfterMethod(alwaysRun = true) // so that the pre-req run even for a group annotations
	public void tearDown()
	{
		driver.close();
	}
}
