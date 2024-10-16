package swingapp;

import java.sql.*;

import javax.swing.JOptionPane;

public class ConnectDB {
	private Connection con;
	private String url, username, password;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;

	public ConnectDB() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			url = "jdbc:sqlserver://DESKTOP-NN2JUAV\\SQLEXPRESS:1433;databaseName=LANGUAGE;encrypt=true;trustServerCertificate=true";
			username = "sa";
			password = "123456789";
			con = null;
			preparedStatement = null;
			resultSet = null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Connection connectDB() {
		try {
			con = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	public ResultSet getLanguage(String tableName) {
		try {
			String sql = "SELECT * FROM " + tableName;
			preparedStatement = connectDB().prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	public void addLanguage(int ID, String name, int r_year, String author, String usage, String other, String type)
			throws SQLException {
		String tableName;
		if (type.toLowerCase().contains("python")) {
			tableName = "PythonLanguage";
		} else
			tableName = "JavaLanguage";
		String sql = "INSERT INTO " + tableName + "(ID, Name, ReleaseYear, Author, Usage, ";
		if (tableName.toLowerCase().contains("python")) {
			sql += "DynamicTyped) VALUES (?, ?, ?, ?, ?, ?)";
			preparedStatement = connectDB().prepareStatement(sql);
			preparedStatement.setInt(1, ID);
			preparedStatement.setString(2, name.trim());
			preparedStatement.setInt(3, r_year);
			preparedStatement.setString(4, author.trim());
			preparedStatement.setString(5, usage.trim());
			preparedStatement.setBoolean(6, other.toLowerCase().equalsIgnoreCase("yes"));
		} else {
			sql += "JdkVersion) VALUES (?, ?, ?, ?, ?, ?)";
			preparedStatement = connectDB().prepareStatement(sql);
			preparedStatement.setInt(1, ID);
			preparedStatement.setString(2, name.trim());
			preparedStatement.setInt(3, r_year);
			preparedStatement.setString(4, author.trim());
			preparedStatement.setString(5, usage.trim());
			preparedStatement.setString(6, other.trim());
		}
		preparedStatement.executeUpdate();
	}

	public void deleteLaguage(String tableName, int id) throws SQLException {
		String sql = "DELETE FROM " + tableName + " WHERE ID = " + id;
		preparedStatement = connectDB().prepareStatement(sql);
		preparedStatement.executeUpdate();
	}

	public ResultSet searchLanguage(String tableName, String column, String toFind) throws SQLException {
		String sql = "SELECT * FROM " + tableName + " WHERE " + column + " = ?";
		preparedStatement = connectDB().prepareStatement(sql);
		if(column.contains("Year")) {
			preparedStatement.setInt(1, Integer.parseInt(toFind));
		}
		else {preparedStatement.setString(1, toFind);}
		
		resultSet = preparedStatement.executeQuery();
		return resultSet;
	}

//	public static void main(String[] args) {
//		ConnectDB cdb = new ConnectDB();
//		cdb.addLanguage("PythonLanguage","Python3",2014,"MyBoi","Data_Sci","YEs");
//		ResultSet java = cdb.getLanguage("PythonLanguage");
//		try {
//			while (java.next()) {
//				System.out.println(java.getString(1));
//				System.out.println(java.getInt(2));
//				System.out.println(java.getString(3));
//				System.out.println(java.getString(4));
//				System.out.println(java.getString(5));
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
}
