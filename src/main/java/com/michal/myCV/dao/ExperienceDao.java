package com.michal.myCV.dao;

import com.michal.myCV.model.Experience;

import java.util.List;

/**
 * Created by michal on 16.05.17.
 */
public interface ExperienceDao {
	void add(Experience experience);
	List<Experience> getAllForPerson(int id);
	void remove(int id);
	Integer getNewId();
}
