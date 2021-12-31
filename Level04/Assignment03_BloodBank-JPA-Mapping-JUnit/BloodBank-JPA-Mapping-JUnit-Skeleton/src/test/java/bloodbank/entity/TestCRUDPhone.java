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
public class TestCRUDPhone extends JUnitBase {
	private EntityManager em;
	private EntityTransaction et;
	private static Phone phone;

	@BeforeAll
	static void setupAllInit() {
		phone = new Phone();
		phone.setNumber("444", "555", "6666");
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
		// Get all the results from the address
		long result = getTotalCount(em, Phone.class);
		// The result should be 0
		assertThat(result, is(comparesEqualTo(0L)));
	}

	@Order(2)
	@Test
	void testCreate() {
		et.begin();
		em.persist(phone);
		et.commit();

		// Get the count of records with id of address obj
		long result = getCountWithId(em, Phone.class, Integer.class, Phone_.id, phone.getId());

		// Check count is 1
		assertThat(result, is(comparesEqualTo(1L)));
	}

	@Order(3)
	@Test
	void testCreateInvalid() {
		et.begin();
		Phone invalidPhone = new Phone();

		assertThrows(PersistenceException.class, () -> em.persist(invalidPhone));
		et.commit();
	}

	@Test
	void testRead() {
		List<Phone> phoneList = getAll(em, Phone.class);
		assertThat(phoneList.get(0), equalTo(phone));
	}

	@Test
	void testUpdate() {
		String updatedCountryCode = "444";
		String updatedAreaCode = "555";
		String updatedNumber = "6666";

		Phone returnedPhone = getWithId(em, Phone.class, Integer.class, Phone_.id, phone.getId());

		returnedPhone.setNumber(updatedCountryCode, updatedAreaCode, updatedNumber);

		assertThat(returnedPhone.getCountryCode(), equalTo(updatedCountryCode));
		assertThat(returnedPhone.getAreaCode(), equalTo(updatedAreaCode));
		assertThat(returnedPhone.getNumber(), equalTo(updatedNumber));
	}

	@Order(Integer.MAX_VALUE)
	@Test
	void testDelete() {
		Phone phoneToDelete = new Phone();
		phoneToDelete.setNumber("333", "444", "5555");

		et.begin();
		em.persist(phoneToDelete);
		et.commit();

		et.begin();
		em.remove(phoneToDelete);
		et.commit();

		long deletedCount = getCountWithId(em, Phone.class, Integer.class, Phone_.id, phoneToDelete.getId());
		long phoneCount = getCountWithId(em, Phone.class, Integer.class, Phone_.id, phone.getId());

		assertThat(deletedCount, is(equalTo(0L)));
		assertThat(phoneCount, is(equalTo(1L)));
	}

}
