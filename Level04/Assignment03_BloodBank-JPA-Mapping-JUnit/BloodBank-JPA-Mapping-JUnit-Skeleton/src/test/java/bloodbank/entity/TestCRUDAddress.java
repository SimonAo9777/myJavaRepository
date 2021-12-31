package bloodbank.entity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import common.JUnitBase;
/**
 * 
 * @author Simon Ao #040983402
 *
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestCRUDAddress extends JUnitBase {

	private EntityManager em;
	private EntityTransaction et;

	private static Address address;
	private static final String STREET_NUMBER = "567";
	private static final String STREET = "Woodroffe Dr";
	private static final String CITY = "Ottawa";
	private static final String PROVINCE = "ON";
	private static final String COUNTRY = "CA";
	private static final String ZIPCODE = "K2G1V8";
    
	@BeforeAll
	static void setupAllInit() {
		address = new Address();
		address.setAddress(STREET_NUMBER, STREET, CITY, PROVINCE, COUNTRY, ZIPCODE);
	}
	@BeforeEach
	void setup() {
		em = getEntityManager();
		et = em.getTransaction();
	}
	@Order(1)
	@Test
	void testEmpty() {
		//Get all the results from the address
		long result = getTotalCount(em, Address.class);
		//The result should be 0
		assertThat(result, is(comparesEqualTo(0L)));
	}
	@Order(2)
	@Test
	void testCreate() {
		et.begin();
		em.persist(address);
		et.commit();

		// Get the count of records with id of address obj
		long result = getCountWithId(em, Address.class, Integer.class, Address_.id, address.getId());

		//Check count is 1
		assertThat(result, is(comparesEqualTo(1L)));
	}
	@Order(3)
	@Test
	void testCreateInvalid() {
		Address invalidAddress = address;
		//setAddress street number,street,etc.,should not be null
		invalidAddress.setCity(null);
		et.begin();
		// We expect this test should fail, because the city is a required field
		assertThrows(PersistenceException.class, () -> em.persist(invalidAddress));
		et.commit();
	}
	@SuppressWarnings("unchecked")
	@Test
	void testRead() {
		List<Address> addresses = (List<Address>) getAll(em, Address.class);
		//The memory address of this object changes for a number of reasons
		// Just checking id matches
		assertThat(addresses.get(0).id, equalTo(address.getId()));
	}
	@Test
	void testReadDependencies() {
		// Get address records
		Address returnedAddress = getWithId(em, Address.class, Integer.class, Address_.id, address.getId());

		// Check that fields are correct
		assertThat(returnedAddress.getStreetNumber(), equalTo(STREET_NUMBER));
		assertThat(returnedAddress.getStreet(), equalTo(STREET));
		assertThat(returnedAddress.getCity(), equalTo(CITY));
		assertThat(returnedAddress.getProvince(), equalTo(PROVINCE));
		assertThat(returnedAddress.getCountry(), equalTo(COUNTRY));
		assertThat(returnedAddress.getZipcode(), equalTo(ZIPCODE));
	}
	@Test
	void testUpdate() {
		// Get address records
		Address returnedAddress = getWithId(em, Address.class, Integer.class, Address_.id, address.getId());
		// Update address records
		String newCity = "Vancouver";
		String newZipcode = "DEF456";
		et.begin();
		returnedAddress.setCity(newCity);
		returnedAddress.setZipcode(newZipcode);
		em.merge(returnedAddress);
		et.commit();
		// Get updated address from database
		Address updatedAddress = getWithId(em, Address.class, Integer.class, Address_.id, address.getId());
		// Check updated fields are correct
		assertThat(updatedAddress.getCity(), equalTo(newCity));
		assertThat(updatedAddress.getZipcode(), equalTo(newZipcode));
											
		
	}
	@Order(Integer.MAX_VALUE)
	@Test
	void testDelete() {
		// Add another row to the database to ensure that only the row is deleted.
		Address deleteAddress = new Address();
		deleteAddress.setAddress("456", "new street", "torono", "on", "ca", "DEF456");
		et.begin();
		em.persist(deleteAddress);
		et.commit();
		
		// Remove new address
		et.begin();
		em.remove(deleteAddress);
		et.commit();
		
		// Get two addresses from database as a count
		long deletedAddressCount = getCountWithId(em, Address.class, Integer.class, Address_.id, deleteAddress.getId());
		long addressCount = getCountWithId(em, Address.class, Integer.class, Address_.id, address.getId());
		
		// Asserting count is 0 for deleted record and our old address is still there
		//For deleted records, the assertion count is 0 and our old address is still here
		assertThat( deletedAddressCount, is( equalTo( 0L)));
		assertThat( addressCount, is( equalTo( 1L)));
	}


}