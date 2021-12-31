package bloodbank.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2021-11-10T10:55:13.657-0500")
@StaticMetamodel(Address.class)
public class Address_ extends PojoBase_ {
	public static volatile SingularAttribute<Address, String> streetNumber;
	public static volatile SingularAttribute<Address, String> city;
	public static volatile SingularAttribute<Address, String> country;
	public static volatile SingularAttribute<Address, String> province;
	public static volatile SingularAttribute<Address, String> street;
	public static volatile SingularAttribute<Address, String> zipcode;
	public static volatile SetAttribute<Address, Contact> contacts;
}
