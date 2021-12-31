package dal;

import entity.Person;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Rong Fu
 */
public class PersonDAL extends GenericDAL<Person> {

    public PersonDAL() {
        super( Person.class );
    }

    @Override
    public List<Person> findAll() {
        //first argument is a name given to a named query defined in appropriate entity
        //second argument is map used for parameter substitution.
        //parameters are names starting with : in named queries, :[name]
        return findResults( "Person.findAll", null );
    }

    @Override
    public Person findById( int id ) {
        Map<String, Object> map = new HashMap<>();
        map.put( "id", id );
        //first argument is a name given to a named query defined in appropriate entity
        //second argument is map used for parameter substitution.
        //parameters are names starting with : in named queries, :[name]
        //in this case the parameter is named "id" and value for it is put in map
        return findResult( "Person.findById", map );
    }

    /**
     * Find by first name, First Name is not unique so return a list
     * @param firstName
     * @return 
     */
    public List<Person> findByFirstName( String firstName ) {
        Map<String, Object> map = new HashMap<>();
        map.put( "firstName", firstName );
        return findResults( "Person.findByFirstName", map );
    }

    /**
     * Find by last name, Last Name is not unique so return a list
     * @param lastName
     * @return 
     */
    public List<Person> findByLastName( String lastName ) {
        Map<String, Object> map = new HashMap<>();
        map.put( "lastName", lastName );
        return findResults( "Person.findByLastName", map );
    }

    /**
     * Find by phone number, phone is not defined as unique so return a list
     * @param phone
     * @return 
     */
    public List<Person> findByPhone( String phone ) {
        Map<String, Object> map = new HashMap<>();
        map.put( "phone", phone );
        return findResults( "Person.findByPhone", map );
    }

    /**
     * Find by address, Address may be shared by a family so return a list
     * @param address
     * @return 
     */
    public List<Person> findByAddress( String address ) {
        Map<String, Object> map = new HashMap<>();
        map.put( "address", address );
        return findResults( "Person.findByAddress", map );
    }

    /**
     * Find by birth date, Birth is not unique so return a list
     * @param birth
     * @return 
     */
    public List<Person> findByBirth( Date birth ) {
        Map<String, Object> map = new HashMap<>();
        map.put( "birth", birth );
        return findResults( "Person.findByBirth", map );
    }

}
