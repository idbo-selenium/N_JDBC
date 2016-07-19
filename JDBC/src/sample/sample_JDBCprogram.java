package sample;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class sample_JDBCprogram {
	
	public static String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost:3306/selenium";	
	static final String user = "root";
	static final String pass = "";	
	public static int id;
	public static String username;
	public static String password;
	public static String mailid;
	public static WebDriver driver;
	@Test
	public void sampleMethod() throws SQLException{
		Connection conn = null;
	    Statement stmt = null; 	    
		try{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Connecting to database...");
		    conn = DriverManager.getConnection(DB_URL,user,pass);
		    stmt = conn.createStatement();
		    String sql;
		    sql = "SELECT * FROM login";
		    ResultSet rs = stmt.executeQuery(sql);
		    ArrayList<Credentials> al = new ArrayList<Credentials>();
		    Credentials credentials = null;
		    while(rs.next()){			    	
		    	credentials = new Credentials();
		        credentials.id  = rs.getInt("ID");
		        credentials.username = rs.getString("username");
		        credentials.password = rs.getString("password");
		        credentials.mailid = rs.getString("mailid");
		        al.add(credentials);
		    }
		    for(Credentials item : al){
		        System.out.println("ID : "+id+" , Username : "+item.username+" , password : "+item.password+" , mailid : "+item.mailid);
	    	    driver = new FirefoxDriver();
	    	    driver.manage().window().maximize();
	    		driver.get("http://example.local/auth/login");
	    		driver.findElement(By.xpath("/html/body/div/div/div/div/div[2]/form/div[1]/div/input")).sendKeys(item.mailid);
	    		driver.findElement(By.xpath("/html/body/div/div/div/div/div[2]/form/div[2]/div/input")).sendKeys(item.password);
	    		driver.findElement(By.xpath("html/body/div[1]/div/div/div/div[2]/form/div[5]/div/button")).click();
	    		Thread.sleep(4000);
	    		driver.findElement(By.xpath(".//*[@id='bs-example-navbar-collapse-1']/ul[2]/li/a")).click();
	    		driver.findElement(By.xpath(".//*[@id='bs-example-navbar-collapse-1']/ul[2]/li/ul/li[2]/a")).click();
	    		Thread.sleep(4000);
	    		driver.close();
		    }
		}
		catch(Exception e){
		    e.printStackTrace();
		}
		finally{
			if(conn!=null)
	            conn.close();
	    }
	}
}