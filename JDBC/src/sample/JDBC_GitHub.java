package sample;

import java.sql.*;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class JDBC_GitHub {

	public WebDriver driver;
	public static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/selenium";
	public String baseURL = "https://github.com/login";
	static final String user = "root";
	static final String pass = "";
	
	@Before
	public void Init(){
		driver = new FirefoxDriver();		
	}
	
	@After
	public void CleanUp(){
		driver.close();
	}
	
	@Test
	public void GitHub_Login(){
		Connection conn = null;
		Statement stmt = null;
		
		try {
			Class.forName(JDBC_DRIVER);
			System.out.println("Connecting to DataBase..... ");
			conn = DriverManager.getConnection(DB_URL, user, pass);
			stmt = conn.createStatement();
			String sql = "select * from github";
			ResultSet rs =  stmt.executeQuery(sql);
			ArrayList<GitHub> githubArray = new ArrayList<GitHub>();
			while(rs.next()){
				GitHub github = new GitHub();
				github.id = rs.getInt("id");
				github.username = rs.getString("username");
				github.password = rs.getString("password");
				//System.out.println(github.id + " , "+github.username+" , "+github.password);
				githubArray.add(github);
			}
			for (GitHub item : githubArray){
				System.out.println(item.id + " , "+item.username+" , "+item.password);
				driver.get(baseURL);
				driver.findElement(By.id("login_field")).sendKeys(item.username);
				driver.findElement(By.id("password")).sendKeys(item.password);
				driver.findElement(By.name("commit")).click();Thread.sleep(4000);
				String currentURL = driver.getCurrentUrl();
				System.out.println("currentURL : "+currentURL);
				if(currentURL.equals("https://github.com/session")){
					System.out.println("wrong details");
				}
				else{
					driver.findElement(By.xpath(".//*[@id='user-links']/li[3]/a")).click();
					driver.findElement(By.xpath(".//*[@id='user-links']/li[3]/div/div/form/button")).click();
					System.out.println("successfully logout");
				}
			}		
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
	}
}