package com.michal.myCV.dao;

import java.util.List;

/**
 * Created by michal on 16.05.17.
 */
public interface ExperienceDao {
	void add(Experience experience);
	Experience find(int id);
	void remove(int id);

	List<Experience> getAll();
}
