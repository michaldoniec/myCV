package com.michal.myCV.dao;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;


public class JDBCPostgres {

	private Connection connection = null;
	private Statement statement = null;
	private ResultSet resultSet = null;

	public JDBCPostgres() {

		try {
			Class.forName("org.postgresql.Driver");
			this.connection = generateConnection();
			this.statement = connection.createStatement();
			this.connection.setAutoCommit(false);
		} catch ( SQLException  | ClassNotFoundException e) {
			System.err.println(e.getMessage());
		}
	}

	public Connection getConnection() {
		return this.connection;
	}

	public void closeDatabase() {
		try {
			clearStatementAndResultSet();
			this.connection.close();
		} catch ( SQLException e) {
			System.err.println( e.getMessage() );
		}
	}

	void executeUpdateQuery(PreparedStatement query) {
		try {
			query.executeUpdate();
			this.connection.commit();
			query.close();
		} catch ( SQLException e) {
			System.err.println( e.getMessage() );
		}
	}

	ResultSet executeSelectQuery(PreparedStatement query) {
		try {
			this.resultSet = query.executeQuery();
		} catch ( SQLException e) {
			System.err.println( e.getMessage() );
		}
		return this.resultSet;
	}


	private void clearStatementAndResultSet() throws SQLException {
		if (this.statement != null) {
			this.statement.close();
		}
		if (this.resultSet != null) {
			this.resultSet.close();
		}
	}

	private static Connection generateConnection() throws SQLException {

		String username = "uyavzffasatvqz";
		String password = "6f7124941b02f877b063682de2bf9dfd694628b196cea23620e7b6f613ff5470";
		String dbUrl = "jdbc:postgresql://ec2-54-197-232-155.compute-1.amazonaws.com:5432/d8e9dbe742mlk0";
		return DriverManager.getConnection(dbUrl, username, password);
	}
}
