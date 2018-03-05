package com.zolee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zolee.domain.Person;
import com.zolee.repository.PersonRepository;

@Component
public class PersonService {

	@Autowired
	PersonRepository personRepository;
	
	public List<Person> findAll(){
		return personRepository.findAll();
	}
	
	public Person findByName(String name) {
		return personRepository.findByName(name);
	}
	
	public void save(Person person) {
		personRepository.save(person);
	} 
	
}
