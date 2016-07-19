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
import sample.Mercury;

public class Mercury_Login {

	public static WebDriver driver;
	public static String base_URL = "http://newtours.demoaut.com/";
	public static String JDBC_Driver = "com.mysql.jdbc.Driver";
	public static String DB_URL = "jdbc:mysql://localhost:3306/selenium";
	public static String user = "root";
	public static String password = "";
	
	@Before
	public void Init(){
		System.setProperty("webdriver.chrome.driver", "F:\\Eclipse_Selenium\\Java_Selenium_Maven\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
//		System.setProperty("webdriver.safari.driver", "C:\\Users\\sravan\\Downloads\\IEDriverServer_Win32_2.53.0\\IEDriverServer.exe");
//		driver = new SafariDriver();
	}
	
	@After
	public void Cleanup(){
		//driver.close();
	}
	
	@Test
	public void MercuryLogin(){
		
		Connection con;
		Statement stmt;
		try {
			Class.forName(JDBC_Driver);
			con = DriverManager.getConnection(DB_URL, user, password);
			stmt = con.createStatement();
			String sql = "select * from mercury";
			ResultSet rs = stmt.executeQuery(sql);
			Mercury mercury = null;
			ArrayList<Mercury> arrayList = new ArrayList<Mercury>();
			while(rs.next()){
				mercury = new Mercury();
				mercury.id = rs.getInt("ID");
				mercury.username = rs.getString("username");
				mercury.password = rs.getString("password");
				arrayList.add(mercury);
			}
			for(Mercury item : arrayList){
				System.out.println(item.id+" , "+item.username+" , "+item.password);
				driver.get(base_URL);
				//signon button click
				driver.findElement(By.xpath("/html/body/div/table/tbody/tr/td[2]/table/tbody/tr[2]/td/table/tbody/tr/td[1]/a")).click();
				Thread.sleep(2000);
				//enter username
				driver.findElement(By.name("userName")).sendKeys(item.username);
				//enter password
				driver.findElement(By.name("password")).sendKeys(item.password);
				//click submit
				driver.findElement(By.name("login")).click();
				Thread.sleep(2000);
				String URL = driver.getCurrentUrl();System.out.println(URL);
				if(URL.equals("http://newtours.demoaut.com/mercuryreservation.php")){
					driver.findElement(By.xpath("/html/body/div/table/tbody/tr/td[2]/table/tbody/tr[2]/td/table/tbody/tr/td[1]/a")).click();
					System.out.println("Success");
				}
				else{
					System.out.println("Invalid user");
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