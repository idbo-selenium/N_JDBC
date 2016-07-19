package sample;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginJDBC {

	public static WebDriver driver;
	public static String baseURL = "http://example.local/auth/login";	
	
	static int maxSecond=5000;
	public static WebElement waitForPageUntilElementIsVisible(By locator, int maxSecond){
		return (new WebDriverWait(driver, maxSecond))
				.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
		
	
	public void Init(){
		driver = new FirefoxDriver();
		//driver.manage().window().maximize();
		driver.navigate().to(baseURL);
	}
	
    
    public void CleanUp(){
    	//driver.close();
    }
	
   
    public void Login(){
    	waitForPageUntilElementIsVisible(By.xpath("/html/body/div/div/div/div/div[2]/form/div[1]/div/input"), 5000).sendKeys("knskumari@gmail.com");
		waitForPageUntilElementIsVisible(By.xpath("/html/body/div/div/div/div/div[2]/form/div[2]/div/input"), 5000).sendKeys("123456");
		waitForPageUntilElementIsVisible(By.xpath("html/body/div[1]/div/div/div/div[2]/form/div[5]/div/button"), 5000).click();
		waitForPageUntilElementIsVisible(By.xpath(".//*[@id='bs-example-navbar-collapse-1']/ul[2]/li/a"), maxSecond).click();
		waitForPageUntilElementIsVisible(By.xpath(".//*[@id='bs-example-navbar-collapse-1']/ul[2]/li/ul/li[2]/a"), maxSecond).click();
    }	
}