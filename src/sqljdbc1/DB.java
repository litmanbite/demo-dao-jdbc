package sqljdbc1;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {

	private static Connection c = null;

	public static Connection getConn() {
		if (c == null) {
			try {
				Properties pp = loadProperties();
				String url = pp.getProperty("dburl");
				c = DriverManager.getConnection(url, pp);
			} catch (SQLException e) {
				throw new DbExcep(e.getMessage());
			}
		}
		return c;
	}

	public static void closeConn() {
		if (c != null) {
			try {
				c.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				throw new DbExcep(e.getMessage());
			}
		}
	}

	private static Properties loadProperties() {

		try (FileInputStream fs = new FileInputStream("db.properties")) {
			Properties p = new Properties();
			p.load(fs);
			return p;
		} catch (IOException e) {
			throw new DbExcep(e.getMessage());
		}
	}
	
	public static void closeStatement(Statement st) {
		if (st != null) {
			try {
				st.close();			
		}
			catch(SQLException e) {
				throw new DbExcep(e.getMessage());
			}
		
	}
}
		
		public static void closeResultSet(ResultSet rs) {
			if (rs != null) {
				try {
					rs.close();			
			}
				catch(SQLException e) {
					throw new DbExcep(e.getMessage());
				}
			
		}
	}
}
