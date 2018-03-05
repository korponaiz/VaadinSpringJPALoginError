package com.zolee.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zolee.domain.Person;
import com.zolee.service.PersonService;

@Service
public class Authentication {

	@Autowired
	PersonService personService;
	
	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

	public Boolean authenticate(String name, String password){
		Person tempPerson = personService.findByName(name);
		if((tempPerson!=null)&&(name.equals(tempPerson.getName())) && (password.equals(tempPerson.getPassword()))){
			return true;
		}
		return false;
	}

}