package com.michal.myCV.dao;


import com.michal.myCV.model.Experience;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExperienceDaoSQLite implements ExperienceDao {
	private JDBCSQLite database;
	private Connection databaseConnection;
	private PreparedStatement statement;

	public ExperienceDaoSQLite(JDBCSQLite database){
		this.database = database;
		this.databaseConnection = this.database.getConnection();
	}

	public void add(Experience experience) {
		try{
			String addStatement = String.format("INSERT INTO experience " +
			 "(id, name, description, date_from, date_to, user_id) VALUES(%d,'%s','%s','%s','%s',%d)",
			 experience.getId(), experience.getName(), experience.getDescription(),
			 experience.getDateFrom().toString(), experience.getDateTo().toString(), experience.getIdUser());
			statement = databaseConnection.prepareStatement(addStatement);
			database.executeUpdateQuery(statement);
		} catch(SQLException | NullPointerException e){
			System.err.println(e.getMessage());
		}
	}

	public void update(Experience experience){
		try{
			String updateStatement = String.format("UPDATE experience " +
			  " SET name = '%s', description = '%s', date_from = '%s', date_to = '%s', user_id = %d WHERE id = %d",
			 experience.getName(), experience.getDescription(), experience.getDateFrom().toString(),
			 experience.getDateTo().toString(), experience.getIdUser(), experience.getId());
			statement = databaseConnection.prepareStatement(updateStatement);
			database.executeUpdateQuery(statement);
		} catch(SQLException | NullPointerException e){
			System.err.println(e.getMessage());
		}
	}

	public List<Experience> getAllForPerson(Integer personId){
		List<Experience> experienceList = new ArrayList<>();
		try{
			String queryStatement = String.format("SELECT * FROM experience  WHERE user_id = %d ORDER BY date_to DESC",
			 personId);
			statement = databaseConnection.prepareStatement(queryStatement);
			ResultSet result = database.executeSelectQuery(statement);
			while(result.next()){
				Experience exp = new Experience(result.getString("name"),
				 result.getString("description"),LocalDate.parse(result.getString("date_from")),
				 LocalDate.parse(result.getString("date_to")), result.getInt("user_id"));
				exp.setId(result.getInt("id"));
				experienceList.add(exp);
			}
		} catch(NullPointerException | SQLException e){
			System.err.println(e.getMessage());
		}
		return experienceList;
	}

	public Experience find(Integer experienceId){
		Experience experience = null;
		try{
			String queryStatement = String.format("SELECT * FROM experience WHERE id = %d", experienceId);
			statement = databaseConnection.prepareStatement(queryStatement);
			ResultSet result = database.executeSelectQuery(statement);
			while(result.next()){
				experience = new Experience(result.getString("name"),
				 result.getString("description"),LocalDate.parse(result.getString("date_from")),
				 LocalDate.parse(result.getString("date_to")), result.getInt("user_id"));
				experience.setId(result.getInt("id"));
				return experience;
			}
		} catch(NullPointerException | SQLException e){
			System.err.println(e.getMessage());
		}
		return experience;
	}

	public void remove(Integer experienceId){
		try{
		String deleteStatement = String.format("DELETE FROM experience WHERE id = %d", experienceId);
		statement = databaseConnection.prepareStatement(deleteStatement);
		database.executeUpdateQuery(statement);
		} catch(SQLException | NullPointerException e){
			System.err.println(e.getMessage());
		}
	}

	public Integer getNewId(){
		Integer newId = 0;
		try{
			String selectStatement = "SELECT id FROM experience ORDER BY id DESC LIMIT 1";
			statement = databaseConnection.prepareStatement(selectStatement);
			ResultSet result = database.executeSelectQuery(statement);
			while(result.next()) {
				newId = result.getInt("id");
			}
			newId++;
			return  newId;
		} catch(NullPointerException | SQLException e){
			System.err.println(e.getMessage());
			return newId;
		}
	}
}
