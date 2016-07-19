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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import sample.OutLook;

public class OutLook_Login {
	public WebDriver driver;
	public static String base_URL ="https://email.i-dbo.com/owa/#path=/mail";
	public static String JDBC_Driver = "com.mysql.jdbc.Driver";
	public static String DB_URL = "jdbc:mysql://localhost:3306/selenium";
	public static String username = "root";
	public static String password = "";
		
	@Before
	public void Init(){
		System.setProperty("webdriver.chrome.driver", "F:\\Eclipse_Selenium\\Java_Selenium_Maven\\chromedriver_win32\\chromedriver.exe");
		//driver = new ChromeDriver();
	}
	
	@After
	public void CleanUp(){
		//driver.close();
	}
	
	@Test
	public void OutLook(){
		Connection con = null;
		Statement stmt = null;
		try {
			Class.forName(JDBC_Driver);
			con = DriverManager.getConnection(DB_URL, username, password);
			stmt = con.createStatement();
			String sql = "select * from outlook";
			ResultSet rs = stmt.executeQuery(sql);
			OutLook outlook = null;
			ArrayList<OutLook> arraylist = new ArrayList<OutLook>();
			while(rs.next()){
				outlook = new OutLook();
				outlook.id = rs.getInt("id");
				outlook.username = rs.getString("username");
				outlook.password = rs.getString("password");
				arraylist.add(outlook);
			}
			
			for(OutLook item : arraylist){
				System.out.println(item.id+" , "+item.username+" , "+item.password);
				driver = new ChromeDriver();
				driver.get(base_URL);
				Thread.sleep(4000);
				driver.findElement(By.id("username")).sendKeys(item.username);
				driver.findElement(By.id("password")).sendKeys(item.password);
				driver.findElement(By.xpath("//*[@id='lgnDiv']/div[9]/div/span")).click();
				Thread.sleep(10000);
				driver.close();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}