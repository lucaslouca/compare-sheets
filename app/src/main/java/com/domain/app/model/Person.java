package com.domain.app.model;

public class Person {
	private String firstName;
	private String lastName;

	@Override
	public String toString() {
		return firstName + " " + lastName;
	}

	public Person(String firstName, String lastName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
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
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		}
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		}

		if (firstName.equalsIgnoreCase(other.firstName) && lastName.equalsIgnoreCase(other.lastName)) {
			return true;
		} else if (firstName.equalsIgnoreCase(other.lastName) && lastName.equalsIgnoreCase(other.firstName)) {
			return true;
		}
		return false;
	}
}
