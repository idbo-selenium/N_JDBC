package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class OutLookLogin_UsingClass {

	public static WebDriver driver;
	public static String baseURL ="https://email.i-dbo.com/owa/#path=/mail";
	public static String JDBC_driver = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/selenium";
	public static String username = "root";
	public static String password = "";
	
	@Test
	public void OutLookLogin(){
		Connection con = null;
		Statement stmt = null;
		
		try {
			Class.forName(JDBC_driver);
			System.out.println("Connecting to DataBase");
			con = DriverManager.getConnection(DB_URL, username, password);
			stmt = con.createStatement();
			String sql = "select * from outlook";
			ResultSet rs = stmt.executeQuery(sql);
			ArrayList<OutLook> outlookArray = new ArrayList<OutLook>();
			OutLook outlook = null;
			while (rs.next()) {
				outlook = new OutLook();
				outlook.id = rs.getInt("id");
				outlook.username = rs.getString("username");
				outlook.password = rs.getString("password");
				outlookArray.add(outlook);
			}
			for(OutLook item : outlookArray){
				System.out.println(item.id +" , "+item.username+" , "+item.password);
				System.setProperty("webdriver.chrome.driver", "F:\\Eclipse_Selenium\\Java_Selenium_Maven\\chromedriver_win32\\chromedriver.exe");
				driver = new ChromeDriver();
				driver.get(baseURL);
				driver.findElement(By.id("username")).sendKeys(item.username);
				driver.findElement(By.id("password")).sendKeys(item.password);
				driver.findElement(By.xpath("//*[@id='lgnDiv']/div[9]/div/span")).click();
				Thread.sleep(10000);				
				String currentURL = driver.getCurrentUrl();
				System.out.println("CurrentURL : "+currentURL);
				if(currentURL.equals("https://email.i-dbo.com/owa/#path=/mail")){
					//new mail click
					driver.findElement(By.xpath("//*[@id='_ariaId_37']")).click();
					Thread.sleep(20000);
					//To Address
					driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div[3]/div/div[1]/div[6]/div/div/div[3]/div[2]/div[1]/div[3]/div[1]/div/div/div/span/span[1]/form/input")).sendKeys("Nirmala@ProConstructor.com");
					Thread.sleep(2000);
					//Subject
					driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div[3]/div/div[1]/div[6]/div/div/div[3]/div[2]/div[1]/div[7]/div/div/input")).sendKeys("Sample Mail");
					Thread.sleep(2000);
					//body
					//driver.findElement(By.xpath("//*[@id='MicrosoftOWAEditorRegion']")).sendKeys("Body");
					//Thread.sleep(2000);
					//send button click
					driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div[3]/div/div[1]/div[6]/div/div/div[2]/div[1]/span[1]/div[1]/button[1]")).click();
					System.out.println("Success");
				}
				else{
					System.out.println("Failure : Wrong Credentials");
				}
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