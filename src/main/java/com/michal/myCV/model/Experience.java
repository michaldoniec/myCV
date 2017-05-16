package com.michal.myCV.model;

import java.time.LocalDate;


public class Experience extends PersonBio {
	public Experience(String name, String description, LocalDate dateFrom, LocalDate dateTo, Integer idUser) {
		super(name, description, dateFrom, dateTo, idUser);
	}
}
