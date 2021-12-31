/************************************************************
 * File: PersonController.java Course materials (21F) CST8277
 *
 * @author Teddy Yap
 * @author Shariar (Shawn) Emami
 * @author (original) Mike Norman
 * 
 * @author Simon Ao
 * @references 21F_Assignment1_JSF.pdf, tutoring by Lab Professor.
 */
package databank.jsf;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.SessionMap;
import javax.inject.Inject;
import javax.inject.Named;

import databank.dao.PersonDao;
import databank.model.PersonPojo;

/**
 * Description: Responsible for collection of Person Pojo's in XHTML (list)
 * <h:dataTable> </br>
 * Delegates all C-R-U-D behavior to DAO
 */
//TODO don't forget this object is a managed bean with a session scope
@Named
@SessionScoped
public class PersonController implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	@SessionMap
	private Map<String, Object> sessionMap;

	@Inject
	private PersonDao personDao;

	private List<PersonPojo> people;

	// necessary methods to make controller work

	public void loadPeople() {
		setPeople(personDao.readAllPeople());
	}

	public List<PersonPojo> getPeople() {
		return people;
	}

	public void setPeople(List<PersonPojo> people) {
		this.people = people;
	}

	public String navigateToAddForm() {
		// Pay attention to the name here, it will be used as the object name in
		// add-person.xhtml
		// ex. <h:inputText value="#{newPerson.firstName}" id="firstName" />
		sessionMap.put("newPerson", new PersonPojo());
		return "add-person?faces-redirect=true";
	}

	/**
	 * This is used in add-xhtml to implement to submitPerson for submit button
	 * 
	 * @param person
	 * @return: ture when direct to list-peopel.xhtml
	 */
	public String submitPerson(PersonPojo person) {
		// TODO use DAO, also update the Person object with current date. you can use
		// Instant::now
		PersonPojo newPerson = (PersonPojo)sessionMap.get("newPerson");
		newPerson.setCreated(Instant.now());
		personDao.createPerson(newPerson);
		return "list-people?faces-redirect=true";
	}

	/**
	 * This is to go to edit-person.xhtml
	 * 
	 * @param personId
	 * @return: ture when direct to edit-person.xhtml
	 */
	public String navigateToUpdateForm(int personId) {
		// TODO use session map to keep track of of the object being edited
		// use DAO to find the object
		PersonPojo person = personDao.readPersonById(personId);
		sessionMap.put("editedPerson", person);
		return "edit-person?faces-redirect=true";
	}

	/**
	 * This is used in edit-xhtml to implement UpdatedPerson for submit button.
	 * 
	 * @param person:updatedPerson
	 * @return: ture when direct to list-person.xhtml 
	 */
	public String submitUpdatedPerson(PersonPojo person) {
		// TODO use DAO
		personDao.updatePerson(person);
		return "list-people?faces-redirect=true";
	}

	/**
	 * This is used in list-xhtml to implement deletePerson
	 * 
	 * @param personId
	 * @return: ture when direct to list-person.xhtml 
	 */
	public String deletePerson(int personId) {
		// TODO use DAO
		personDao.deletePersonById(personId);
		return "list-people?faces-redirect=true";
	}

}