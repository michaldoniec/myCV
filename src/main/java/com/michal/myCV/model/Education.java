package com.michal.myCV.model;


import java.time.LocalDate;

public class Education extends PersonBio {
	public Education(String name, String description, LocalDate dateFrom, LocalDate dateTo, Integer idUser){
		super(name, description,dateFrom,dateTo,idUser);
	}

}
