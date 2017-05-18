package com.michal.myCV.dao;

import com.michal.myCV.model.Education;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EducationDaoPostgresql implements EducationDao {
	private JDBCPostgres database;
	private Connection databaseConnection;
	private PreparedStatement statement;

	public EducationDaoPostgresql(JDBCPostgres database){
		this.database = database;
		this.databaseConnection = this.database.getConnection();
	}

	public void add(Education education) {
		try{
			String addStatement = String.format("INSERT INTO education(id, name, description, date_from, date_to, id_user) " +
			 "VALUES(%d,'%s','%s','%s','%s',%d)", education.getId(), education.getName(), education.getDescription(),
			 education.getDateFrom(), education.getDateTo(),education.getIdUser());
			statement = databaseConnection.prepareStatement(addStatement);
			database.executeUpdateQuery(statement);
		} catch(NullPointerException | SQLException e){
			System.err.println(e.getMessage());
		}
	}

	public void update(Education education) {
		try {
			String updateStatement = String.format("UPDATE education " +
			  " SET name = '%s', description = '%s', date_from = '%s', date_to = '%s', id_user = %d WHERE id = %d",
			 education.getName(), education.getDescription(), education.getDateFrom(), education.getDateTo(),
			 education.getIdUser(), education.getId());
			statement = databaseConnection.prepareStatement(updateStatement);
			database.executeUpdateQuery(statement);
		} catch (SQLException | NullPointerException e) {
			System.err.println(e.getMessage());
		}
	}

	public Education find(Integer educationId){
		Education education = null;
		try{
			String queryStatement = String.format("SELECT * FROM education WHERE id = %d", educationId);
			statement = databaseConnection.prepareStatement(queryStatement);
			ResultSet result = database.executeSelectQuery(statement);
			while(result.next()){
				education = new Education(result.getString("name"),
				 result.getString("description"),LocalDate.parse(result.getString("date_from")),
				 LocalDate.parse(result.getString("date_to")), result.getInt("id_user"));
				education.setId(result.getInt("id"));
				return education;
			}
		} catch(NullPointerException | SQLException e){
			System.err.println(e.getMessage());
		}
		return education;
	}

	public List<Education> getAllForPerson(Integer personId){
		List<Education> educationList = new ArrayList<>();
		try{
			String queryStatement = String.format("SELECT * FROM education WHERE id_user = %d ORDER BY date_to DESC",
			 personId);
			statement = databaseConnection.prepareStatement(queryStatement);
			ResultSet result = database.executeSelectQuery(statement);
			while(result.next()){
				Education edu = new Education(result.getString("name"),
				 result.getString("description"),LocalDate.parse(result.getString("date_from")),
				LocalDate.parse(result.getString("date_to")), result.getInt("id_user"));
				edu.setId(result.getInt("id"));
				educationList.add(edu);
			}
		} catch(NullPointerException | SQLException e){
			System.err.println(e.getMessage());
			System.out.println(e);
		}
		return educationList;
	}

	public void remove(Integer educationId){
		try{
		String deleteStatement = String.format("DELETE FROM education WHERE id = %d", educationId);
		statement = databaseConnection.prepareStatement(deleteStatement);
		database.executeUpdateQuery(statement);
		} catch (SQLException | NullPointerException e){
			System.err.println(e.getMessage());
		}

	}

	public Integer getNewId(){
		Integer newId = 0;
		try{
			String selectStatement = "SELECT id FROM education ORDER BY id DESC LIMIT 1";
			statement = databaseConnection.prepareStatement(selectStatement);
			ResultSet result = database.executeSelectQuery(statement);
			while(result.next()) {
				newId = result.getInt("id");
			}
			newId++;
			return newId;
		} catch(NullPointerException | SQLException e){
			System.err.println(e.getMessage());
			return newId;
		}
	}
}
