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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import sample.GitHub;

public class WriteJDBCData_ExcelSheet {

	public WebDriver driver;
	public static String baseURL = "";
	public static String JDBC_Driver ="com.mysql.jdbc.Driver";
	static final String DB_URL ="jdbc:mysql://localhost:3306/selenium";
	public static String username = "root";
	public static String password = "";
	
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
	public void GettingData_JDBC_InsertIntoExcelSheet(){
		Connection con;
		Statement stmt;
		
		try {
			Class.forName(JDBC_Driver);
			System.out.println("Connecting to DataBase..... ");
			con = DriverManager.getConnection(DB_URL, username, password);
			stmt = con.createStatement();
			String sql = "select * from github";
			ResultSet rs = stmt.executeQuery(sql);
			ArrayList<GitHub> arrayGithub = new ArrayList<GitHub>();
			GitHub github = null;
			while(rs.next()){
				github = new GitHub();
				github.id = rs.getInt("ID");
				github.username = rs.getString("username");
				github.password = rs.getString("password");
				arrayGithub.add(github);
			}
			for(GitHub item : arrayGithub){
				System.out.println(item.id +" , "+item.username +" , "+item.password);				
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