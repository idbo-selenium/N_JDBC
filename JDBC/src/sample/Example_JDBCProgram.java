package sample;

import java.sql.*;
import org.junit.Test;

public class Example_JDBCProgram {
	
	static final String JDBC_Driver = "com.mysql.jdbc.Driver";
	static final String JDBC_url = "jdbc:mysql://localhost:3306/selenium";
	static final String username = "root";
	static final String password = "";
	
	@Test
	public void SampleProgram() throws SQLException {
	//public static void main(String args[]) throws SQLException{
		Connection connection = null;
		Statement statement = null;
		try{
			Class.forName(JDBC_Driver);
			System.out.println("connection successfully opened");
			connection = DriverManager.getConnection(JDBC_url, username, password);
			statement = connection.createStatement();
			
			//Insert record into DB
			//String sqlInsert = "insert into login(Username,Password,Mailid) values('Swetha','123456','palvaiswethareddy@gmail.com')";
			//statement.executeUpdate(sqlInsert);
			
			//Update record into DB
			//String sqlUpdate = "Update Login set Password = '123456' where Password = '23456'";
			//statement.executeUpdate(sqlUpdate);
			
			//Delete record into DB
			//String sqlDelete = "Delete from Login where username = 'Harika'";
			//statement.executeUpdate(sqlDelete);
			
			//Alter column name
			//String sqlAlter = "Alter table login change 'username' 'name'";
			//statement.executeUpdate(sqlAlter);
			
			String sqlSelect = "Select * from login";
			ResultSet resultSet = statement.executeQuery(sqlSelect);
			while (resultSet.next()) {
				int id = resultSet.getInt("ID");
				String username = resultSet.getString("username");
				String password = resultSet.getString("password");
				String mailid = resultSet.getString("mailid");				
				System.out.println("ID : "+id+" , Username : "+username+" , Password : "+password+" , MailID : "+mailid);
			}
		}
		catch(Exception e){
		    e.printStackTrace();
		}
		finally{
			if(connection!=null)
	            connection.close();
	    }
	}
}