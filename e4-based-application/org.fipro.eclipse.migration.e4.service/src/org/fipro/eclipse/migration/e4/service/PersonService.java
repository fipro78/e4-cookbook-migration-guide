package org.fipro.eclipse.migration.e4.service;

import java.util.List;

import org.fipro.eclipse.migration.e4.model.Person;

/**
 * Class that acts as service for accessing numerous {@link Person}s.
 * The values are randomly put together out of names and places from "The Simpsons"
 */
public interface PersonService {

	/**
	 * Creates a list of {@link Person}s. 
	 * @param numberOfPersons The number of {@link Person}s that should be generated.
	 * @return
	 */
	List<Person> getPersons(int numberOfPersons);

	/**
	 * Creates a random person out of names which are taken from "The Simpsons" 
	 * and enrich them with random generated married state and birthday date.
	 * @return
	 */
	Person createPerson(int id);
}
