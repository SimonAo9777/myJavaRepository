/*****************************************************************
 * File: PersonPojo.java Course materials (21F) CST8277
 *
 * @author Teddy Yap
 * @author Shahriar (Shawn) Emami
 * @author (original) Mike Norman
 * 
 * @author (update)Simon Ao
 */
package databank.model;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 * TODO 14.1 - complete the @Entity with correct name.<br>
 * TODO 14.2 - complete the two @NamedQueries.<br>
 * TODO 15 - use the correct table name.<br>
 * TODO 16 - fix the AccessType.<br>
 * TODO 17 - make PersonPojoListener be the lister of this class. use:
 * https://www.logicbig.com/tutorials/java-ee-tutorial/jpa/entity-listeners.html<br>
 * TODO 18 - add the remaining @Basic and @Column to all the fields.<br>
 * TODO 19 - Use @Version on the correct field. This annotation helps keeping
 * track of what version of Entity we are working with.<br>
 * TODO 20 - dates (Instant) and editable are not to be mapped. Answer is near
 * the top: https://www.objectdb.com/java/jpa/entity/fields<br>
 */
@Entity(name = "Person")
@Table(name = "person", catalog = "databank", schema = "")
@Access(AccessType.FIELD)
@NamedQueries({ @NamedQuery(name = PersonPojo.PERSON_FIND_ALL, query = "SELECT p from Person p"),
		@NamedQuery(name = PersonPojo.PERSON_FIND_ID, query = "SELECT p FROM Person p WHERE p.id = :personId") })
@EntityListeners(PersonPojoListener.class)
public class PersonPojo implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String PERSON_FIND_ALL = "Person.findAll";
	public static final String PERSON_FIND_ID = "Person.findById";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private int id;

	@Basic(optional = false)
	@Column(name = "first_name")
	private String firstName;

	@Basic(optional = false)
	@Column(name = "last_name")
	private String lastName;

	@Basic(optional = false)
	@Column(name = "email")
	private String email;

	@Basic(optional = false)
	@Column(name = "phone")
	private String phoneNumber;

	@Basic(optional = false)
	@Column(name = "created")
	private long epochCreated;

	@Basic(optional = false)
	@Column(name = "updated")
	private long epochUpdated;

	@Version
	private int version = 1;

	@Transient
	private Instant updated;
	@Transient
	private Instant created;
	@Transient
	private boolean editable;

	public boolean getEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public int getId() {
		return id;
	}

	/**
	 * @param id new value for id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the value for firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName new value for firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the value for lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName new value for lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * 
	 * @return the value for email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 
	 * @param email new value for email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 
	 * @return the value for phonenumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * 
	 * @param phoneNumber new value for phoneNumber
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * 
	 * @return the value for created
	 */
	public Instant getCreated() {
		if (created == null)
			setCreated(epochCreated);
		return created;
	}

	/**
	 * 
	 * @return the value for CreatedEpochMilli
	 */
	public long getCreatedEpochMilli() {
		return created.toEpochMilli();
	}

	/**
	 * 
	 * @param created new value for created
	 */
	public void setCreated(Instant created) {
		setCreated(created.toEpochMilli());
	}

	/**
	 * 
	 * @param created new value for created
	 */
	public void setCreated(long created) {
		this.epochCreated = created;
		this.created = Instant.ofEpochMilli(created);
	}

	/**
	 * 
	 * @param updated new value for update
	 */
	public void setUpdated(Instant updated) {
		setUpdated(updated.toEpochMilli());
	}

	/**
	 * 
	 * @param updated new value for update
	 */
	public void setUpdated(long updated) {
		this.epochUpdated = updated;
		this.updated = Instant.ofEpochMilli(updated);
	}

	/**
	 * 
	 * @return the value for updated
	 */
	public Instant getUpdated() {
		if (updated == null)
			setCreated(epochUpdated);
		return updated;
	}

	/**
	 * 
	 * @return the value for updated
	 */
	public long getUpdatedEpochMilli() {
		return updated.toEpochMilli();
	}

	public int getVersion() {
		return version;
	}

	/**
	 * 
	 * @param version new value for version
	 */
	public void setVersion(int version) {
		this.version = version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof PersonPojo)) {
			return false;
		}
		PersonPojo other = (PersonPojo) obj;
		if (id != other.id) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Person [id=").append(id);
		if (firstName != null) {
			builder.append(", ").append("firstName=").append(firstName);
		}
		if (lastName != null) {
			builder.append(", ").append("lastName=").append(lastName);
		}
		if (phoneNumber != null) {
			builder.append(", ").append("phoneNumber=").append(phoneNumber);
		}
		if (email != null) {
			builder.append(", ").append("email=").append(email);
		}
		if (created != null) {
			builder.append(", ").append("created=").append(created);
		}
		if (updated != null) {
			builder.append(", ").append("updated=").append(updated);
		}
		builder.append("]");
		return builder.toString();
	}

}