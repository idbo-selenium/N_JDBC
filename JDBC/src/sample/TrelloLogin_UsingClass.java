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
import org.openqa.selenium.chrome.ChromeDriver;

public class TrelloLogin_UsingClass {

	public static WebDriver driver;
	public static String baseURL = "https://trello.com/login";
	public static String JDBC_driver = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/selenium";
	public static String user = "root";
	public static String pass = "";	
	
	@Before
	public void Init(){
		System.setProperty("webdriver.chrome.driver", "F:\\Eclipse_Selenium\\Java_Selenium_Maven\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
	}
	
	@After
	public void CleanUp(){
		driver.close();
	}
	
	@Test
	public void Trello_Login() throws InterruptedException{
		Connection conn = null;
		Statement stmt = null;
		
		try {
			Class.forName(JDBC_driver);
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, user, pass);
			stmt = conn.createStatement();
			String sql = "select * from trelloLogin";
			ResultSet rs = stmt.executeQuery(sql);
			ArrayList<Trello> arrayList = new ArrayList<Trello>();
			Trello trello = null;
			while(rs.next()){
				trello = new Trello();
				trello.id = rs.getInt("id");
				trello.username = rs.getString("username");
				trello.password = rs.getString("password");
				arrayList.add(trello);
			}
			for(Trello item : arrayList){
				System.out.println(item.id +" , "+item.username+" , "+item.password);
				driver.get(baseURL);
				driver.findElement(By.id("user")).sendKeys(item.username);
				driver.findElement(By.id("password")).sendKeys(item.password);
				driver.findElement(By.id("login")).click();
				Thread.sleep(3000);
				String loginpageURL = "https://trello.com/login";
				String currentURL = driver.getCurrentUrl();
				if(!loginpageURL.equals(currentURL)){
					driver.findElement(By.xpath(".//*[@id='header']/div[4]/a[2]")).click();
					driver.findElement(By.xpath("html/body/div[5]/div/div[2]/div/div/div/ul[3]/li/a")).click();
					System.out.println("correct details");
				}
				else{
					System.out.println("invalid details");
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}	
}