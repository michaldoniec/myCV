package com.michal.myCV.controller;


import com.michal.myCV.dao.JDBCSQLite;
import com.michal.myCV.dao.PersonDao;
import com.michal.myCV.dao.PersonDaoSQLite;
import com.michal.myCV.model.Person;

import spark.ModelAndView;

import java.util.HashMap;
import java.util.Map;

public class PersonController {
	private PersonDao personDao;

	public PersonController(JDBCSQLite database){
		this.personDao = new PersonDaoSQLite(database);
	}

	public ModelAndView renderUser(){
		Map userParams = new HashMap<String, Person>();
		Person person = personDao.find(1);
		userParams.put("user", person);
		return new ModelAndView(userParams,"menu");
	}
}
