package com.michal.myCV.controller;


import com.michal.myCV.dao.JDBCPostgres;
import com.michal.myCV.dao.PersonDao;
import com.michal.myCV.dao.PersonDaoPostgresql;
import com.michal.myCV.model.Person;

import spark.ModelAndView;

import java.util.HashMap;
import java.util.Map;

public class PersonController {
	private PersonDao personDao;

	public PersonController(JDBCPostgres database){
		this.personDao = new PersonDaoPostgresql(database);
	}

	public ModelAndView renderUser(){
		Map userParams = new HashMap<String, Person>();
		Person person = personDao.find(1);
		userParams.put("user", person);
		return new ModelAndView(userParams,"menu");
	}
}
