package com.michal.myCV.model;

public class BaseModel {
	protected Integer id;
	protected String name;

	BaseModel(String name){
		this.name = name;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
