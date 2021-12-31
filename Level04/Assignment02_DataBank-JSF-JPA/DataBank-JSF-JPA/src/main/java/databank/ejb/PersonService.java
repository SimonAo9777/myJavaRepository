/*****************************************************************
 * File: PersonPojo.java Course materials (21F) CST8277
 *
 * @author Teddy Yap
 * @author Shahriar (Shawn) Emami
 * @author (original) Mike Norman
 * 
 * @author (update)Simon Ao
 */
package databank.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import databank.model.PersonPojo;

@Stateless
public class PersonService {
	//get the log4j2 logger for this class
	private static final Logger LOG = LogManager.getLogger();
	
	@PersistenceContext(name = "PU_DataBank")
	protected EntityManager em;
	
	public List< PersonPojo> findAll() {
		LOG.debug( "find all people");
		//use the named JPQL query from the PersonPojo class to grab all the people
		TypedQuery< PersonPojo> allPeopleQuery = em.createNamedQuery( PersonPojo.PERSON_FIND_ALL, PersonPojo.class);
		//execute the query and return the result/s.
		return allPeopleQuery.getResultList();
	}
	@Transactional
	public PersonPojo insertPerson( PersonPojo person) {
		LOG.debug( "creating a person = {}", person);
		try {
			em.persist(person);
		} catch (Exception e) {
			LOG.catching(e);
		}
		return person;
	}
	@Transactional
	public PersonPojo findById( int personId) {
		LOG.debug( "find a specific person = {}", personId);
		return em.find( PersonPojo.class, personId);
	}
	@Transactional
	public PersonPojo mergePerson( PersonPojo personWithUpdates) {
		LOG.debug( "updating a specific person = {}", personWithUpdates);
		PersonPojo mergedPersonPojo = em.merge( personWithUpdates);
		if (mergedPersonPojo != null) {
			try {
				em.merge(mergedPersonPojo);
			} catch (Exception e) {
				LOG.catching(e);
			}
		}
		return mergedPersonPojo;
	}
	@Transactional
	public void deletePersonById( int personId) {
		LOG.debug( "deleting a specific personID = {}", personId);
		PersonPojo person = findById( personId);
		LOG.debug( "deleting a specific person = {}", person);
		if ( person != null) {
			try {
				em.refresh(person);
				em.remove(person);
			} catch (Exception e) {
				LOG.catching(e);
			}
		}
	
	
	}	
	
}
