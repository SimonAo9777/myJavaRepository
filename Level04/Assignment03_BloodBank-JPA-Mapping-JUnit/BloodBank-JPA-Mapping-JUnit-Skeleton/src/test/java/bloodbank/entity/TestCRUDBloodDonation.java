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
public class TestCRUDBloodDonation extends JUnitBase {
	private EntityManager em;
	private EntityTransaction et;

	private static BloodBank bloodBank;
	private static String NAME = "Simzhuo Blood Bank";
	private static BloodDonation bloodDonation;
	private static Set<BloodDonation> donations;
	private static BloodType bloodType;
	private static DonationRecord donationRecord;
	private static Person person;

	@BeforeAll
	static void setupAllInit() {
		// Create PrivateBloodBank object
		bloodBank = new PrivateBloodBank();
		bloodDonation = new BloodDonation();
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
		// Get all the results from the BloodDonation table
		long result = getTotalCount(em, BloodDonation.class);
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
		// Get the count of records with id of PrivateBloodBank obj
		long result = getCountWithId(em, BloodDonation.class, Integer.class, BloodDonation_.id, bloodDonation.getId());
		// Check count is 1
		assertThat(result, is(comparesEqualTo(1L)));
	}

	@Order(3)
	@Test
	void testCreateInvalid() {
		et.begin();
		BloodDonation invalidBloodDonation = new BloodDonation();
		// We expect this test should fail, because the NULL is a required field
		assertThrows(PersistenceException.class, () -> em.persist(invalidBloodDonation));
		et.commit();
	}

	@SuppressWarnings("unchecked")
	@Test
	void testRead() {
		List<BloodDonation> bloodDonationList = (List<BloodDonation>) getAll(em, BloodDonation.class);
		assertThat(bloodDonationList.get(0), equalTo(bloodDonation));
	}

	@Test
	void testReadDependencies() {
		BloodDonation returnedBloodDonation = getWithId(em, BloodDonation.class, Integer.class, BloodDonation_.id,
				bloodDonation.getId());

		assertThat(returnedBloodDonation.getBank(), equalTo(bloodDonation.getBank()));
		assertThat(returnedBloodDonation.getRecord(), equalTo(bloodDonation.getRecord()));
		assertThat(returnedBloodDonation.getMilliliters(), equalTo(bloodDonation.getMilliliters()));
		assertThat(returnedBloodDonation.getBloodType(), equalTo(bloodDonation.getBloodType()));
	}

	@Test
	void testUpdate() {
		int milliliters = 5;
		// Get the blood bank and rename it
		BloodDonation updatedBloodDonation = getWithId(em, BloodDonation.class, Integer.class, BloodDonation_.id,
				bloodDonation.getId());
		updatedBloodDonation.setMilliliters(milliliters);
		// Commit to database
		et.begin();
		em.merge(updatedBloodDonation);
		et.commit();
		// Get the BloodDonation again and check that the update value is correct
		updatedBloodDonation = getWithId(em, BloodDonation.class, Integer.class, BloodBank_.id, bloodDonation.getId());
		assertThat(updatedBloodDonation.getMilliliters(), equalTo(milliliters));
	}

	@Test
	void testUpdateDependencies() {
		// Get to BloodDonation and replacement donations
		BloodDonation returnedBloodDonation = getWithId(em, BloodDonation.class, Integer.class, BloodDonation_.id,
				bloodDonation.getId());

		// Updates
		returnedBloodDonation.getBloodType().setType("AB", "+");
		returnedBloodDonation.getBank().setName("Updated Bank");
		returnedBloodDonation.getRecord().setTested(true);
		// Commit to database
		et.begin();
		em.merge(returnedBloodDonation);
		et.commit();
		// Get to BloodDonation and replacement donations
		BloodDonation updatedBloodDonation = getWithId(em, BloodDonation.class, Integer.class, BloodDonation_.id,
				returnedBloodDonation.getId());

		assertThat(returnedBloodDonation.getBloodType(), equalTo(updatedBloodDonation.getBloodType()));
		assertThat(returnedBloodDonation.getBank(), equalTo(updatedBloodDonation.getBank()));
		assertThat(returnedBloodDonation.getRecord(), equalTo(updatedBloodDonation.getRecord()));
	}

	@Order(Integer.MAX_VALUE - 1)
	@Test
	void testDeleteDependency() {
		// //Get to BloodDonation and delete donationRecord
		BloodDonation returnedBloodDonation = getWithId(em, BloodDonation.class, Integer.class, BloodDonation_.id,
				bloodDonation.getId());
		returnedBloodDonation.setRecord(null);

		// Commit to database
		et.begin();
		em.merge(returnedBloodDonation);
		et.commit();
		// Get the bloodBank again and check that the donation is NULL
		returnedBloodDonation = getWithId(em, BloodDonation.class, Integer.class, BloodDonation_.id,
				bloodDonation.getId());
		assertThat(returnedBloodDonation.getRecord(), is(nullValue()));

	}

	@Order(Integer.MAX_VALUE)
	@Test
	void testDelete() {
		BloodDonation bloodDonationToDelete = getWithId(em, BloodDonation.class, Integer.class, BloodDonation_.id,
				bloodDonation.getId());
		et.begin();
		em.remove(bloodDonationToDelete);
		et.commit();
		long deletedBloodDonationCount = getCountWithId(em, BloodDonation.class, Integer.class, BloodDonation_.id,
				bloodDonationToDelete.getId());
		assertThat(deletedBloodDonationCount, is(equalTo(1L)));

	}

}
