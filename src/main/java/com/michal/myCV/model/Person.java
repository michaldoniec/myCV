package com.michal.myCV.model;

/**
 * Created by michal on 16.05.17.
 */
public class Person extends BaseModel {
	private String surname;
	private String address;
	private String phone;
	private String email;
	private String sex;
	private Integer age;
	private Experience professionalExperience;
	private  Education education;

	public Person(String name, String surname, String address, String phone, String email, String sex, Integer age,
	       Experience professionalExperience, Education education){
		super(name);
		this.surname = surname;
		this.address = address;
		this.phone = phone;
		this.email = email;
		this.sex = sex;
		this.age = age;
		this.professionalExperience = professionalExperience;
		this.education = education;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setProfessionalExperience(Experience professionalExperience) {
		this.professionalExperience = professionalExperience;
	}

	public void setEducation(Education education) {
		this.education = education;
	}

	public String getSurname() {
		return surname;
	}

	public Integer getAge() {
		return age;
	}

	public String getAddress() {
		return address;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public String getSex() {
		return sex;
	}

	public Experience getProfessionalExperience() {
		return professionalExperience;
	}

	public Education getEducation() {
		return education;
	}
}
