package bloodbank.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2021-11-10T11:13:15.057-0500")
@StaticMetamodel(DonationRecord.class)
public class DonationRecord_ extends PojoBase_ {
	public static volatile SingularAttribute<DonationRecord, BloodDonation> donation;
	public static volatile SingularAttribute<DonationRecord, Person> owner;
	public static volatile SingularAttribute<DonationRecord, Byte> tested;
}
