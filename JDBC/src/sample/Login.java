package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Login {
	
	public static WebDriver driver;
	public static String baseURL = "http://example.local/auth/login";
	
	public static String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost:3306/selenium";	
	static final String user = "root";
	static final String pass = "";
	public static String users;
	public static String password;
	
	int maxSecond;
	public static WebElement waitForPageUntilElementIsVisible(By locator, int maxSecond){
		return (new WebDriverWait(driver, maxSecond))
				.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	@Before
	public void Init(){
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.navigate().to(baseURL);
	}
	
	@After
	public void Cleanup(){
		//driver.close(); 
	}
	
	@Test
	public void Login_Test() throws SQLException{
		Connection conn = null;
	    Statement stmt = null; 
	    try{
			Class.forName(JDBC_DRIVER);
			System.out.println("Connecting to database...");
		    conn = DriverManager.getConnection(DB_URL,user,pass);
		    stmt = conn.createStatement();
		    String sql;
		    sql = "SELECT * FROM login where ID = 1";
		    ResultSet rs = stmt.executeQuery(sql);
		    ArrayList<String> al=new ArrayList<String>();			    
		    while(rs.next()){		    			    	
		         int id  = rs.getInt("ID");
		         String username = rs.getString("username");
		         String passw = rs.getString("password");
		         String mailid = rs.getString("mailid");		         
		         System.out.print("ID: " + id);
		         System.out.print(", Username: " + username);
		         System.out.print(", Password: " + passw);
		         System.out.println(", MailID: "+mailid);
		         al.add(mailid);
		         al.add(passw);
		         users = al.get(0);
		         password = al.get(1);
//		         System.out.println("Username : "+users);
//		         System.out.println("Password : "+password);
//		         System.out.println(username);
		      }		    
		}
		catch(Exception e){
		    e.printStackTrace();
		}
		finally{
			if(conn!=null)
	            conn.close();
	    }	
	    waitForPageUntilElementIsVisible(By.xpath("/html/body/div/div/div/div/div[2]/form/div[1]/div/input"), 5000).sendKeys(users);
		waitForPageUntilElementIsVisible(By.xpath("/html/body/div/div/div/div/div[2]/form/div[2]/div/input"), 5000).sendKeys(password);
		waitForPageUntilElementIsVisible(By.xpath("/html/body/div[1]/div/div/div/div[2]/form/div[5]/div/button"), 5000).click();
	}	
}