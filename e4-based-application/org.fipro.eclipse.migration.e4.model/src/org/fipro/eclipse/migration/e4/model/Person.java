package org.fipro.eclipse.migration.e4.model;

import java.util.Date;

public class Person extends ModelObject {

	public enum Gender {
		MALE, FEMALE
	}
	
	private final int id;
	private String firstName;
	private String lastName;
	private Gender gender;
	private boolean married;
	private Date birthday;
	
	public Person(int id) { 
		this.id = id;
	}
	
	public Person(int id, String firstName, String lastName, Gender gender, boolean married, Date birthday) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.married = married;
		this.birthday = birthday;
	}
	
	public Person(Person base) {
		this.id = base.id;
		this.firstName = base.firstName;
		this.lastName = base.lastName;
		this.gender = base.gender;
		this.married = base.married;
		this.birthday = base.birthday;
	}
	
	public int getId() {
		return id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		firePropertyChange("firstName", this.firstName, this.firstName = firstName);
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		firePropertyChange("lastName", this.lastName, this.lastName = lastName);
	}
	
	public Gender getGender() {
		return gender;
	}
	
	public void setGender(Gender gender) {
		firePropertyChange("gender", this.gender, this.gender = gender);
	}
	
	public boolean isMarried() {
		return married;
	}
	
	public void setMarried(boolean married) {
		firePropertyChange("married", this.married, this.married = married);
	}
	
	public Date getBirthday() {
		return birthday;
	}
	
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	 
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (id != other.id)
			return false;
		return true;
	}

}