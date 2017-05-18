package com.michal.myCV.dao;

import com.michal.myCV.model.Experience;

import java.util.List;

public interface ExperienceDao {
	void add(Experience experience);

	void update(Experience experience);

	Experience find(Integer experienceId);

	List<Experience> getAllForPerson(Integer personId);

	void remove(Integer experienceId);

	Integer getNewId();
}
