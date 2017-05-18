package com.michal.myCV.dao;

import com.michal.myCV.model.Education;

import java.util.List;

public interface EducationDao {
	void add(Education education);

	List<Education> getAllForPerson(Integer personId);

	void update(Education education);

	void remove(Integer educationId);

	Education find(Integer educationId);

	Integer getNewId();
}
