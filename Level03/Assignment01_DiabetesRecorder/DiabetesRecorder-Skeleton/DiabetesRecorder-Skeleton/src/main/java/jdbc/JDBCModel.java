package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * CST_8288_21F Assignment 1
 * 
 * @author Simon Ao 040983402
 * @version July 4,2021
 * @References: Professor's tutorials
 */
public class JDBCModel {

	private static final String[] COL_NAMES_GOLUCO = { "Id", "EntryType", "GlucoseValue", "TakenAt" };

	private static final String[] COL_NAMES_ACCOUNT = { "Name", "YearOfBirth", "Weight" };

	private static final String QUERY_ENTRYTYPE_SELECT = "SELECT EntryType FROM entrytype";

	private static final String QUERY_ACCOUNT_SELECT = "SELECT Name,YearOfBirth,Weight FROM account where id=?";

	private static final String QUERY_ACCOUNT_UPDATE = "UPDATE account SET Name = ?, YearOfBirth = ?, Weight = ? WHERE Id = ?";

	private static final String QUERY_VALIDATE = "SELECT AccountId FROM security where username=? and password=?";

	private static final String QUERY_GLUCOSE_INSERT = "INSERT INTO glucosevalue(EntryTypeId,AccountId,GlucoseValue,"
			+ "TakenAt)VALUES((select id from entrytype where entrytype=?),?,?,now())";

	private static final String QUERY_GLUCOSE_NUMBER = "SELECT g.Id,(SELECT EntryType FROM entrytype where g.EntryTypeId=Id)"
			+ " as EntryType,g.GlucoseValue,g.TakenAt FROM glucosevalue g where AccountId=?";

	private Connection con;
	private String url;

	private String user = "cst8288";
	private String pass = "8288";

	/** Return list instance of COL_NAMES_GOLUCO */
	public List<String> getColumnNames() {
		return Arrays.asList(COL_NAMES_GOLUCO);
	}

	/**
	 * True is the working status of the connection,false is not
	 * 
	 * @return isConnected turn or false
	 */
	public boolean isConnected() {
		try {
			return con != null && !con.isClosed() && con.isValid(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Set user,pass to credentials for database connection
	 * 
	 * @param user
	 * @param pass
	 */

	public void setCredentials(String user, String pass) {
		this.user = user;
		this.pass = pass;
	}

	/**
	 * If not connected database,call DriverManager.getConnection
	 * 
	 * @param url
	 */
	public void connectTo(String url) {
		this.url = url;
		try {
			close();
			con = DriverManager.getConnection(url, user, pass);
			con.setAutoCommit(false);
		} catch (SQLException e) {
			con = null;
			throw new RuntimeException("Connection Failed", e);
		}

	}

	/**
	 * Connection test
	 * 
	 * @return No connection is false, connection is true
	 * @throws SQLException
	 */
	public boolean hasValidConnection() throws SQLException {
		if (con == null)
			throw new SQLException("There is no connection.");
		if (con.isClosed())
			throw new SQLException("Connection closed");
		if (!con.isValid(0))
			throw new SQLException("Connection failure");
		return true;
	}

	/**
	 * Added a new glucose value
	 * 
	 * @param entryType
	 * @param activeAccountId
	 * @param glucose
	 */
	public void addGlucoseValue(String entryType, int activeAccountId, double glucose) {
		try {
			if (hasValidConnection()) {
				PreparedStatement statement = con.prepareStatement(QUERY_GLUCOSE_INSERT);
				statement.setString(1, entryType);
				statement.setInt(2, activeAccountId);
				statement.setDouble(3, glucose);
				statement.executeUpdate();
				con.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Get all the entry types from the database
	 * 
	 * @return entry type list
	 */
	List<String> getEntryTypes() {
		List<String> list = new ArrayList<>();
		try {
			if (hasValidConnection()) {
				PreparedStatement statement = con.prepareStatement(QUERY_ENTRYTYPE_SELECT);
				ResultSet result = statement.executeQuery();
				while (result.next()) {
					list.add(result.getString(1));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * Get account information for the id
	 * 
	 * @param activeAccountId
	 * @return list
	 */
	List<List<String>> getAccountInfoFor(int activeAccountId) {
		List<List<String>> list = new ArrayList<>();
		try {
			if (hasValidConnection()) {
				PreparedStatement statement = con.prepareStatement(QUERY_ACCOUNT_SELECT);
				statement.setInt(1, activeAccountId);
				ResultSet result = statement.executeQuery();
				while (result.next()) {
					List<String> r = new ArrayList<String>();
					list.add(r);
					r.add(result.getString(COL_NAMES_ACCOUNT[0]));
					r.add(result.getString(COL_NAMES_ACCOUNT[1]));
					r.add(result.getString(COL_NAMES_ACCOUNT[2]));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	};

	/**
	 * Update account information
	 * 
	 * @param name
	 * @param yob
	 * @param weight
	 * @param activeAccountId
	 */
	public void updateInfo(String name, String yob, String weight, int activeAccountId) {
		try {
			if (hasValidConnection()) {
				PreparedStatement statement = con.prepareStatement(QUERY_ACCOUNT_UPDATE);
				statement.setString(1, name);
				statement.setString(2, yob);
				statement.setString(3, weight);
				statement.setInt(4, activeAccountId);
				statement.executeUpdate();
				con.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Account login
	 * 
	 * @param username
	 * @param password
	 * @return accountId
	 */
	public int loginWith(String username, String password) {
		try {
			if (hasValidConnection()) {
				PreparedStatement statement = con.prepareStatement(QUERY_VALIDATE);
				statement.setString(1, username);
				statement.setString(2, password);
				ResultSet rs = statement.executeQuery();
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
		} catch (Exception e) {

			System.out.printf("username=%s, pass=%s\n", username, password);
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * Get all glucose records for id
	 * 
	 * @param activeAccountId
	 * @return list
	 */
	List<List<Object>> getAllGlucoseNumbers(int activeAccountId) {
		List<List<Object>> list = new ArrayList<>();
		try {
			if (hasValidConnection()) {
				PreparedStatement statement = con.prepareStatement(QUERY_GLUCOSE_NUMBER);
				statement.setInt(1, activeAccountId);
				ResultSet result = statement.executeQuery();
				while (result.next()) {
					List<Object> r = new ArrayList<Object>();
					list.add(r);
					r.add(result.getObject(1));
					r.add(result.getObject(2));
					r.add(result.getObject(3));
					r.add(result.getObject(4));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * Close connection
	 */
	public void close() {
		if (con != null) {
			try {
				con.close();
			} catch (Exception e) {
				con = null;
			}
		}
	}
}
