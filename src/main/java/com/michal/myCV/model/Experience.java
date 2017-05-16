package com.michal.myCV.model;

import  java.util.Date;

public class Experience extends BaseModel {
	Experience(String name, String description, Date dateFrom, Date dateTo, Integer idUser) {
		super(name, description, dateFrom, dateTo, idUser);
	}
}
