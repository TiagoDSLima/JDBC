package application;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;
import db.DbException;

public class Program {

	public static void main(String[] args) {
		
		Connection con = null;
		Statement st = null;
		
		try {
			con = DB.getConnection();
			
			con.setAutoCommit(false);
			
			st = con.createStatement();
			
			int rows1 = st.executeUpdate("UPDATE seller SET BaseSalary = 5090 WHERE DepartmentId = 1");
			
			int x = 1;
			if(x < 2) {
				throw new SQLException("Fake Error");
			}
			
			int rows2 = st.executeUpdate("UPDATE seller SET BaseSalary = 6090 WHERE DepartmentId = 2");
			
			con.commit();
			
			System.out.println("Done! Rows affected: " + rows1);
			System.out.println("Done! Rows affected: " + rows2);
			
		} catch(SQLException e) {
			try {
				con.rollback();
				throw new DbException("Transaction rolled back! Caused by: " + e.getMessage());
			} catch (SQLException e1) {
				e1.printStackTrace();
				throw new DbException("Error trying to rollback! Caused by: " + e1.getMessage());
			}
		} finally {
			DB.closeStatement(st);
			DB.closeConnection();
		}
	}

}
