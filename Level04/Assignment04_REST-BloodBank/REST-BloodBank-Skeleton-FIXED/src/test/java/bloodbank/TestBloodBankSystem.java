/**
 * File: OrderSystemTestSuite.java
 * Course materials (21F) CST 8277
 * Teddy Yap
 * (Original Author) Mike Norman
 *
 * @date 2020 10
 *
 * (Modified) @author Student Name
 */
package bloodbank;

import static bloodbank.utility.MyConstants.APPLICATION_API_VERSION;
import static bloodbank.utility.MyConstants.APPLICATION_CONTEXT_ROOT;
import static bloodbank.utility.MyConstants.DEFAULT_ADMIN_USER;
import static bloodbank.utility.MyConstants.DEFAULT_ADMIN_USER_PASSWORD;
import static bloodbank.utility.MyConstants.DEFAULT_USER_PASSWORD;
import static bloodbank.utility.MyConstants.DEFAULT_USERNAME;
import static bloodbank.utility.MyConstants.PERSON_RESOURCE_NAME;
import static bloodbank.utility.MyConstants.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;

import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.logging.LoggingFeature;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import bloodbank.entity.Address;
import bloodbank.entity.BloodDonation;
import bloodbank.entity.Contact;
import bloodbank.entity.Person;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class TestBloodBankSystem {
    private static final Class<?> _thisClaz = MethodHandles.lookup().lookupClass();
    private static final Logger logger = LogManager.getLogger(_thisClaz);

    static final String HTTP_SCHEMA = "http";
    static final String HOST = "localhost";
    static final int PORT = 8080;

    // test fixture(s)
    static URI uri;
    static HttpAuthenticationFeature adminAuth;
    static HttpAuthenticationFeature userAuth;

    @BeforeAll
    public static void oneTimeSetUp() throws Exception {
        logger.debug("oneTimeSetUp");
        uri = UriBuilder
            .fromUri(APPLICATION_CONTEXT_ROOT + APPLICATION_API_VERSION)
            .scheme(HTTP_SCHEMA)
            .host(HOST)
            .port(PORT)
            .build();
        adminAuth = HttpAuthenticationFeature.basic(DEFAULT_ADMIN_USER, DEFAULT_ADMIN_USER_PASSWORD);
        userAuth = HttpAuthenticationFeature.basic(DEFAULT_USERNAME, DEFAULT_USER_PASSWORD);
    }

    protected WebTarget webTarget;
    @BeforeEach
    public void setUp() {
        Client client = ClientBuilder.newClient(
            new ClientConfig().register(MyObjectMapperProvider.class).register(new LoggingFeature()));
        webTarget = client.target(uri);
    }

    @Test
    public void test01_all_persons_with_adminrole() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            //.register(userAuth)
            .register(adminAuth)
            .path(PERSON_RESOURCE_NAME)
            .request()
           .get();
        assertThat(response.getStatus(), is(200));
        List<Person> persons = response.readEntity(new GenericType<List<Person>>(){});
        assertThat(persons, is(not(empty())));
        assertThat(persons, hasSize(1));
    }
    
    @Test
    public void test02_all_persons_with_userrole() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            .register(userAuth)
            //.register(adminAuth)
            .path(PERSON_RESOURCE_NAME)
            .request()
           .get();
        assertThat(response.getStatus(), is(401));
    }
    
    @Test
    public void test03_verify_person_response_is_json() {
    	//response is json
    	Response response = webTarget
                //.register(userAuth)
                .register(adminAuth)
                .path(PERSON_RESOURCE_NAME)
                .request()
                .get();
    	assertThat(response.getMediaType().toString(),is(MediaType.APPLICATION_JSON));
    }
    
    @Test
    public void test04_verify_person_response_is_not_xml() {
    	Response response = webTarget
                //.register(userAuth)
                .register(adminAuth)
                .path(PERSON_RESOURCE_NAME)
                .request()
                .get();
    	assertThat(response.getMediaType().toString(),is(not((MediaType.APPLICATION_XML))));
    }
    
    @Test
    public void test05_verify_get_person_by_id_unauthorized() {
    	//no previleges no access
    	Response response = webTarget
                //.register(userAuth)
                //.register(adminAuth)
                .path(PERSON_RESOURCE_NAME+"/1") //get user with ID 1
                .request()
                .get();
    	assertThat(response.getStatus(), is(401));
    }
    
    @Test
    public void test06_verify_get_person_by_id_admin() {
    	// admin privileges with access
    	Response response = webTarget
                //.register(userAuth)
                .register(adminAuth)
                .path(PERSON_RESOURCE_NAME+"/1") //get user with ID 1
                .request()
                .get();
    	assertThat(response.getStatus(), is(200));
    }
    
    
    
    @Test
    public void test07_all_bloodbank_with_adminrole() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            //.register(userAuth)
            .register(adminAuth)
            .path(BLOODBANK_RESOURCE_NAME)
            .request()
           .get();
        assertThat(response.getStatus(), is(200));
        List<Person> persons = response.readEntity(new GenericType<List<Person>>(){});
        assertThat(persons, is(not(empty())));
        assertThat(persons, hasSize(2));
    }
    
    @Test
    public void test08_all_bloodbank_with_userrole() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            .register(userAuth)
            //.register(adminAuth)
            .path(BLOODBANK_RESOURCE_NAME)
            .request()
           .get();
        assertThat(response.getStatus(), is(401));
    }
    
    @Test
    public void test09_verify_bloodbank_response_is_json() {
    	//response is json
    	Response response = webTarget
                //.register(userAuth)
                .register(adminAuth)
                .path(BLOODBANK_RESOURCE_NAME)
                .request()
                .get();
    	assertThat(response.getMediaType().toString(),is(MediaType.APPLICATION_JSON));
    }
    
    @Test
    public void test10_verify_bloodbank_response_is_not_xml() {
    	Response response = webTarget
                //.register(userAuth)
                .register(adminAuth)
                .path(BLOODBANK_RESOURCE_NAME)
                .request()
                .get();
    	assertThat(response.getMediaType().toString(),is(not((MediaType.APPLICATION_XML))));
    }
    
    @Test
    public void test11_all_blood_donations_with_adminrole() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            //.register(userAuth)
            .register(adminAuth)
            .path(BLOODDONATION_RESOURCE_NAME)
            .request()
           .get();
        assertThat(response.getStatus(), is(200));
        List<Person> persons = response.readEntity(new GenericType<List<Person>>(){});
        assertThat(persons, is(not(empty())));
        assertThat(persons, hasSize(2));
    }
    
    @Test
    public void test12_all_blood_donations_with_userrole() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            .register(userAuth)
            //.register(adminAuth)
            .path(BLOODDONATION_RESOURCE_NAME)
            .request()
           .get();
        assertThat(response.getStatus(), is(401));
    }
    
    @Test
    public void test13_all_blood_donations_with_adminrole() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            //.register(userAuth)
            .register(adminAuth)
            .path(BLOODDONATION_RESOURCE_NAME)
            .request()
           .get();
        assertThat(response.getStatus(), is(200));
        List<BloodDonation> bloodDonation = response.readEntity(new GenericType<List<BloodDonation>>(){});
        assertThat(bloodDonation, is(not(empty())));
        assertThat(bloodDonation, hasSize(2));
    }
    
    @Test
    public void test14_all_persons_with_user_role() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            .register(userAuth)
            //.register(adminAuth)
            .path(PERSON_RESOURCE_NAME)
            .request()
           .get();
        assertThat(response.getStatus(), is(401));
    }
    
    @Test
    public void test15_all_blooddonation_with_adminrole() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            //.register(userAuth)
            .register(adminAuth)
            .path(BLOODDONATION_RESOURCE_NAME)
            .request()
           .get();
        assertThat(response.getStatus(), is(200));
        List<Person> persons = response.readEntity(new GenericType<List<Person>>(){});
        assertThat(persons, is(not(empty())));
        assertThat(persons, hasSize(2));
    }
    
    @Test
    public void test16_verify_blooddonation_response_is_json() {
    	Response response = webTarget
                //.register(userAuth)
                .register(adminAuth)
                .path(BLOODDONATION_RESOURCE_NAME)
                .request()
                .get();
    	assertThat(response.getMediaType().toString(),is(MediaType.APPLICATION_JSON));
    }
    
    @Test
    public void test17_verify_blooddonation_response_is_not_xml() {
    	Response response = webTarget
                //.register(userAuth)
                .register(adminAuth)
                .path(BLOODDONATION_RESOURCE_NAME)
                .request()
                .get();
    	assertThat(response.getMediaType().toString(),is(not((MediaType.APPLICATION_XML))));
    }
    
    @Test
    public void test18_all_contact_with_adminrole() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            //.register(userAuth)
            .register(adminAuth)
            .path(CONTACT_RESOURCE_NAME)
            .request()
           .get();
        assertThat(response.getStatus(), is(200));
        List<Contact> contacts = response.readEntity(new GenericType<List<Contact>>(){});
        assertThat(contacts, is(not(empty())));
        assertThat(contacts, hasSize(2));
    }
    
    @Test
    public void test19_all_contact_with_userrole() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            .register(userAuth)
            //.register(adminAuth)
            .path(CONTACT_RESOURCE_NAME)
            .request()
           .get();
        assertThat(response.getStatus(), is(401));
    }
    
    @Test
    public void test20_verify_contact_response_is_json() {
    	Response response = webTarget
                //.register(userAuth)
                .register(adminAuth)
                .path(CONTACT_RESOURCE_NAME)
                .request()
                .get();
    	assertThat(response.getMediaType().toString(),is(MediaType.APPLICATION_JSON));
    }
    
    @Test
    public void test21_verify_contact_response_is_not_xml() {
    	Response response = webTarget
                //.register(userAuth)
                .register(adminAuth)
                .path(CONTACT_RESOURCE_NAME)
                .request()
                .get();
    	assertThat(response.getMediaType().toString(),is(not((MediaType.APPLICATION_XML))));
    }
    
    @Test
    public void test22_all_address_with_adminrole() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            //.register(userAuth)
            .register(adminAuth)
            .path(CUSTOMER_ADDRESS_SUBRESOURCE_NAME)
            .request()
           .get();
        assertThat(response.getStatus(), is(200));
        List<Address> Addresses = response.readEntity(new GenericType<List<Address>>(){});
        assertThat(Addresses, is(not(empty())));
        assertThat(Addresses, hasSize(1));
    }
    
    @Test
    public void test23_all_address_with_userrole() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            .register(userAuth)
            //.register(adminAuth)
            .path(CUSTOMER_ADDRESS_SUBRESOURCE_NAME)
            .request()
           .get();
        assertThat(response.getStatus(), is(401));
    }
    
    @Test 
    public void test24_verify_address_response_is_json() {
    	Response response = webTarget
                //.register(userAuth)
                .register(adminAuth)
                .path(CUSTOMER_ADDRESS_SUBRESOURCE_NAME)
                .request()
                .get();
    	assertThat(response.getMediaType().toString(),is(MediaType.APPLICATION_JSON));
    }
    
    @Test
    public void test25_verify_address_response_is_not_xml() {
    	Response response = webTarget
                //.register(userAuth)
                .register(adminAuth)
                .path(CUSTOMER_ADDRESS_SUBRESOURCE_NAME)
                .request()
                .get();
    	assertThat(response.getMediaType().toString(),is(not((MediaType.APPLICATION_XML))));
    }
    
    @Test
    public void test26_all_donationrecord_with_adminrole() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            //.register(userAuth)
            .register(adminAuth)
            .path(CUSTOMER_ADDRESS_SUBRESOURCE_NAME)
            .request()
           .get();
        assertThat(response.getStatus(), is(200));
        List<BloodDonation> bloodDonations = response.readEntity(new GenericType<List<BloodDonation>>(){});
        assertThat(bloodDonations, is(not(empty())));
        assertThat(bloodDonations, hasSize(1));
    }
    
    @Test
    public void test27_all_donationrecord_with_userrole() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            .register(userAuth)
            //.register(adminAuth)
            .path(CUSTOMER_ADDRESS_SUBRESOURCE_NAME)
            .request()
           .get();
        assertThat(response.getStatus(), is(401));
    }
    
    @Test
    public void test28_verify_donationrecord_response_is_json() {
    	Response response = webTarget
                //.register(userAuth)
                .register(adminAuth)
                .path(DONATION_RECORD_NAME)
                .request()
                .get();
    	assertThat(response.getMediaType().toString(),is(MediaType.APPLICATION_JSON));
    }
    
    @Test
    public void test29_verify_donationrecord_response_is_not_xml() {
    	Response response = webTarget
                //.register(userAuth)
                .register(adminAuth)
                .path(DONATION_RECORD_NAME)
                .request()
                .get();
    	assertThat(response.getMediaType().toString(),is(not((MediaType.APPLICATION_XML))));
    }
    
    @Test
    public void test30_verify_phone_response_is_json() {
    	Response response = webTarget
                //.register(userAuth)
                .register(adminAuth)
                .path(PHONE_NAME)
                .request()
                .get();
    	assertThat(response.getMediaType().toString(),is(MediaType.APPLICATION_JSON));
    }
    
    @Test
    public void test31_verify_phone_response_is_not_xml() {
    	Response response = webTarget
                //.register(userAuth)
                .register(adminAuth)
                .path(PHONE_NAME)
                .request()
                .get();
    	assertThat(response.getMediaType().toString(),is(not((MediaType.APPLICATION_XML))));
    }
    
//  @Test
//  public void test09_verify_get_bloodbank_by_id_unauthorized() {
//  	//no previleges no access
//  	Response response = webTarget
//              //.register(userAuth)
//              //.register(adminAuth)
//              .path(BLOODBANK_RESOURCE_NAME+"/1") //get user with ID 1
//              .request()
//              .get();
//  	assertThat(response.getStatus(), is(401));
//  	
//  }
  
//  @Test
//  public void test10_verify_get_bloodbank_by_id_admin() {
//  	// admin privileges with access
//  	Response response = webTarget
//              //.register(userAuth)
//              .register(adminAuth)
//              .path(BLOODBANK_RESOURCE_NAME+"/1") //get user with ID 1
//              .request()
//              .get();
//  	assertThat(response.getStatus(), is(200));
//  }
  
  //Contacts:
  //1. READ
  //2. CREATE - CREATE A WRONG CONTACT
  //3. UPDATE - UPDATE IT SO IT'S CORRECT
  //4. DELETE - DELETE IT
  
//  @Test
//  @Order(1)
//  public void test11_all_contacts_with_adminrole() throws JsonMappingException, JsonProcessingException {
//      Response response = webTarget
//          //.register(userAuth)
//          .register(adminAuth)
//          .path(CONTACT_RESOURCE_NAME)
//          .request()
//          .get();
//      assertThat(response.getStatus(), is(200));
//      List<Contact> contacts = response.readEntity(new GenericType<List<Contact>>(){});
//      assertThat(contacts, is(not(empty())));
//      assertThat(contacts, hasSize(2));
//  }
//  
//  @Test
//  @Order(2)
//  public void test12_create_contact_with_adminrole() throws JsonMappingException, JsonProcessingException {
//  	Contact contact = new Contact();
//  	
//      Response response = webTarget
//          //.register(userAuth)
//          .register(adminAuth)
//          .path(CONTACT_RESOURCE_NAME)
//          .request()
//          .post(Entity.entity(contact, MediaType.APPLICATION_JSON_TYPE));
//      assertThat(response.getStatus(), is(200));
//      List<Contact> contacts = response.readEntity(new GenericType<List<Contact>>(){});
//      assertThat(contacts, is(not(empty())));
//      assertThat(contacts, hasSize(2));
//  }
    
    
}