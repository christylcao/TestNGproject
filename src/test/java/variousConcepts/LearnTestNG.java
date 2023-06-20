package variousConcepts;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LearnTestNG {

	WebDriver driver;
	String browser = null;
	String url = null;
	
	By USER_NAME_FIELD = By.id("username");
	By PASSWORD_FIELD = By.id("password");
	By SIGN_IN_FIELD = By.name("login");
	By DASHBOARD_HEADER_FIELD = By.xpath("//h2[text()=' Dashboard ']");
	By CUSTOMER_MENU_FIELD = By.xpath("//span[contains(text(), 'Customers')]");
	By ADD_CUSTOMER_FIELD = By.xpath("/html/body/section/div/nav/div/ul/li[3]/ul/li[1]/a");
	By COUNTRY_DROPDOWN_FIELD = By.xpath("//select[@id='country']");
	By FULL_NAME_FIELD = By.xpath("//*[@id=\"account\"]");
	By COMPANY_MENU_FIELD = By.xpath("//*[@id=\"cid\"]");
	By EMAIL_FIELD = By.xpath("//input[@id='email']");
	By PHONE_NUM_FIELD = By.xpath("//input[@id='phone']");
	By ADDRESS_FIELD = By.xpath("//input[@id='address']");
	By CITY_FIELD = By.xpath("//input[@id='city']");
	By ADD_CUSTOMER_HEADER_FIELD = By.xpath("//h5[contains(text(), 'Add Contact')]");
	
//	Test/Mock Data
	String username = "demo@techfios.com";
	String password = "abc123";
	String name = "Selenium102";
	String company = "Bank Of America";
	String email = "abdojwmd@gmail.com";
	String country = "Svalbard And Jan Mayen";
	String phone = "0123456789";
	String address = "1234 Selenium Lane";
	String city = "Edmond";
	
	
	@BeforeClass
	public void readConfig() {
		try {
			InputStream input = new FileInputStream("src\\test\\java\\config\\config.properties");
			Properties prop = new Properties();
			prop.load(input);
			browser = prop.getProperty("browser");
			url = prop.getProperty("url");
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@BeforeMethod
	public void init() {
		
		if(browser.equalsIgnoreCase("Chrome")) {
			System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
			driver = new ChromeDriver();
			
		}else if (browser.equalsIgnoreCase("Edge")) {
			System.setProperty("webdriver.edge.driver", "drivers\\msedgedriver.exe");
			driver = new EdgeDriver();
		}
		
		driver.manage().deleteAllCookies();
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		
	}
	
//	@Test
	public void loginTest() {
		
		driver.findElement(USER_NAME_FIELD).sendKeys(username);
		driver.findElement(PASSWORD_FIELD).sendKeys(password);
		driver.findElement(SIGN_IN_FIELD).click();
		
		Assert.assertEquals(driver.findElement(DASHBOARD_HEADER_FIELD).getText(), "Dashboard", 
				"Dashboard page not found!!!" );
	}
	
	@Test
	public void addCust() throws InterruptedException {
		
		loginTest();
		Thread.sleep(2000);
		driver.findElement(CUSTOMER_MENU_FIELD).click();
		driver.findElement(ADD_CUSTOMER_FIELD).click();
		
		waitforElement(driver, 10, ADD_CUSTOMER_HEADER_FIELD);	
		Assert.assertEquals(driver.findElement(ADD_CUSTOMER_HEADER_FIELD).getText(), "Add Contact", "ADD CUSTOMER PAGE NOT FOUND!");
		
		driver.findElement(FULL_NAME_FIELD).sendKeys(name + generateRandomNum(9999));
		
		selectField(driver.findElement(COMPANY_MENU_FIELD), company);
		
		driver.findElement(EMAIL_FIELD).sendKeys(generateRandomNum(99)+ email);
		
		selectField(driver.findElement(COUNTRY_DROPDOWN_FIELD), country);
		
		waitforElement(driver, 10, COUNTRY_DROPDOWN_FIELD);
		
		driver.findElement(PHONE_NUM_FIELD).sendKeys(phone);
		driver.findElement(ADDRESS_FIELD).sendKeys(address);
		driver.findElement(CITY_FIELD).sendKeys(city);
		

	}
	

	private void waitforElement(WebDriver driver, int timeInSeconds, By locator) {
		WebDriverWait wait = new WebDriverWait(driver, timeInSeconds);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		
	}

	private void selectField(WebElement element, String visibleText ) {
		Select sel = new Select(element);
		sel.selectByVisibleText(visibleText);
	
	}

	private int generateRandomNum(int bounderyNum) {
		Random rnd = new Random();
		int generatedNum = rnd.nextInt(bounderyNum);
		return generatedNum; 
	}

	
	
}
