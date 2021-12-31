package bloodbank.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author Simon Ao #040983402
 */
//TODO PBB01 - add missing annotations, week 9 slides page 15. value 1 is private and value 0 is public.
@Entity
@DiscriminatorValue(value = "1")
public class PrivateBloodBank extends BloodBank {
	private static final long serialVersionUID = 1L;

	public PrivateBloodBank() {
	}
}
