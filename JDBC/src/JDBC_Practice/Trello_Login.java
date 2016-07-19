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
import sample.Trello;

public class Trello_Login {

	WebDriver driver;
	public static String JDBC_Driver = "com.mysql.jdbc.Driver";
	public static String DB_URL = "jdbc:mysql://localhost:3306/selenium";
	public static String username = "root";
	public static String password = "";
	
	public void ExplicitWaitForAnElementToBeVisible(WebDriver driver,String xpath){
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
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
	public void Login_Trello(){
		Connection con = null;
		Statement stmt = null;
		try {
			Class.forName(JDBC_Driver);
			con = DriverManager.getConnection(DB_URL, username, password);
			stmt = con.createStatement();
			String sql = "select * from trellologin";
			ResultSet rs = stmt.executeQuery(sql);
			ArrayList<Trello> trelloArray = new ArrayList<Trello>();
			Trello trello = null;
			while(rs.next()){
				trello = new Trello();
				trello.id = rs.getInt("ID");
				trello.username = rs.getString("username");
				trello.password = rs.getString("pasword");
				trelloArray.add(trello);
			}
			for(Trello item : trelloArray){
				System.out.println(item.id+" , "+item.username+" , "+item.password);
//				driver.navigate().to("https://trello.com/login");
//				driver.findElement(By.id("user")).sendKeys(item.username);
//				driver.findElement(By.id("password")).sendKeys(item.password);
//				driver.findElement(By.id("login")).click();
//				try{
//					ExplicitWaitForAnElementToBeVisible(driver,"/html/body/div[3]/div[1]/div[4]/a[2]/span[2]");
//					driver.findElement(By.xpath("/html/body/div[3]/div[1]/div[4]/a[2]/span[2]")).click();
//					driver.findElement(By.xpath("/html/body/div[5]/div/div[2]/div/div/div/ul[3]/li/a")).click();
//					System.out.println("Successfully signout");
//					stmt = con.createStatement();
//					String update = "update trellologin set status = 'pass' where id = "+item.id;
//					stmt.executeUpdate(update);
//				} catch(TimeoutException e){
//					stmt = con.createStatement();
//					String update = "update trellologin set status = 'fail' where id = "+item.id;
//					stmt.executeUpdate(update);
//					System.out.println("Wrong Credentials");
//				}
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