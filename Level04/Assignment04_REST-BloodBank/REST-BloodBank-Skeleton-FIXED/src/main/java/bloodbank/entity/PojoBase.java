/***************************************************************************
 * File: PojoBase.java Course materials (21F) CST 8277
 * 
 * @author Teddy Yap
 * @author Shariar (Shawn) Emami
 * @date Mar 9, 2021
 * @author Mike Norman
 * @date 2020 04
 * 
 * Update By Students:
 * @author Simon Ao
 * @author Chik Matthew
 * @author Park Fred
 * @author Sun Ding
 * 
 * @date 06/12/2021
 */
package bloodbank.entity;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * Abstract class that is base of (class) hierarchy for all @Entity classes
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@EntityListeners(PojoListener.class)
public abstract class PojoBase implements Serializable {
	private static final long serialVersionUID = 1L;
	@Basic( optional = false) 
	@Id  
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	protected int id;

	@Version
	protected int version;
	
	@Basic( optional = false) 
	@Column( name = "created")
	protected long epochCreated;

	@Basic( optional = false) 
	@Column( name = "updated")
	protected long epochUpdated;

	@Transient 
	protected Instant created;
   
	@Transient 
	protected Instant updated;

	public int getId() {
		return id;
	}
	public void setId( int id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}
	public void setVersion( int version) {
		this.version = version;
	}

	public Instant getCreated() {
		if ( created == null)
			setCreatedEpochMilli( epochCreated);
		return created;
	}
	public void setCreated( Instant created) {
		setCreatedEpochMilli( created.toEpochMilli());
	}
	@JsonIgnore
	public long getCreatedEpochMilli() {
		return created.toEpochMilli();
	}
	public void setCreatedEpochMilli( long created) {
		this.epochCreated = created;
		this.created = Instant.ofEpochMilli( created);
	}

	public Instant getUpdated() {
		if ( updated == null)
			setUpdatedEpochMilli( epochUpdated);
		return updated;
	}
	public void setUpdated( Instant updated) {
		setUpdatedEpochMilli( updated.toEpochMilli());
	}
	@JsonIgnore
	public long getUpdatedEpochMilli() {
		return updated.toEpochMilli();
	}
	public void setUpdatedEpochMilli( long updated) {
		this.epochUpdated = updated;
		this.updated = Instant.ofEpochMilli( updated);
	}

	/**
	 * <a href=https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier">How to implement hashCode, quals</a>
	 * <p>
	 * Very important - use getter's for member variables because needs to Hibernate 'traps' those calls and <br/>
	 * figure out some things!
	 */
	@Override
	public int hashCode() {
		//final int prime = 31;
		//int result = super.hashCode();
		//return prime * result + Objects.hash(getId());
		return getClass().hashCode();
	}
	@Override
	public boolean equals( Object obj) {
		if ( this == obj) {
			return true;
		}
		if ( obj == null) {
			return false;
		}
		if ( !( obj instanceof PojoBase)) {
			return false;
		}
		PojoBase other = (PojoBase) obj;
		if ( id != other.id) {
			return false;
		}
		return true;
	}

}