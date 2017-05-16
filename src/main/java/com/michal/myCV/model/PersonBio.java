package com.michal.myCV.model;


import java.time.LocalDate;

public class PersonBio extends BaseModel {
	private String description;
	private LocalDate  dateFrom;
	private LocalDate dateTo;
	private Integer idUser;

	public PersonBio(String name, String description, LocalDate dateFrom, LocalDate dateTo, Integer idUser){
		super(name);
		this.description = description;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.idUser = idUser;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDateFrom(LocalDate dateFrom) {
		this.dateFrom = dateFrom;
	}

	public void setDateTo(LocalDate dateTo) {
		this.dateTo = dateTo;
	}

	public String getDescription() {
		return description;
	}

	public LocalDate getDateFrom() {
		return dateFrom;
	}

	public LocalDate getDateTo() {
		return dateTo;
	}

	public Integer getIdUser() {
		return idUser;
	}
}
