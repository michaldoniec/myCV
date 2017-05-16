package com.michal.myCV.model;

/**
 * Created by michal on 16.05.17.
 */
public class BaseModel {
	protected Integer id;
	protected String name;
	BaseModel(String name){
		this.name = name;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
