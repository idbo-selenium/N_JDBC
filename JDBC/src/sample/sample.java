package sample;

	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.ResultSet;
	import java.sql.Statement;
	import java.util.ArrayList;

import org.junit.Test;
import org.openqa.selenium.By;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.firefox.FirefoxDriver;

	public class sample {
	
		
		static final String Jdbc_Driver="com.mysql.jdbc.Driver";
		static final String DB="jdbc:mysql://localhost:3306/Test_Sele1";
		static final String DB_Username="root";
		static final String DB_password="pcadmin";
		
		public static WebDriver  driver;
		
		
		public static String Un;
		public static String pwd;
	    public static ArrayList<String>al= new ArrayList<String>();
	    public static ArrayList<String>al1=new ArrayList<String>();		
		
		public static void  Readingdata(){			
			Connection conn=null;
			Statement stmt=null;			
			try{
				Class.forName("com.mysql.jdbc.Driver");
				System.out.println("connecting DB....");				
				conn=DriverManager.getConnection(DB,DB_Username,DB_password);
				stmt=conn.createStatement();				
				String sql;				
				sql="Select  Username,Password from Tlogin";				
				ResultSet rs=stmt.executeQuery(sql);
				while(rs.next()){
					//int id=rs.getInt("ID");
					Un=rs.getString("Username");
					pwd=rs.getString("password");
					al.add(Un);
					al1.add(pwd);
				}				
			}
			catch(Exception e){
				e.printStackTrace();
			}		
		}		 
		public static  void printingcredentials(){			
			for(int i=0; i<al.size();i++){
				System.out.println("un is "+al.get(i));
				if(i<=al1.size()){
					System.out.println("pwdis :"+al1.get(i));
				}
			}
		}		
		public static void logintrello() throws InterruptedException{
			System.out.println("size of al1 :"+al1.size());
			System.out.println("size of al :"+al.size());
			driver= new FirefoxDriver();
			for(int i=0;i<al.size();i++){
				driver.get("https://trello.com/login");
				String curl="https://trello.com/";
				Thread.sleep(3000);
				System.out.println("username is :"+al.get(i));
				Thread.sleep(2000);
				driver.findElement(By.id("user")).sendKeys(al.get(i));			
				//String expectedtxt="Invalid password";			
				if(i<al1.size()){
					System.out.println("pwd :"+al1.get(i));
					driver.findElement(By.id("password")).sendKeys(al1.get(i));
					driver.findElement(By.id("login")).click();
					Thread.sleep(5000);
					if(driver.getCurrentUrl().equals(curl)){
						System.out.println(driver.getCurrentUrl());
						System.out.println("valid ");
						Thread.sleep(5000);
						
						driver.findElement(By.xpath("/html/body/div[3]/div[1]/div[4]/a[2]")).click();
						if(driver.findElement(By.xpath("/html/body/div[5]/div/div[2]/div/div/div/ul[3]/li/a")).isDisplayed()){
							driver.findElement(By.xpath("/html/body/div[5]/div/div[2]/div/div/div/ul[3]/li/a")).click();
						}
					}
					else if(!driver.getCurrentUrl().equals(curl)){						  
						System.out.println("invalid");
					}
				}
			}
		}
		
		@Test
		public void PrintAll() throws InterruptedException{
//			Db_Driven_Logins.Readingdata();
//			//Db_Driven_Logins.printingcredentials();			
//		    Db_Driven_Logins.logintrello();
			
		}
	}