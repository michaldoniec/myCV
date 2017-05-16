package com.michal.myCV.dao;

import java.util.List;

/**
 * Created by michal on 16.05.17.
 */
public interface EducationDao {
	void add(Education education);
	Education find(int id);
	void remove(int id);

	List<Education> getAll();
}
