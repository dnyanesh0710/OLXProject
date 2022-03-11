package com.zensar.training.bean;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="persons10")
@XmlRootElement
public class Person {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	
	String name;
	LocalDate dateOfBirth;
	Gender gender;
	boolean elgible;
	double salary;
	char profession;
	
	public Person(int id) {
		super();
		this.id = id;
	}

	public Person() {
		super();
	}
	
	public Person(String name, LocalDate dateOfBirth, Gender gender, boolean elgible, double salary, char profession) {
		super();
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.elgible = elgible;
		this.salary = salary;
		this.profession = profession;
	}
	
	public Person(int id, String name, LocalDate dateOfBirth, Gender gender, boolean elgible, double salary,
			char profession) {
		super();
		this.id = id;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.elgible = elgible;
		this.salary = salary;
		this.profession = profession;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	public Gender getGender() {
		return gender;
	}
	
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	public boolean isElgible() {
		return elgible;
	}
	
	public void setElgible(boolean elgible) {
		this.elgible = elgible;
	}
	
	public double getSalary() {
		return salary;
	}
	
	public void setSalary(double salary) {
		this.salary = salary;
	}
	
	public char getProfession() {
		return profession;
	}
	
	public void setProfession(char profession) {
		this.profession = profession;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", dateOfBirth=" + dateOfBirth + ", gender=" + gender
				+ ", elgible=" + elgible + ", salary=" + salary + ", profession=" + profession + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Person))
			return false;
		Person other = (Person) obj;
		return id == other.id;
	}
}
