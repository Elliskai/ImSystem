package action.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class DbUtil {
	private static final String DB_HOST = "127.0.0.1";
	private static final String DB_NAME = "rezodb";
	private static final String DB_USER = "rezouser";
	private static final String DB_PASS = "Rezo_0000";

	private static final String DBMS = "mysql";
	private static final String DB_DRIVER = "com." + DBMS + ".cj.jdbc.Driver";
	private static final String DB_PORT = "3306";
	private static final String DB_URL = "jdbc:" + DBMS + "://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;

	private DbUtil() {
	}

	private static Connection con;

	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName(DB_DRIVER);
		Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
		return con;
	}

	public static void closeStatement(PreparedStatement DbStatement) throws SQLException {
		if (DbStatement != null) {
				DbStatement.close();
		}
	}

	public static void closeConnection(Connection clCon) throws SQLException {
		if (clCon != null) {
			clCon.close();
		}
	}
}
