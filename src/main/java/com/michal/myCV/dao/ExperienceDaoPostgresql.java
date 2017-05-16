package com.michal.myCV.dao;


import com.michal.myCV.model.Experience;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExperienceDaoPostgresql implements ExperienceDao {
	private JDBCPostgres database = new JDBCPostgres();

	public void add(Experience experience) {
		try{
			SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
			Integer id = experience.getId();
			String name = experience.getName();
			String description = experience.getDescription();
			LocalDate dateFrom = experience.getDateFrom();
			LocalDate dateTo = experience.getDateTo();
			Integer userId = experience.getIdUser();
			String addStatement = String.format("INSERT INTO experience (id, name, description, date_from, date_to, user_id) VALUES(%d,'%s','%s','%s','%s',%d)", id, name, description, dateFrom, dateTo, userId);
			System.out.println(addStatement);
			database.executeUpdateQuery(addStatement);
		} catch(NullPointerException e){
			System.err.println(e.getMessage());
		}
	}

	public List<Experience> getAllForPerson(int id){
		List<Experience> experienceList = new ArrayList<>();
		try{
			String queryStatement = String.format("SELECT * FROM experience WHERE id = %d", id);
			ResultSet result = database.executeSelectQuery(queryStatement);
			while(result.next()){
				Experience exp = new Experience(result.getString("name"), result.getString("description"),LocalDate.parse(result.getString("date_from")),
				 LocalDate.parse(result.getString("date_to")), result.getInt("user_id"));
				exp.setId(result.getInt("id"));
				experienceList.add(exp);
			}
			return experienceList;

		} catch(NullPointerException | SQLException e){
			System.err.println(e.getMessage());
			return experienceList;
		}
	}

	public void remove(int id){
		String deleteStatement = String.format("DELETE FROM experience WHERE id = %d", id);
		database.executeUpdateQuery(deleteStatement);
	}

	public Integer getNewId(){
		Integer newId = 0;
		try{
			String selectStatement = "SELECT id FROM experience ORDER BY id DESC LIMIT 1";
			ResultSet result = database.executeSelectQuery(selectStatement);
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
