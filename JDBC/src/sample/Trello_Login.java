package sample;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

public class Trello_Login {

	public static WebDriver driver;
	public static String baseURL = "https://trello.com/login";
	public static String JDBC_driver = "com.mysql.jdbc.Driver";
	public static final String DB_URL = "jdbc:mysql://localhost:3306/selenium";
	public static String user = "root";
	public static String pass = "";	
	public static String usr = null;
	public static String pwd = null;
	public static ArrayList<String> arrayUsername = new ArrayList<String>();
	public static ArrayList<String> arrayPassword = new ArrayList<String>();	
	public static Logger logger = Logger.getLogger(Trello_Login.class);
		
	@Before
	public void Init(){
		PropertyConfigurator.configure("log4j.properties");
		System.setProperty("webdriver.chrome.driver", "F:\\Eclipse_Selenium\\Java_Selenium_Maven\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
	}
	
	@After
	public void Cleanup(){
		driver.close();
	}
	
	public static void JDBC_Connection() throws SQLException{
		Connection conn = null;
		Statement stmt = null;
		String username = null;
		String password = null;
		
		try{
			Class.forName(JDBC_driver);
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL,user,pass);
			stmt = conn.createStatement();
			String sql = "Select username,password from trellologin";
			ResultSet rs = stmt.executeQuery(sql);		
			
			while(rs.next()){
				username = rs.getString("username");
				password = rs.getString("password");
				arrayUsername.add(username);
				arrayPassword.add(password);				
			}
			for(int i=0; i<arrayUsername.size();i++){
				usr = arrayUsername.get(i);
				//System.out.println(usr);
			}
			
			for(int i=0;i<arrayPassword.size();i++){
				pwd = arrayPassword.get(i);
				//System.out.println(pwd);
			}
			System.out.println(arrayUsername);
			System.out.println(arrayPassword);
		}
		
		catch(Exception e){
		    e.printStackTrace();
		}
		
		finally{
			if(conn!=null)
	            conn.close();
	    }		
	}
	
	@Test
	public void TrelloLogin() throws SQLException, InterruptedException{
		Trello_Login.JDBC_Connection();
		System.out.println("size of arrayUsername :"+arrayUsername.size());
		System.out.println("size of arrayPassword :"+arrayPassword.size());
		System.out.println(arrayUsername);
		for(int i=0;i<arrayUsername.size();i++){	
			driver.navigate().to(baseURL);
			driver.findElement(By.xpath("/html/body/section/div/div/div[3]/form/div/div[1]/input[1]")).sendKeys(arrayUsername.get(i));
			driver.findElement(By.xpath("/html/body/section/div/div/div[3]/form/div/div[1]/input[2]")).sendKeys(pwd);
			driver.findElement(By.xpath("/html/body/section/div/div/div[3]/form/div/input")).click();
			Thread.sleep(5000);
			if(!driver.getCurrentUrl().equals(baseURL)){
				driver.findElement(By.xpath("/html/body/div[3]/div[1]/div[4]/a[2]/span[2]")).click();
				driver.findElement(By.xpath("/html/body/div[5]/div/div[2]/div/div/div/ul[3]/li/a")).click();
				System.out.println("Successfully logged out");
			}
			else{
				System.out.println("credintials doesnot match");
			}
		}
	}
}