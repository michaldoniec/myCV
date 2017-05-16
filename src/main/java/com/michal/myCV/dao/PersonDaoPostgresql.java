package com.michal.myCV.dao;

import com.michal.myCV.model.Person;
import com.michal.myCV.model.Education;
import com.michal.myCV.model.Experience;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by michal on 16.05.17.
 */
public class PersonDaoPostgresql implements PersonDao {
	private JDBCPostgres database = new JDBCPostgres();
	private EducationDao educationDao = new EducationDaoPostgresql();
	private ExperienceDao experienceDao = new ExperienceDaoPostgresql();

	public Person find(int id){
		Person person = null;
		try {
			String selectStatement = String.format("SELECT * FROM personaldata WHERE id = %d", id);
			ResultSet result = database.executeSelectQuery(selectStatement);
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
