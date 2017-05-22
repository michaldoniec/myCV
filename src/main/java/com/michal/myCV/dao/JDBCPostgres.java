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
		} catch ( SQLException  | ClassNotFoundException | URISyntaxException e) {
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

	private static Connection generateConnection() throws  URISyntaxException, SQLException {
		URI dbUri = new URI(System.getenv("DATABASE_URL"));
		String username = dbUri.getUserInfo().split(":")[0];
		String password = dbUri.getUserInfo().split(":")[1];
		String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
		return DriverManager.getConnection(dbUrl, username, password);
	}
}
