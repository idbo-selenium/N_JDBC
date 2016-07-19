package JDBC_Practice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import sample.Gmail;

public class Gmail_Login {

	WebDriver driver;
	public static String JDBC_Driver = "com.mysql.jdbc.Driver";
	public final String DB_URL = "jdbc:mysql://localhost:3306/selenium";
	public String user = "root";
	public String pass = "";
	
	public void ExplicitWaitForAnElementToBeVisible(WebDriver driver,String xpath){
		(new WebDriverWait(driver, 5)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
	}
	
	@Test
	public void Login_Gmail(){
		Connection con = null;
		Statement stmt = null;
		try {
			Class.forName(JDBC_Driver);
			con = DriverManager.getConnection(DB_URL, user, pass);
			stmt = con.createStatement();
			String sql = "select * from gmail";
			ResultSet rs = stmt.executeQuery(sql);
			ArrayList<Gmail> gmailArray = new ArrayList<Gmail>();
			Gmail gmail = null;
			while (rs.next()){
				gmail = new Gmail();
				gmail.id = rs.getInt("ID");
				gmail.username = rs.getString("username");
				gmail.password = rs.getString("pasword");				
				gmailArray.add(gmail);
			}
			for(Gmail item : gmailArray){
				System.out.println(item.id+" , "+item.username+" , "+item.password);
				driver = new FirefoxDriver();
				driver.navigate().to("https://accounts.google.com/ServiceLogin?sacu=1&scc=1&continue=https%3A%2F%2Fmail.google.com%2Fmail%2F%3Ftab%3Dwm&hl=en&service=mail#identifier");
				Thread.sleep(4000);
				driver.findElement(By.id("Email")).sendKeys(item.username);
				driver.findElement(By.id("next")).click();
				try{
					ExplicitWaitForAnElementToBeVisible(driver,"/html/body/div/div[2]/div[2]/div[1]/form/div[2]/div/div[2]/div/div/input[2]");
					driver.findElement(By.id("Passwd")).sendKeys(item.password);
					driver.findElement(By.id("signIn")).click();
					try{						
						ExplicitWaitForAnElementToBeVisible(driver,"/html/body/div[7]/div[3]/div/div[2]/div[1]/div[1]/div[1]/div[2]/div/div/div[1]/div/div");
						driver.findElement(By.xpath("/html/body/div[7]/div[3]/div/div[1]/div[4]/div[1]/div[1]/div[1]/div[2]/div[4]/div[1]/a/span")).click();
						driver.findElement(By.xpath("/html/body/div[7]/div[3]/div/div[1]/div[4]/div[1]/div[1]/div[1]/div[2]/div[4]/div[2]/div[3]/div[2]/a")).click();
						System.out.println("Successfully Signout");
						stmt = con.createStatement();
						String update = "update gmail set status = 'Successfully SignOut' where id = "+item.id;
						stmt.executeUpdate(update);
					}
					catch(Exception e){
						stmt = con.createStatement();
						String update = "update gmail set status = 'Wrong Password' where id = "+item.id;
						stmt.executeUpdate(update);
						System.out.println("Wrong Password");
					}
				}
				catch(Exception e){
					stmt = con.createStatement();
					String update = "update gmail set status = 'Invalid user' where id = "+item.id;
					stmt.executeUpdate(update);
					System.out.println("Invalid User");
				}
				driver.close();
			}	
			con.close();
			stmt.close();
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