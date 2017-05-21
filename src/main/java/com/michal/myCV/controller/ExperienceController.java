package com.michal.myCV.controller;

import com.michal.myCV.dao.*;
import com.michal.myCV.model.Experience;
import com.michal.myCV.model.Person;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.time.format.DateTimeParseException;


public class ExperienceController {
	private ExperienceDao experienceDao;
	private PersonDao personDao;
	private Person person;

	public ExperienceController(JDBCSQLite database){
		this.experienceDao = new ExperienceDaoSQLite(database);
		this.personDao = new PersonDaoPostgresql(database);
	}

	public ModelAndView renderExperience(Request req, Response res){
		Map experienceParams = getExperienceParams();
		String editId = req.queryParams("edit");
		String deleteId = req.queryParams("delete");
		experienceParams.put("fieldName", "Professional experience");
		if(editId == null){
		    experienceParams.put("editField", null);
			if(deleteId != null){
				experienceDao.remove(new Integer(deleteId));
			}
			return new ModelAndView(experienceParams, "bio/list");
		} else {
			Integer id = new Integer(editId);
			experienceParams.put("editField", id);
			return new ModelAndView(experienceParams, "bio/list");
		}
	}

	public ModelAndView renderAddExperience(Request req, Response res){
		Map experienceParams = getExperienceParams();
		experienceParams.put("fieldName", " new professional experience:");
		return new ModelAndView(experienceParams, "bio/add");
	}

	public ModelAndView addExperience(Request req, Response res){
		try {
			Experience experience = convertDataToExperience(req);
			if(experience != null){
				experience.setId(experienceDao.getNewId());
				experienceDao.add(experience); 
			}
			res.redirect("/experience/view");
			return renderExperience(req, res);
		} catch(NullPointerException | DateTimeParseException  e){
			System.err.println(e.getMessage());
			return renderExperience(req,res);
		}
	}

	public ModelAndView updateExperience(Request req, Response res) {
		String experienceId = req.queryParams("edit");
		if(experienceId != null) {
			Experience experience = experienceDao.find(new Integer(experienceId));
			DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-d");
			Integer updatesCount = 0;
			String updateName = req.queryParams("edit_name");
			String updateDescription = req.queryParams("edit_description");
			String updateDateFrom = req.queryParams("edit_date_from");
			String updateDateTo = req.queryParams("edit_date_to");
			if(experience != null) {
				try {
					if(!updateName.equals("")) {
						experience.setName(updateName);
						updatesCount++;
					}
       				if(!updateDescription.equals("")) {
						experience.setDescription(updateDescription);
						updatesCount++;
					}
					if(!updateDateFrom.equals("")) {
						try {
						experience.setDateFrom(LocalDate.parse(updateDateFrom, dateFormat));
						updatesCount++;
						} catch(DateTimeParseException e) {
							System.err.println(e.getMessage());
						}
					}
					if(!updateDateTo.equals("")) {
						try {
							experience.setDateTo(LocalDate.parse(updateDateTo, dateFormat));
							updatesCount++;
						} catch(DateTimeParseException e){
							System.err.println(e.getMessage());
						}
					}
					if(updatesCount > 0) {
						experienceDao.update(experience);
					}
					res.redirect("/experience/view");
					return renderExperience(req, res);
				} catch (NullPointerException e) {
					System.err.println(e.getMessage());
				}
			}
		}
		res.redirect("/experience/view");
		return renderExperience(req, res);
	}
	
	private Map getExperienceParams(){
		Map experienceParams = new HashMap<String, List<Experience>>();
		person = personDao.find(1);
		experienceParams.put("user", person);
		experienceParams.put("list", person.getProfessionalExperience());
		return experienceParams;
	}

	private Experience convertDataToExperience(Request req) throws NullPointerException, DateTimeParseException {
		Experience experience = null;
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-d");
		String name = req.queryParams("new_name");
		String description = req.queryParams("new_description");
		LocalDate dateFrom = LocalDate.parse(req.queryParams("new_date_from"), dateFormat);
		LocalDate dateTo = LocalDate.parse(req.queryParams("new_date_to"), dateFormat);
		Integer idUser = new Integer(req.queryParams("new_user_id"));
		experience = new Experience(name, description, dateFrom, dateTo, idUser);
		return experience;
	}

}
