package bloodbank.entity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import org.junit.jupiter.api.AfterEach;
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
public class TestCRUDPublicBloodBank extends JUnitBase {
	private EntityManager em;
	private EntityTransaction et;
	private static BloodBank bloodBank;
	private static String NAME = "Simzhuo Blood Bank";
	private static Set<BloodDonation> donations;
	private static BloodType bloodType;
	private static DonationRecord donationRecord;
	private static Person person;

	@BeforeAll
	static void setupAllInit() {
		// Create PublicBloodBank object
		bloodBank = new PublicBloodBank();
		BloodDonation bloodDonation = new BloodDonation();
		// Create BloodType
		bloodType = new BloodType();
		bloodType.setType("AB", "+");

		// Create Person
		person = new Person();
		person.setFirstName("Simzhuo");
		person.setLastName("Aho");

		// Create DonationRecord
		donationRecord = new DonationRecord();
		donationRecord.setDonation(bloodDonation);
		donationRecord.setOwner(person);
		donationRecord.setTested(false);

		// Set the values of bloodDonation
		bloodDonation.setBank(bloodBank);
		bloodDonation.setRecord(donationRecord);
		bloodDonation.setMilliliters(5);
		bloodDonation.setBloodType(bloodType);

		// Set the values of bloodBank
		donations = new HashSet<BloodDonation>();
		donations.add(bloodDonation);
		bloodBank.setName(NAME);
		bloodBank.setDonations(donations);
	}

	@BeforeEach
	void setup() {
		em = getEntityManager();
		et = em.getTransaction();
	}

	@AfterEach
	void tearDown() {
		em.close();
	}
	@Order(1)
	@Test
	void testEmpty() {
		// Get all the results from the PublicBloodBank table
		long result = getTotalCount(em, BloodBank.class);
		// The result should be 0
		assertThat(result, is(comparesEqualTo(0L)));		
	}
	@Order(2)
	@Test
	void testCreate() {
		et.begin();
		em.persist(person);
		em.persist(donationRecord);
		em.persist(bloodBank);		
		et.commit();

		// Get the count of records with id of PublicBloodBank obj
		long result = getCountWithId(em, BloodBank.class, Integer.class, BloodBank_.id, bloodBank.getId());

		// Check count is 1
		assertThat(result, is(comparesEqualTo(1L)));
	}
	@Order(3)
	@Test
	void testCreateInvalid() {
		et.begin();
		BloodBank invalidBloodBank = new PublicBloodBank();
		//We expect this test should fail, because the NULL is a required field
		assertThrows(PersistenceException.class, () -> em.persist(invalidBloodBank));
		et.commit();
	}
	@SuppressWarnings("unchecked")
	@Test
	void testRead() {
		List<BloodBank> bloodBankList = (List<BloodBank>) getAll(em, BloodBank.class);
		assertThat(bloodBankList.get(0), equalTo(bloodBank));
	}
	@Test
	void testReadDependencies() {
		BloodBank returnedBloodBank = getWithId(em, BloodBank.class, Integer.class, BloodBank_.id,
				bloodBank.getId());

		assertThat(returnedBloodBank.getDonations(), equalTo(bloodBank.getDonations()));
	}
	@Test
	void testUpdate() {
		String updatedName = "updated blood bank";

		//Get the bloodBank and rename it
		BloodBank returnedBloodBank = getWithId(em, BloodBank.class, Integer.class, BloodBank_.id,
				bloodBank.getId());
		returnedBloodBank.setName(updatedName);
		// Commit to database
		et.begin();
		em.merge(returnedBloodBank);
		et.commit();		
		//Get the BloodBank again and check that the update value is correct
		returnedBloodBank = getWithId(em, BloodBank.class, Integer.class, BloodBank_.id, bloodBank.getId());		
		assertThat(returnedBloodBank.getName(), equalTo(updatedName));
	}
	@Test
	void testUpdateDependencies() {
		// update milliliters in donation
		int milliliters = 5;		
		//Get to BloodBank and replacement donations
		BloodBank returnedBloodBank = getWithId(em, BloodBank.class, Integer.class, BloodBank_.id,
				bloodBank.getId());
		returnedBloodBank.getDonations().stream().findFirst().get().setMilliliters(milliliters);
		Set<BloodDonation> updatedBloodDonations = returnedBloodBank.getDonations();
		// Commit to database
		et.begin();
		em.merge(returnedBloodBank);
		et.commit();
		//Get the BloodBank again and check that the update value is correct
		returnedBloodBank = getWithId(em, BloodBank.class, Integer.class, BloodBank_.id, bloodBank.getId());
		assertThat(returnedBloodBank.getDonations(), equalTo(updatedBloodDonations));
	}
	@Order(Integer.MAX_VALUE - 1)
	@Test
	void testDeleteDependency() {
		//Get to BloodBank and delete donations
		BloodBank returnedBloodBank = getWithId(em, BloodBank.class, Integer.class, BloodBank_.id,
				bloodBank.getId());
		returnedBloodBank.setDonations(null);
		// Commit to database
		et.begin();
		em.merge(returnedBloodBank);
		et.commit();		
		//Get the bloodBank again and check that the donation is NULL
		returnedBloodBank = getWithId(em, BloodBank.class, Integer.class, BloodBank_.id, bloodBank.getId());
		assertThat(returnedBloodBank.getDonations(), is(nullValue()));
		
	}
	@Order(Integer.MAX_VALUE)
	@Test
	void testDelete() {		
		//Add another row to the database to ensure that only the row is deleted
		BloodBank deletedBloodBank = new PublicBloodBank();
		deletedBloodBank.setName(NAME);		
		et.begin();
		em.persist(deletedBloodBank);
		et.commit();
		// Remove new BloodBank
		et.begin();
		em.remove(deletedBloodBank);
		et.commit();
		// Get two BloodBanks from database as a count
		long deletedBloodBankCount = getCountWithId(em, BloodBank.class, Integer.class, BloodBank_.id,
				deletedBloodBank.getId());
		long bloodBankCount = getCountWithId(em, BloodBank.class, Integer.class, BloodBank_.id, bloodBank.getId());	
		//For deleted records, the assertion count is 0 and our old blood bank still exists
		assertThat(deletedBloodBankCount, is(equalTo(0L)));
		assertThat(bloodBankCount, is(equalTo(1L)));
	}


	
}
