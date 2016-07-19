package JDBC_Practice;

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
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import sample.GitHub;

public class GitHub_Login {

	WebDriver driver;
	public static String baseURL = "https://github.com/login";
	public static String JDBC_Driver = "com.mysql.jdbc.Driver";
	public final String DB_URL = "jdbc:mysql://localhost:3306/selenium";
	public String user = "root";
	public String pass = "";	
	
	public void ExplicitWaitForAnElementToBeVisible(WebDriver driver,String xpath){
		(new WebDriverWait(driver, 5)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
	}
	
	@Before
	public void Init(){
		driver = new FirefoxDriver();
	}
	
	@After
	public void CleanUp(){
		driver.close();
	}
	
	@Test
	public void Login_GitHub(){
		Connection con = null;
		Statement stmt = null;
		try {
			Class.forName(JDBC_Driver);
			System.out.println("Connecting to DataBase....");
			con = DriverManager.getConnection(DB_URL, user, pass);
			stmt = con.createStatement();
			String sql = "Select * from github";
			ResultSet rs = stmt.executeQuery(sql);
			ArrayList<GitHub> githubArray = new ArrayList<GitHub>();
			GitHub github = null;
			while(rs.next()){
				github = new GitHub();
				github.id= rs.getInt("id");
				github.username = rs.getString("username");
				github.password = rs.getString("pasword");
				githubArray.add(github);
			}
			for(GitHub item : githubArray){
				System.out.println(item.id+" , "+item.username+" , "+item.password);
				driver.navigate().to("https://github.com/login");
				driver.findElement(By.id("login_field")).sendKeys(item.username);
				driver.findElement(By.id("password")).sendKeys(item.password);
				driver.findElement(By.name("commit")).click();
				try{
					ExplicitWaitForAnElementToBeVisible(driver,"/html/body/div[2]/div/ul[2]/li[3]/a");
					driver.findElement(By.xpath("/html/body/div[2]/div/ul[2]/li[3]/a")).click();
					driver.findElement(By.className("logout-form")).click();
					stmt = con.createStatement();
					stmt.executeUpdate("update github set status = 'pass' where id = '"+item.id+"'");
					System.out.println("Successfully signout");	
				}
				catch(TimeoutException e){		
					stmt = con.createStatement();
					stmt.executeUpdate("update github set status = 'fail' where id = '"+item.id+"'");
					System.out.println("Invalid Credentials");
				}				
			}
			con.close();
			stmt.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}	
}