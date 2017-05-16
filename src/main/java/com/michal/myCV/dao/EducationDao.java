package com.michal.myCV.dao;

import com.michal.myCV.model.Education;

import java.util.List;


public interface EducationDao {
	void add(Education education);
	List<Education> getAllForPerson(int id);
	void remove(int id);
	Integer getNewId();
}
