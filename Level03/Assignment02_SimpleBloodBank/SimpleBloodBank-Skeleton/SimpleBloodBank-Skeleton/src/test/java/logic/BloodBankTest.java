package logic;

import common.EMFactory;
import common.TomcatStartUp;
import common.ValidationException;
import entity.BloodBank;
import entity.Person;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Hui Lyu
 */
public class BloodBankTest {

    private BloodBankLogic logic;
    private PersonLogic personLogic;
    private BloodBank expectedEntity;
    Person person;

    @BeforeAll
    final static void setUpBeforeClass() throws Exception {
        TomcatStartUp.createTomcat("/SimpleBloodBank", "common.ServletListener", "simplebloodbank-PU-test");
    }

    @AfterAll
    final static void tearDownAfterClass() throws Exception {
        TomcatStartUp.stopAndDestroyTomcat();
    }

    @BeforeEach
    // dependency

    final void setUp() throws Exception {
        logic = LogicFactory.getFor("BloodBank");
        personLogic = LogicFactory.getFor("Person");

        EntityManager em = EMFactory.getEMF().createEntityManager();
        //start a transaction
        em.getTransaction().begin();

        person = em.find(Person.class, 1);
        if (person == null) {
            person = new Person();
            person.setFirstName("Junit");
            person.setLastName("Yu");
            person.setPhone("613-252-2836");
            person.setAddress("kanata");
            person.setBirth(logic.convertStringToDateTime("1111-11-11T11:11"));
            em.persist(person);
        }

        //create the desired entity
        BloodBank entity = new BloodBank();
        entity.setName("Junit");
        entity.setPrivatelyOwned(true);
        entity.setEstablished(logic.convertStringToDateTime("2020-12-12T11:11"));
        entity.setEmplyeeCount(1111);
        //add dependency to the desired entity
        entity.setOwner(person);
        //add desired entity to hibernate, entity is now managed.
        //we use merge instead of add so we can get the managed entity.
        expectedEntity = em.merge(entity);
        //commit the changes
        em.getTransaction().commit();
        //close EntityManager
        em.close();

    }

    @AfterEach
    final void tearDown() throws Exception {
        if (expectedEntity != null) {
            logic.delete(expectedEntity);
        }
        PersonLogic pLogic = LogicFactory.getFor("Person");
        if (expectedEntity.getOwner() != null) {
            pLogic.delete(expectedEntity.getOwner());
        }
    }

    @Test
    final void testGetAll() {
        List<BloodBank> list = logic.getAll();

        //store the size of the list
        int originalSize = list.size();

        // make sure blood bank was created successfully
        assertNotNull(expectedEntity);

        logic.delete(expectedEntity);

        //get all blood banks again
        list = logic.getAll();

        assertEquals(originalSize - 1, list.size());
    }

    private void assertBloodBankEquals(BloodBank expected, BloodBank actual) {
        //assert all field to guarantee they are the same
        assertBloodBankEquals(expected, actual, true);
    }

    private void assertBloodBankEquals(BloodBank expected, BloodBank actual, boolean hasOwner) {
        //assert all field to guarantee they are the same
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getEstablished(), actual.getEstablished());
        assertEquals(expected.getPrivatelyOwned(), actual.getPrivatelyOwned());
        assertEquals(expected.getEmplyeeCount(), actual.getEmplyeeCount());
        if(hasOwner)
            assertEquals(expected.getOwner().getId(), actual.getOwner().getId());
    }

    @Test
    final void testGetWithId() {
        BloodBank returnBloodBank = logic.getWithId(expectedEntity.getId());

        assertBloodBankEquals(expectedEntity, returnBloodBank);
    }

    @Test
    final void testGetBloodBankWithName() {
        BloodBank returnBloodBank = logic.getBloodBankWithName(expectedEntity.getName());

        assertBloodBankEquals(expectedEntity, returnBloodBank);
    }

    @Test
    final void testGetBloodBankWithEstablished() {
        int foundFull = 0;
        List<BloodBank> returnedBloodBanks = logic.getBloodBankWithEstablished(expectedEntity.getEstablished());
        for (BloodBank bloodBank : returnedBloodBanks) {
            assertEquals(expectedEntity.getEstablished(), bloodBank.getEstablished());

           if (bloodBank.getEstablished().compareTo(expectedEntity.getEstablished())==0) {
             
                assertBloodBankEquals(expectedEntity, bloodBank);
                foundFull++;
            }
        }
        assertEquals(1, foundFull, "if zero means not found, if more than one means duplicate");
    }

    @Test
    final void testGetBloodBankWithPrivatelyOwned() {
        int foundFull = 0;
        List<BloodBank> returnBloodBank = logic.getBloodBankWithPrivatelyOwned(expectedEntity.getPrivatelyOwned());
        for (BloodBank bloodBank : returnBloodBank) {
            assertEquals(expectedEntity.getPrivatelyOwned(), bloodBank.getPrivatelyOwned());

            // can I use == instead of equals()
            if (bloodBank.getPrivatelyOwned() == (expectedEntity.getPrivatelyOwned())) {
                assertBloodBankEquals(expectedEntity, bloodBank);
                foundFull++;
            }
        }
        assertEquals(1, foundFull, "if zero means not found, if more than one means duplicate");

    }

    @Test
    final void testGetBloodBanksWithOwner() {
        // data type ???
        BloodBank returnedBloodBank = logic.getBloodBanksWithOwner(expectedEntity.getOwner().getId());
        assertBloodBankEquals(expectedEntity, returnedBloodBank);
    }

    @Test
    final void testGetBloodBanksWithEmployeeCount() {
        int foundFull = 0;
        List<BloodBank> returnBloodBank = logic.getBloodBanksWithEmployeeCount(expectedEntity.getEmplyeeCount());
        for (BloodBank bloodBank : returnBloodBank) {
            assertEquals(expectedEntity.getEmplyeeCount(), bloodBank.getEmplyeeCount());
            // int == int ??
            if (bloodBank.getEmplyeeCount() == (expectedEntity.getEmplyeeCount())) {
                assertBloodBankEquals(expectedEntity, bloodBank);
                foundFull++;
            }
        }
        assertEquals(1, foundFull, "if zero means not found, if more than one means duplicate");
    }

    @Test
    final void testCreateEntityAndAdd() {
        // based on database to setup variables
        Map<String, String[]> sampleMap = new HashMap<>();
        sampleMap.put(BloodBankLogic.NAME, new String[]{"create"});
        sampleMap.put(BloodBankLogic.OWNER_ID, new String[]{"3"});
        sampleMap.put(BloodBankLogic.PRIVATELY_OWNED, new String[]{Boolean.toString(true)});
        sampleMap.put(BloodBankLogic.ESTABLISHED, new String[]{logic.convertDateTimeToString(expectedEntity.getEstablished())});
        sampleMap.put(BloodBankLogic.EMPLOYEE_COUNT, new String[]{Integer.toString(1111)});
        BloodBank returnBloodBank = logic.createEntity(sampleMap);
        logic.add(returnBloodBank);
        
        returnBloodBank = logic.getBloodBankWithName(returnBloodBank.getName());

        assertEquals(sampleMap.get(BloodBankLogic.NAME)[0], returnBloodBank.getName());
        assertEquals(sampleMap.get(BloodBankLogic.PRIVATELY_OWNED)[0], Boolean.toString(returnBloodBank.getPrivatelyOwned()));
        assertEquals(sampleMap.get(BloodBankLogic.ESTABLISHED)[0], logic.convertDateTimeToString(returnBloodBank.getEstablished()));
        assertEquals(sampleMap.get(BloodBankLogic.EMPLOYEE_COUNT)[0], Integer.toString(returnBloodBank.getEmplyeeCount()));

        logic.delete(returnBloodBank);
    }

    @Test
    final void testCreateEntity() {

        Map<String, String[]> sampleMap = new HashMap<>();
        sampleMap.put(BloodBankLogic.ID, new String[]{Integer.toString(expectedEntity.getId())});
        sampleMap.put(BloodBankLogic.NAME, new String[]{expectedEntity.getName()});
       // sampleMap.put(BloodBankLogic.OWNER_ID, new String[]{Integer.toString(expectedEntity.getOwner().getId())});
       sampleMap.put(BloodBankLogic.OWNER_ID, new String[]{"3"});
        sampleMap.put(BloodBankLogic.PRIVATELY_OWNED, new String[]{Boolean.toString(expectedEntity.getPrivatelyOwned())});
        // date to String
        sampleMap.put(BloodBankLogic.ESTABLISHED, new String[]{logic.convertDateTimeToString(expectedEntity.getEstablished())});
        sampleMap.put(BloodBankLogic.EMPLOYEE_COUNT, new String[]{Integer.toString(expectedEntity.getEmplyeeCount())});

        BloodBank returnedBloodBank = logic.createEntity(sampleMap);
        assertBloodBankEquals(expectedEntity, returnedBloodBank, false);
    }

    @Test
    final void testCreateEntityNullAndEmptyValues() {
        Map<String, String[]> sampleMap = new HashMap<>();
        Consumer<Map<String, String[]>> fillMap = (Map<String, String[]> map) -> {
            map.clear();
            map.put(BloodBankLogic.ID, new String[]{Integer.toString(expectedEntity.getId())});
         //   sampleMap.put(BloodBankLogic.OWNER_ID, new String[]{Integer.toString(expectedEntity.getOwner().getId())});
            sampleMap.put(BloodBankLogic.OWNER_ID, new String[]{"3"});
            map.put(BloodBankLogic.NAME, new String[]{expectedEntity.getName()});
            map.put(BloodBankLogic.PRIVATELY_OWNED, new String[]{Boolean.toString(expectedEntity.getPrivatelyOwned())});
            // date to String
            map.put(BloodBankLogic.ESTABLISHED, new String[]{logic.convertDateTimeToString(expectedEntity.getEstablished())});
            map.put(BloodBankLogic.EMPLOYEE_COUNT, new String[]{Integer.toString(expectedEntity.getEmplyeeCount())});
        };

        fillMap.accept(sampleMap);
        sampleMap.replace(BloodBankLogic.ID, null);
        assertThrows(NullPointerException.class, () -> logic.createEntity(sampleMap));
        sampleMap.replace(BloodBankLogic.ID, new String[]{});
        assertThrows(IndexOutOfBoundsException.class, () -> logic.createEntity(sampleMap));

        fillMap.accept(sampleMap);
        sampleMap.replace(BloodBankLogic.NAME, null);
        assertThrows(NullPointerException.class, () -> logic.createEntity(sampleMap));
        sampleMap.replace(BloodBankLogic.NAME, new String[]{});
        assertThrows(IndexOutOfBoundsException.class, () -> logic.createEntity(sampleMap));

        fillMap.accept(sampleMap);
        sampleMap.replace(BloodBankLogic.PRIVATELY_OWNED, null);
        assertThrows(NullPointerException.class, () -> logic.createEntity(sampleMap));
        sampleMap.replace(BloodBankLogic.PRIVATELY_OWNED, new String[]{});
        assertThrows(IndexOutOfBoundsException.class, () -> logic.createEntity(sampleMap));

        fillMap.accept(sampleMap);
        sampleMap.replace(BloodBankLogic.ESTABLISHED, null);
        assertThrows(NullPointerException.class, () -> logic.createEntity(sampleMap));
        sampleMap.replace(BloodBankLogic.ESTABLISHED, new String[]{});
        assertThrows(IndexOutOfBoundsException.class, () -> logic.createEntity(sampleMap));
        
        fillMap.accept(sampleMap);
        sampleMap.replace(BloodBankLogic.EMPLOYEE_COUNT, null);
        assertThrows(NullPointerException.class, () -> logic.createEntity(sampleMap));
        sampleMap.replace(BloodBankLogic.EMPLOYEE_COUNT, new String[]{});
        assertThrows(IndexOutOfBoundsException.class, () -> logic.createEntity(sampleMap));
    }

    
    @Test
    final void testCreateEntityBadLengthValues(){
        Map<String, String[]> sampleMap = new HashMap<>();
        Consumer<Map<String, String[]>> fillMap = (Map<String, String[]> map) ->{
        map.clear();
        map.put(BloodBankLogic.ID, new String[]{Integer.toString(expectedEntity.getId())});
        map.put(BloodBankLogic.NAME, new String[]{expectedEntity.getName()});
        sampleMap.put(BloodBankLogic.OWNER_ID, new String[]{Integer.toString(expectedEntity.getOwner().getId())});
        map.put(BloodBankLogic.PRIVATELY_OWNED, new String[]{Boolean.toString(expectedEntity.getPrivatelyOwned())});
            // date to String
        map.put(BloodBankLogic.ESTABLISHED, new String[]{logic.convertDateTimeToString(expectedEntity.getEstablished())});
        map.put(BloodBankLogic.EMPLOYEE_COUNT, new String[]{Integer.toString(expectedEntity.getEmplyeeCount())});
              
        };
        
        IntFunction<String> generateString = (int length) ->{
            https://www.baeldung.com/java-random-string#java8-alphabetic
            
            return new Random().ints('a','z' + 1).limit(length)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
        };
                
        //idealy every test should be in its own method
        fillMap.accept(sampleMap);
        sampleMap.replace(BloodBankLogic.ID, new String[]{""});
        assertThrows(ValidationException.class, ()-> logic.createEntity(sampleMap));
        sampleMap.replace(BloodBankLogic.ID, new String[]{"12b"});
        assertThrows(ValidationException.class, ()-> logic.createEntity(sampleMap));
        
        fillMap.accept(sampleMap);
        sampleMap.replace(BloodBankLogic.NAME, new String[]{""});
        assertThrows(ValidationException.class, ()-> logic.createEntity(sampleMap));
        sampleMap.replace(BloodBankLogic.NAME, new String[]{generateString.apply(101)});
        //AssertionFailedError: Expected common.ValidationException to be thrown, but nothing was thrown.
        assertThrows(ValidationException.class, ()-> logic.createEntity(sampleMap));
    }
    
    @Test
    final void testCreateEntityEdgeValues(){
                IntFunction<String> generateString = ( int length ) -> {
            //https://www.baeldung.com/java-random-string#java8-alphabetic
            return new Random().ints( 'a', 'z' + 1 ).limit( length )
                    .collect( StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append )
                    .toString();
        };
                
        Map<String, String[]> sampleMap = new HashMap<>();
        sampleMap.put(BloodBankLogic.ID, new String[]{ Integer.toString(1)});
        sampleMap.put(BloodBankLogic.NAME, new String[]{ generateString.apply(1)});
        sampleMap.put(BloodBankLogic.OWNER_ID, new String[]{"3"});
        sampleMap.put(BloodBankLogic.PRIVATELY_OWNED, new String[]{(Boolean.toString(true))});
        // data
        sampleMap.put(BloodBankLogic.ESTABLISHED, new String[]{logic.convertDateTimeToString((expectedEntity.getEstablished())) });
        sampleMap.put(BloodBankLogic.EMPLOYEE_COUNT, new String[]{ Integer.toString(1)});
        
        BloodBank returnedBloodBank = logic.createEntity(sampleMap);
        assertEquals( Integer.parseInt( sampleMap.get( BloodBankLogic.ID )[ 0 ] ), returnedBloodBank.getId() );
        assertEquals(sampleMap.get(BloodBankLogic.NAME)[0],returnedBloodBank.getName());
        assertEquals(sampleMap.get(BloodBankLogic.PRIVATELY_OWNED)[0],Boolean.toString(returnedBloodBank.getPrivatelyOwned()));
        assertEquals(sampleMap.get(BloodBankLogic.ESTABLISHED)[0],logic.convertDateTimeToString(returnedBloodBank.getEstablished()));
        assertEquals(sampleMap.get(BloodBankLogic.EMPLOYEE_COUNT)[0],Integer.toString(returnedBloodBank.getEmplyeeCount()));
        
        sampleMap = new HashMap<>();
        sampleMap.put( BloodBankLogic.ID, new String[]{ Integer.toString( 1 ) } );
        sampleMap.put(BloodBankLogic.OWNER_ID, new String[]{"3"});
        sampleMap.put( BloodBankLogic.NAME, new String[]{ generateString.apply( 100 ) } );
        sampleMap.put( BloodBankLogic.PRIVATELY_OWNED, new String[]{ Boolean.toString(true)} );
        sampleMap.put( BloodBankLogic.ESTABLISHED, new String[]{ logic.convertDateTimeToString((expectedEntity.getEstablished())) } );
        sampleMap.put( BloodBankLogic.EMPLOYEE_COUNT, new String[]{ Integer.toString(1) } );
        
        
        returnedBloodBank = logic.createEntity( sampleMap );
        assertEquals( Integer.parseInt( sampleMap.get( BloodBankLogic.ID )[ 0 ] ), returnedBloodBank.getId() );
        assertEquals(sampleMap.get(BloodBankLogic.NAME)[0],returnedBloodBank.getName());
        assertEquals(sampleMap.get(BloodBankLogic.PRIVATELY_OWNED)[0],Boolean.toString(returnedBloodBank.getPrivatelyOwned()));
        assertEquals(sampleMap.get(BloodBankLogic.ESTABLISHED)[0],logic.convertDateTimeToString(returnedBloodBank.getEstablished()));
        assertEquals(sampleMap.get(BloodBankLogic.EMPLOYEE_COUNT)[0],Integer.toString(returnedBloodBank.getEmplyeeCount()));
    }
     
    @Test
    final void testGetColumnNames() {
        List<String> list = logic.getColumnNames();
        assertEquals(Arrays.asList("Bank_ID", "Owner", "Name",
                "Privately Owned", "Established", "Employee Count"), list);
    }

    @Test
    final void testGetColumnCodes() {
        List<String> list = logic.getColumnCodes();
        assertEquals(Arrays.asList(BloodBankLogic.ID, BloodBankLogic.OWNER_ID, BloodBankLogic.NAME, BloodBankLogic.PRIVATELY_OWNED, BloodBankLogic.ESTABLISHED,
                BloodBankLogic.EMPLOYEE_COUNT), list);
    }

    @Test
    final void testExtractDataAsList() {
        // does the order follow the database
        List<?> list = logic.extractDataAsList(expectedEntity);
        assertEquals(expectedEntity.getId().toString(), list.get(0));
        assertEquals(expectedEntity.getOwner().getId(), list.get(1));
        assertEquals(expectedEntity.getName(), list.get(2));
        assertEquals(expectedEntity.getPrivatelyOwned(), list.get(3));
        assertEquals(logic.convertDateToString(expectedEntity.getEstablished()),list.get(4));
        assertEquals(expectedEntity.getEmplyeeCount(), list.get(5));
    }
}
