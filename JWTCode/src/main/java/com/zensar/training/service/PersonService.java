package com.zensar.training.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zensar.training.bean.Person;
import com.zensar.training.db.PersonDAO;

@Service
public class PersonService {

	@Autowired
	PersonDAO dao;

	public PersonDAO getDao() {
		return dao;
	}

	public void setDao(PersonDAO dao) {
		this.dao = dao;
	}
	
	public Person addPerson(Person person) {
		Person p1=null;
		p1=dao.save(person);
		return p1;
	}
	
	public Person updatePerson(Person person) {
		Person p1=null;
		p1=dao.save(person);
		return p1;
	}
	
	public boolean deletePerson(Person person) {
		boolean result=true;
		try {
			dao.delete(person);
		}catch(Exception e) {
			e.printStackTrace();
			result=false;
		}
		return result;
	}
	
	public Person findById(int id) {
		Optional<Person> optional=null;
		optional=dao.findById(id);
		if(optional.isPresent())
			return optional.get();
		else
			return null;
	}
	
	public List<Person> findAllPersons(){
		return this.dao.findAll();
	}
	
}
