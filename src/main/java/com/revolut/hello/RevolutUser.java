package com.revolut.hello;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class RevolutUser {

	//private String id;
	@Id
	private String name;
	private String dateOfBirth;
	
	@Override
	public String toString() {
		return "RevolutUser [name=" + name + ", dateOfBirth=" + dateOfBirth + "]";
	}

	public RevolutUser() {
	}
	
	public RevolutUser(String name, String dateOfBirth) {
		super();
		this.name = name;
		this.dateOfBirth = dateOfBirth;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
}
