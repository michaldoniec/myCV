package com.michal.myCV.dao;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.sql.*;


public class JDBCPostgres {

	private Connection connection = null;
	private Statement statement = null;
	private ResultSet resultSet = null;

	public JDBCPostgres() {

		try {
			Class.forName("org.postgresql.Driver");
			this.connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/michal", "postgres",
			 "MD24");
			this.statement = connection.createStatement();
			this.connection.setAutoCommit(false);
		} catch ( SQLException  | ClassNotFoundException e) {
			System.err.println(e.getMessage());
		}
	}

	public void closeDatabase() {
		try {
			clearStatementAndResultSet();
			this.connection.close();
		} catch ( SQLException e) {
			System.err.println( e.getMessage() );
		}
	}

	void executeUpdateQuery(String query) {
		try {
			this.statement.executeUpdate(query);
			this.statement.close();
			this.connection.commit();
		} catch ( SQLException e) {
			System.err.println( e.getMessage() );
		}
	}

	ResultSet executeSelectQuery(String query) {
		try {
			this.resultSet = this.statement.executeQuery(query);
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
}
