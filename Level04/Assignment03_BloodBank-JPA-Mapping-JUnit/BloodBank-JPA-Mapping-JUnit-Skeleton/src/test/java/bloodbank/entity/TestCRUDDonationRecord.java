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
public class TestCRUDDonationRecord extends JUnitBase {
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
		bloodBank = new PrivateBloodBank();
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
		long result = getTotalCount(em, DonationRecord.class);
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
		long result = getCountWithId(em, DonationRecord.class, Integer.class, BloodBank_.id, donationRecord.getId());
		// Check count is 1
		assertThat(result, is(comparesEqualTo(1L)));
	}

	@Order(3)
	@Test
	void testCreateInvalid() {
		et.begin();
		DonationRecord invalidDonationRecord = new DonationRecord();
		// We expect this test should fail, because the NULL is a required field
		assertThrows(PersistenceException.class, () -> em.persist(invalidDonationRecord));
		et.commit();
	}

	@Test
	void testRead() {
		List<DonationRecord> donationRecordList = (List<DonationRecord>) getAll(em, DonationRecord.class);

		assertThat(donationRecordList.get(0), equalTo(donationRecord));
	}

	@Test
	void ReadDependencies() {
		DonationRecord returnedDonationRecord = getWithId(em, DonationRecord.class, Integer.class, DonationRecord_.id,
				donationRecord.getId());

		assertThat(returnedDonationRecord.getOwner(), equalTo(donationRecord.getOwner()));
		assertThat(returnedDonationRecord.getDonation(), equalTo(donationRecord.getDonation()));
	}

	@Test
	void testUpdate() {
		DonationRecord returnedDonationRecord = getWithId(em, DonationRecord.class, Integer.class, DonationRecord_.id,
				donationRecord.getId());

		returnedDonationRecord.setTested(true);
		byte updatedTested = returnedDonationRecord.getTested();

		et.begin();
		em.merge(returnedDonationRecord);
		et.commit();

		returnedDonationRecord = getWithId(em, DonationRecord.class, Integer.class, DonationRecord_.id,
				donationRecord.getId());

		assertThat(returnedDonationRecord.getTested(), equalTo(updatedTested));
	}

	@Test
	void testUpdateDependencies() {
		String updatedFirstName = "Nao";
		String updatedLastName = "Aizo";

		DonationRecord returnedDonationRecord = getWithId(em, DonationRecord.class, Integer.class, DonationRecord_.id,
				donationRecord.getId());
		returnedDonationRecord.getOwner().setFullName(updatedFirstName, updatedLastName);

		et.begin();
		em.merge(returnedDonationRecord);
		et.commit();

		returnedDonationRecord = getWithId(em, DonationRecord.class, Integer.class, DonationRecord_.id,
				donationRecord.getId());

		// assertThat(returnedDonationRecord.getOwner(),
		// equalTo(donationRecord.getOwner()));
		assertThat(returnedDonationRecord.getOwner().getFirstName(), equalTo(updatedFirstName));
		assertThat(returnedDonationRecord.getOwner().getLastName(), equalTo(updatedLastName));
	}

	@Order(Integer.MAX_VALUE)
	@Test
	void testDelete() {
		DonationRecord donationRecordToDelete = getWithId(em, DonationRecord.class, Integer.class, DonationRecord_.id,
				donationRecord.getId());

		et.begin();
		em.merge(donationRecordToDelete);
		et.commit();

		long count = getCountWithId(em, DonationRecord.class, Integer.class, DonationRecord_.id,
				donationRecordToDelete.getId());

		assertThat(count, is(equalTo(1L)));

	}

}
