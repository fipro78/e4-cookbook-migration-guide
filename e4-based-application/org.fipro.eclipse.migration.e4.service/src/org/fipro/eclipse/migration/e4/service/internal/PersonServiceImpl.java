package org.fipro.eclipse.migration.e4.service.internal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.fipro.eclipse.migration.e4.model.Person;
import org.fipro.eclipse.migration.e4.service.PersonService;
 
/**
 * Class that acts as service for accessing numerous {@link Person}s.
 * The values are randomly put together out of names and places from "The Simpsons"
 */
public final class PersonServiceImpl implements PersonService {
 
	public String[] maleNames = {
		"Bart", 
		"Homer", 
		"Lenny", 
		"Carl", 
		"Waylon", 
		"Ned", 
		"Timothy"};
	public String[] femaleNames = {
		"Marge", 
		"Lisa", 
		"Maggie", 
		"Edna", 
		"Helen", 
		"Jessica"};
	public String[] lastNames = {
		"Simpson", 
		"Leonard", 
		"Carlson", 
		"Smithers", 
		"Flanders", 
		"Krabappel", 
		"Lovejoy"};
 
	@Override
	public List<Person> getPersons(int numberOfPersons) {
		List<Person> result = new ArrayList<Person>();
		
		for (int i = 0; i < numberOfPersons; i++) {
			result.add(createPerson(i));
		}
		
		return result;
	}
	
	@Override
	public Person createPerson(int id) {
		Random randomGenerator = new Random();
		
		Person result = new Person(id);
		result.setGender(Person.Gender.values()[randomGenerator.nextInt(2)]);
		
		if (result.getGender().equals(Person.Gender.MALE)) {
			result.setFirstName(maleNames[randomGenerator.nextInt(maleNames.length)]);
		}
		else {
			result.setFirstName(femaleNames[randomGenerator.nextInt(femaleNames.length)]);
		}
		
		result.setLastName(lastNames[randomGenerator.nextInt(lastNames.length)]);
		result.setMarried(randomGenerator.nextBoolean());
		
		int month = randomGenerator.nextInt(12);
		int day = 0;
		if (month == 2) {
			day = randomGenerator.nextInt(28);
		}
		else {
			day = randomGenerator.nextInt(30);
		}
		int year = 1920 + randomGenerator.nextInt(90);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			result.setBirthday(sdf.parse(""+year+"-"+month+"-"+day));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
}