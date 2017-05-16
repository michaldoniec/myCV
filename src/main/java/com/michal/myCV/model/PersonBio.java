package com.michal.myCV.model;


import java.util.Date;

public class PersonBio extends BaseModel {
	private String description;
	private Date dateFrom;
	private Date dateTo;
	private Integer idUser;

	public PersonBio(String name, String description, Date dateFrom, Date dateTo, Integer idUser){
		super(name);
		this.description = description;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.idUser = idUser;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public String getDescription() {
		return description;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public Integer getIdUser() {
		return idUser;
	}
}
