package com.michal.myCV.controller;

import com.michal.myCV.dao.*;
import com.michal.myCV.model.Education;
import com.michal.myCV.model.Person;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.format.DateTimeParseException;

public class EducationController {
	private EducationDao educationDao;
	private PersonDao personDao;
	private Person person;

	public EducationController(JDBCPostgres database){
		this.educationDao = new EducationDaoPostgresql(database);
		this.personDao = new PersonDaoPostgresql(database);

	}

	public ModelAndView renderEducation(Request req, Response res){
		Map educationParams = getEducationParams();
		String editId = req.queryParams("edit");
		String deleteId = req.queryParams("delete");
		educationParams.put("fieldName", "Education");
		if(editId == null){
			educationParams.put("editField", null);
			if(deleteId != null){
				educationDao.remove(new Integer((deleteId)));
			}
			return new ModelAndView(educationParams, "bio/list");
		} else {
			Integer id = new Integer(editId);
			educationParams.put("editField", id);
			return new ModelAndView(educationParams, "bio/list");
		}
	}

	public ModelAndView updateEducation(Request req, Response res) {
		String educationId = req.queryParams("edit");
		if(educationId != null) {
			Education education = educationDao.find(new Integer(educationId));
			DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-d");
			Integer updatesCount = 0;
			String updateName = req.queryParams("edit_name");
			String updateDescription = req.queryParams("edit_description");
			String updateDateFrom = req.queryParams("edit_date_from");
			String updateDateTo = req.queryParams("edit_date_to");
			if(education != null) {
				try {
					if(!updateName.equals("")) {
						education.setName(updateName);
						updatesCount++;
					}
					if(!updateDescription.equals("")) {
						education.setDescription(updateDescription);
						updatesCount++;
					}
					if(!updateDateFrom.equals("")) {
						try {
							education.setDateFrom(LocalDate.parse(updateDateFrom, dateFormat));
							updatesCount++;
						} catch(DateTimeParseException e) {
							System.err.println(e.getMessage());
						}
					}
					if(!updateDateTo.equals("")) {
						try {
							education.setDateTo(LocalDate.parse(updateDateTo, dateFormat));
							updatesCount++;
						} catch(DateTimeParseException e){
							System.err.println(e.getMessage());
						}
					}
					if(updatesCount > 0) {
						educationDao.update(education);
					}
					res.redirect("/education/view");
					return renderEducation(req, res);
				} catch (NullPointerException e) {
					System.err.println(e.getMessage());
				}
			}
		}
		res.redirect("/education/view");
		return renderEducation(req, res);
	}

	public ModelAndView renderAddEducation(Request req, Response res){
		Map educationParams = getEducationParams();
		educationParams.put("fieldName", "new school or course:");
		return new ModelAndView(educationParams, "bio/add");
	}

	public ModelAndView addEducation(Request req, Response res){
		try {
			Education education = convertDataToEducation(req);
			if(education != null){
				education.setId(educationDao.getNewId());
				educationDao.add(education);
			}
			res.redirect("/education/view");
			return renderEducation(req, res);
		} catch(NullPointerException | DateTimeParseException e){
			System.err.println(e.getMessage());
			return renderEducation(req,res);
		}
	}
	private Map getEducationParams(){
		Map educationParams = new HashMap<String, List<Education>>();
		person = personDao.find(1);
		educationParams.put("user", person);
		educationParams.put("list", person.getEducation());
		return educationParams;
	}

	private Education convertDataToEducation(Request req) throws NullPointerException, DateTimeParseException {
		Education education = null;
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-d");
		String name = req.queryParams("new_name");
		String description = req.queryParams("new_description");
		LocalDate dateFrom = LocalDate.parse(req.queryParams("new_date_from"), dateFormat);
		LocalDate dateTo = LocalDate.parse(req.queryParams("new_date_to"), dateFormat);
		Integer idUser = new Integer(req.queryParams("new_user_id"));
		education = new Education(name, description, dateFrom, dateTo, idUser);
		return education;
	}


}
