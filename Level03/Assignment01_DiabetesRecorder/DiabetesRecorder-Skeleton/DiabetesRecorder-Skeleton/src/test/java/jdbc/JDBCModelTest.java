package jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Connection;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * CST_8288_21F Assignment 1 JUnit 5 Test
 * 
 * @author Simon Ao 040983402
 * @version July 4,2021
 * @References: Professor's tutorials
 * 
 */

class JDBCModelTest {
	private static final String URL = "jdbc:mysql://localhost:3306/diabetesrecord?useUnicode=true&serverTimezone=UTC";
	private static final String USER = "cst8288";
	private static final String PASS = "8288";

	private JDBCModel model = new JDBCModel();
	Connection connection = null;

	@BeforeEach
	void setUp() {
		model.setCredentials(USER, PASS);
		try {
			model.connectTo(URL);
			assertTrue(model.isConnected());
		} catch (Exception e) {
			fail(e);
		}
	}

	@AfterEach
	void closeDown() {
		try {
			model.close();
		} catch (Exception e) {
			fail(e);
		}
	}

	/**
	 * 
	 * Test valid URL
	 */
	@Test
	void testIsConnected() {
		try {
			assertTrue(model.isConnected());
		} catch (Exception e) {
			fail(e);
		}
	}

	/**
	 * 
	 * Test invalid URL
	 */
	@Test
	void testIsConnectedInCorrect() {
		String upurl = "www.algonquincollege.com";
		assertThrows(Exception.class, () -> model.connectTo(upurl));
	}

	/**
	 * 
	 * Test login parameters
	 */
	@Test
	void testLoginWith() {
		try {
			assertTrue(model.isConnected());
			String username = "John";
			String password = "pass";
			assertEquals(2, model.loginWith(username, password));
		} catch (Exception e) {
			fail(e);
		}
	}

	/**
	 * Test getAllGlucoseNumbers
	 */
	@Test
	void testGetAllGlucoseNumbers() {
		int id = 2;
		List<List<Object>> ll = null;
		try {
			assertTrue(model.isConnected());
			ll = model.getAllGlucoseNumbers(id);
		} catch (Exception e) {
			fail(e);
		}
		assertEquals(40, ll.size());
	}

	/**
	 * Test getEntryTypes
	 */
	@Test
	void testGetEntryTypes() {
		List<String> li = null;
		try {
			assertTrue(model.isConnected());
			li = model.getEntryTypes();
		} catch (Exception e) {
			fail(e);
		}
		assertTrue(li.get(0).equalsIgnoreCase("After Meal"));
		assertTrue(li.get(1).equalsIgnoreCase("Bedtime"));
		assertTrue(li.get(2).equalsIgnoreCase("Before Meal"));
		assertTrue(li.get(3).equalsIgnoreCase("Fasting"));
		assertTrue(li.get(4).equalsIgnoreCase("Other"));
	}

	/**
	 * Test GetColumnNames
	 */
	@Test
	void testGetColumnNames() {
		List<String> li;
		try {
			assertTrue(model.isConnected());
			li = model.getColumnNames();
			assertTrue(li.contains("Id"));
			assertTrue(li.contains("EntryType"));
			assertTrue(li.contains("GlucoseValue"));
			assertTrue(li.contains("TakenAt"));
		} catch (Exception e) {
			fail(e);
		}
	}

	/**
	 * Test Close connection
	 */

	
	/**
	 * Test Close connection
	 */
	@Test
	void testCloseMethod() {
		try {
			assertTrue(model.isConnected());
			model.close();
			assertFalse(model.isConnected());
		} catch (Exception e) {
			fail(e);
		}
	}

}
