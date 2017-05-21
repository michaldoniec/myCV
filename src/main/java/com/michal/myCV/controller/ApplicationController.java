package com.michal.myCV.controller;

import static spark.Spark.*;

import com.michal.myCV.dao.JDBCPostgres;
import spark.Request;
import spark.Response;

import spark.template.thymeleaf.ThymeleafTemplateEngine;
import spark.ModelAndView;

public class ApplicationController {
	private ThymeleafTemplateEngine templateEngine = new ThymeleafTemplateEngine();
	private PersonController personController;
	private ExperienceController experienceController;
	private EducationController educationController;
	private SkillsAndInterestsController skillsAndInterestsController;
	private JDBCPostgres database;
	private Request req;
	private Response res;

	public  ApplicationController(JDBCPostgres database){
		exception(Exception.class, (e, req, res) -> e.printStackTrace());
		staticFileLocation("/public");

		this.database = database;
		personController = new PersonController(database);
		experienceController = new ExperienceController(database);
		educationController = new EducationController(database);
		skillsAndInterestsController = new SkillsAndInterestsController(database);
	}

	public void routes(){
		get("/", (req, res) -> {
			return templateEngine.render(personController.renderUser());
		});

		get("/experience/view", (req,res) ->{
			return templateEngine.render(experienceController.renderExperience(req,res));
		});

		post("/experience/view", (req,res) ->{
			return experienceController.updateExperience(req,res);
		});

		get("/education/view", (req,res) ->{
			return templateEngine.render(educationController.renderEducation(req, res));
		});

		post("/education/view", (req, res) -> {
			return educationController.updateEducation(req, res);
		});

		get("/experience/add", (req, res) ->{
			return templateEngine.render(experienceController.renderAddExperience(req, res));
		});

		post("/experience/add", (req, res) ->{
			return experienceController.addExperience(req, res);

		});

		get("/education/add", (req,res) ->{
			return  templateEngine.render(educationController.renderAddEducation(req,res));
		});

		post("/education/add", (req,res) ->{
			return educationController.addEducation(req, res);
		});

		get("/skills", (req,res) ->{
			return templateEngine.render(skillsAndInterestsController.renderSkills(req,res));
		});

		get("/interests", (req,res) ->{
			return templateEngine.render(skillsAndInterestsController.renderInterests(req,res));
		});
	}
}
