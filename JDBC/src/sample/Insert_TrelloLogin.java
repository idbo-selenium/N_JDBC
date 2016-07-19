package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.Test;

public class Insert_TrelloLogin {

	public final String JDBC_Driver = "com.mysql.jdbc.Driver";
	public final String JDBC_URL = "jdbc:mysql://localhost:3306/selenium";
	public final String username = "root";
	public final String password = "";
	@Test
	public void Insert_DB() throws SQLException{
		Connection con = null;
		Statement stmt = null;
		try {
			Class.forName(JDBC_Driver);System.out.println("Connecting to DB");
			con = DriverManager.getConnection(JDBC_URL, username, password);
			stmt = con.createStatement();
			String insertQuery = "insert into trellologin(username,password) values('abcd','1234')";
			stmt.executeUpdate(insertQuery);
			String selectQuery = "select * from trellologin";
			ResultSet rs = stmt.executeQuery(selectQuery);
			while(rs.next()){
				int ID = rs.getInt("ID");
				String username = rs.getString("username");
				String password = rs.getString("password");
				System.out.println("ID : "+ID+" , Username : "+username+" , Password : "+password);
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			if(con!=null)
	            con.close();
	    }
	}
}