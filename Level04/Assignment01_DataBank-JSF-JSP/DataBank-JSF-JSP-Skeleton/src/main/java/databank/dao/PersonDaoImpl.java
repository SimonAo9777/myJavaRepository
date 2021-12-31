/******************************************************
 * File: PersonDaoImpl.java Course materials (21F) CST8277
 *
 * @author Teddy Yap
 * @author Shariar (Shawn) Emami
 * @author (original) Mike Norman
 * 
 * @author Simon Ao
 * @references 21F_Assignment1_JSF.pdf, tutoring by Lab Professor.
 */
package databank.dao;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.sql.DataSource;

import databank.model.PersonPojo;

/**
 * Description: Implements the C-R-U-D API for the database
 */
//TODO don't forget this object is a managed bean with a application scope
@Named
@ApplicationScoped
public class PersonDaoImpl implements PersonDao, Serializable {
	/** explicitly set serialVersionUID */
	private static final long serialVersionUID = 1L;

	private static final String DATABANK_DS_JNDI = "java:app/jdbc/databank";
	private static final String READ_ALL = "select * from person";
	private static final String READ_PERSON_BY_ID = "select * from person where id = ?";
	private static final String INSERT_PERSON = "insert into person (first_name,last_name,email,phone,created) values(?,?,?,?,?)";
	private static final String UPDATE_PERSON_ALL_FIELDS = "update person set first_name = ?, last_name = ?, email = ?, phone = ? where id = ?";
	private static final String DELETE_PERSON_BY_ID = "delete from person where id = ?";

	@Inject
	protected ExternalContext externalContext;

	private void logMsg(String msg) {
		((ServletContext) externalContext.getContext()).log(msg);
	}

	@Resource(lookup = DATABANK_DS_JNDI)
	protected DataSource databankDS;

	protected Connection conn;
	protected PreparedStatement readAllPstmt;
	protected PreparedStatement readByIdPstmt;
	protected PreparedStatement createPstmt;
	protected PreparedStatement updatePstmt;
	protected PreparedStatement deleteByIdPstmt;

	/**
	 * Create connection for JDBC.
	 * @PostConstruct  beginning of the life cycle for connection.
	 * @return: void
	 */
	@PostConstruct	
	protected void buildConnectionAndStatements() {
		try {
			logMsg("building connection and stmts");
			conn = databankDS.getConnection();
			readAllPstmt = conn.prepareStatement(READ_ALL);
			createPstmt = conn.prepareStatement(INSERT_PERSON, RETURN_GENERATED_KEYS);
			// TODO initialize other PreparedStatements
			readByIdPstmt = conn.prepareStatement(READ_PERSON_BY_ID);// initialize readById PreparedStatement
			updatePstmt = conn.prepareStatement(UPDATE_PERSON_ALL_FIELDS);// initialize update PreparedStatement
			deleteByIdPstmt = conn.prepareStatement(DELETE_PERSON_BY_ID);// initialize deleteById PreparedStatement

		} catch (Exception e) {
			logMsg("something went wrong getting connection from database: " + e.getLocalizedMessage());
		}
	}

	/**
	 * Destory connection for JDBC.
	 * @PreDestroy  end of connection.
	 * @return: void
	 */
	@PreDestroy	
	protected void closeConnectionAndStatements() {
		try {
			logMsg("closing stmts and connection");
			readAllPstmt.close();
			createPstmt.close();

			// TODO close other PreparedStatements
			readByIdPstmt.close(); // close readById PreparedStatement
			updatePstmt.close(); // close update PreparedStatement
			deleteByIdPstmt.close(); // close deleteById PreparedStatement

			conn.close();
		} catch (Exception e) {
			logMsg("something went wrong closing stmts or connection: " + e.getLocalizedMessage());
		}
	}

	/**
	 * Read all person in the database.
	 * @return:List<PersonPojo>
	 */
	@Override
	public List<PersonPojo> readAllPeople() {
		logMsg("reading all People");
		List<PersonPojo> people = new ArrayList<>();
		try (ResultSet rs = readAllPstmt.executeQuery();) {

			while (rs.next()) {
				PersonPojo newPerson = new PersonPojo();
				newPerson.setId(rs.getInt("id"));
				newPerson.setFirstName(rs.getString("first_name"));

				// TODO complete the person initialization
				newPerson.setLastName(rs.getString("last_name")); // last name initialization
				newPerson.setEmail(rs.getString("email")); // email initialization
				newPerson.setPhoneNumber(rs.getString("phone")); // phone initialization
				newPerson.setCreated(rs.getLong("created")); // created initialization
				people.add(newPerson);
			}
			try {
                rs.close();
            }
            catch (Exception e) {
                logMsg("something went wrong closing resultSet: " + e.getLocalizedMessage());
            }
        }
        catch (SQLException e) {
            logMsg("something went wrong accessing database: " + e.getSQLState()+","+ e.getLocalizedMessage());
        }
        return people;
	}

	/**
     * To create a new person in the database
     * @param: Person person
     * 
     */ 
	@Override
	public PersonPojo createPerson(PersonPojo person) {
		logMsg("creating an person");
		// TODO complete
		try {
			createPstmt.setString(1, person.getFirstName());  // create first name
			createPstmt.setString(2, person.getLastName());   // create last name
			createPstmt.setString(3, person.getEmail());      // create email
			createPstmt.setString(4, person.getPhoneNumber()); // create phone number
			createPstmt.setLong(5, person.getCreatedEpochMilli()); // create created
			createPstmt.executeUpdate(); // execute the create statement					

		} 	
				
		catch (SQLException e) {
			 logMsg("something went wrong accessing database: " + e.getSQLState()+","+ e.getLocalizedMessage());
		}

		return person;
	}
	/**
     * To find a specific person in the database
     * @param: personId
     * 
     */ 
	@Override
	public PersonPojo readPersonById(int personId) {
		logMsg("read a specific person");
		// TODO complete
		PersonPojo newPerson = new PersonPojo();
		try {
			readByIdPstmt.setInt(1, personId);

			try (ResultSet rs = readByIdPstmt.executeQuery()) {
				if (rs.next()) {
					newPerson.setId(rs.getInt("id")); // read id
					newPerson.setFirstName(rs.getString("first_name"));// read first name
					newPerson.setLastName(rs.getString("last_name")); // read last name
					newPerson.setEmail(rs.getString("email")); // read email
					newPerson.setPhoneNumber(rs.getString("phone")); // read phone
					newPerson.setCreated(rs.getLong("created")); // read created
				}
			}
		} catch (SQLException e) {
			logMsg("something went wrong accessing database: " + e.getSQLState()+","+ e.getLocalizedMessage());
		}
		return newPerson;
	}

	 /**
     * To update a specific person in the database
     * @param: person updatedPerson
     * 
     */ 
	@Override
	public void updatePerson(PersonPojo person) {
		logMsg("updating a specific person");
		// TODO complete
		logMsg(person.toString());
		try {
						
			updatePstmt.setString(1, person.getFirstName());  // update first name
			updatePstmt.setString(2, person.getLastName());   // update last name
			updatePstmt.setString(3, person.getEmail());      // update email
			updatePstmt.setString(4, person.getPhoneNumber()); // update phone number
		    updatePstmt.setInt(5, person.getId());            // update id
		    
		    updatePstmt.executeUpdate();

		//	int i=updatePstmt.executeUpdate(); // execute the Update statement
		//	logMsg(""+i);
		} catch (SQLException e) {
			logMsg("something went wrong accessing database: " + e.getSQLState()+"," + e.getLocalizedMessage());
		}

	}

	/**
     * To delete a specific person in the database
     * @param: int personId
     * 
     */ 
	@Override
	public void deletePersonById(int personId) {
		logMsg("deleting a specific person");
		// TODO complete
		try {
			deleteByIdPstmt.setInt(1, personId); // delete id statement
			deleteByIdPstmt.executeUpdate();     // execute the delete statement
		} catch (SQLException e) {
			logMsg("something went wrong deleting from database (" + e.getSQLState() + ", " + e.getLocalizedMessage() + ")");
		}

	}

}