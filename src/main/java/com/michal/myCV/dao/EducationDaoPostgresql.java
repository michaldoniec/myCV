package com.michal.myCV.dao;

import com.michal.myCV.model.Education;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;

public class EducationDaoPostgresql implements EducationDao {
	private JDBCPostgres database = new JDBCPostgres();

	public void add(Education education) {
		try{
			SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
			Integer id = education.getId();
			String name = education.getName();
			String description = education.getDescription();
			LocalDate dateFrom = education.getDateFrom();
			LocalDate dateTo = education.getDateTo();
			Integer userId = education.getIdUser();
			String addStatement = String.format("INSERT INTO education (id, name, description, date_from, date_to, id_user) VALUES(%d,'%s','%s','%s','%s',%d)", id, name, description, dateFrom, dateTo, userId);
			System.out.println(addStatement);
			database.executeUpdateQuery(addStatement);
		} catch(NullPointerException e){
			System.err.println(e.getMessage());
		}
	}

	public List<Education> getAllForPerson(int id){
		List<Education> educationList = new ArrayList<>();
		try{
			String queryStatement = String.format("SELECT * FROM education WHERE id_user = %d", id);
			ResultSet result = database.executeSelectQuery(queryStatement);
			while(result.next()){
			Education edu = new Education(result.getString("name"), result.getString("description"),LocalDate.parse(result.getString("date_from")),
			LocalDate.parse(result.getString("date_to")), result.getInt("id_user"));
			edu.setId(result.getInt("id"));
			educationList.add(edu);
			}
			return educationList;

		} catch(NullPointerException | SQLException e){
			System.err.println(e.getMessage());
			System.out.println(e);
			return educationList;
		}
	}

	public void remove(int id){
		String deleteStatement = String.format("DELETE FROM education WHERE id = %d", id);
		database.executeUpdateQuery(deleteStatement);
	}

	public Integer getNewId(){
		Integer newId = 0;
		try{
			String selectStatement = "SELECT id FROM education ORDER BY id DESC LIMIT 1";
			ResultSet result = database.executeSelectQuery(selectStatement);
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
