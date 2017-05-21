package com.michal.myCV.dao;

import com.michal.myCV.model.Person;
import com.michal.myCV.model.Education;
import com.michal.myCV.model.Experience;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PersonDaoSQLite implements PersonDao {
	private JDBCSQLite database;
	private Connection databaseConnection;
	private EducationDao educationDao;
	private ExperienceDao experienceDao;
	private PreparedStatement statement;

	public PersonDaoSQLite(JDBCSQLite database){
		this.database = database;
		this.databaseConnection = this.database.getConnection();
		this.educationDao = new EducationDaoSQLite(this.database);
		this.experienceDao = new ExperienceDaoSQLite(this.database);
	}

	public Person find(int id){
		Person person = null;
		try {
			String selectStatement = String.format("SELECT * FROM personaldata WHERE id = %d", id);
			statement = databaseConnection.prepareStatement(selectStatement);
			ResultSet result = database.executeSelectQuery(statement);
			while(result.next()){
				Integer userId = result.getInt("id");
				List<Education> education = educationDao.getAllForPerson(userId);
				List<Experience> experience = experienceDao.getAllForPerson(userId);
				person = new Person(result.getString("name"), result.getString("surname"),
				 result.getString("address"), result.getString("phone"),
				 result.getString("email"), result.getString("sex"), result.getInt("age"),
				 experience, education);
				person.setId(result.getInt("id"));
			}
			return person;
		} catch (SQLException | NullPointerException e){
			System.err.println(e.getMessage());
			return person;
		}
	}

}
