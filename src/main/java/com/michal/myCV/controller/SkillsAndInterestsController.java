package com.michal.myCV.controller;

import com.michal.myCV.dao.JDBCPostgres;
import com.michal.myCV.dao.PersonDao;
import com.michal.myCV.dao.PersonDaoPostgresql;
import com.michal.myCV.model.Person;


import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.Map;
import java.util.HashMap;

public class SkillsAndInterestsController {
	private PersonDao personDao;
	private Person person;

	public SkillsAndInterestsController(JDBCPostgres database){
		this.personDao = new PersonDaoPostgresql(database);
	}

	public ModelAndView renderSkills(Request req, Response res){
		return new ModelAndView(getParams(), "bio/skills");
	}

	public ModelAndView renderInterests(Request req, Response res){
		return new ModelAndView(getParams(), "bio/interests");
	}

	private Map getParams(){
		person = personDao.find(1);
		Map params = new HashMap<String, Person>();
		params.put("user", person);
		return params;
	}
}
