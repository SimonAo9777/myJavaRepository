package logic;

import common.ValidationException;
import dal.PersonDAL;
import entity.Person;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.ObjIntConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rong Fu
 */
public class PersonLogic extends GenericLogic<Person, PersonDAL> {

    /**
     * create static final variables with proper name of each column. this way you will never manually type it again,
     * instead always refer to these variables.
     *
     * by using the same name as column id and HTML element names we can make our code simpler. this is not recommended
     * for proper production project.
     */
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String PHONE = "phone";
    public static final String ADDRESS = "address";
    public static final String BIRTH = "birth";
    public static final String ID = "id";

    PersonLogic() {
        super( new PersonDAL() );
    }

    @Override
    public List<Person> getAll() {
        return get( () -> dal().findAll() );
    }

    @Override
    public Person getWithId( int id ) {
        return get( () -> dal().findById( id ) );
    }

    public List<Person> getPersonWithPhone( String phone ) {
        return get( () -> dal().findByPhone( phone ) );
    }

    public List<Person> getPersonWithFirstName( String firstName ) {
        return get( () -> dal().findByFirstName( firstName ) );
    }

    public List<Person> getPersonWithLastName( String lastName ) {
        return get( () -> dal().findByLastName( lastName ) );
    }

    public List<Person> getPersonWithAddress( String address ) {
        return get( () -> dal().findByAddress( address ) );
    }

    public List<Person> getPersonWithBirth( Date birth ) {
        return get( () -> dal().findByBirth( birth ) );
    }
    
    @Override
    public Person createEntity( Map<String, String[]> parameterMap ) {
        //do not create any logic classes in this method.

//        return new PersonBuilder().SetData( parameterMap ).build();
        Objects.requireNonNull( parameterMap, "parameterMap cannot be null" );
        //same as if condition below
//        if (parameterMap == null) {
//            throw new NullPointerException("parameterMap cannot be null");
//        }

        //create a new Entity object
        Person entity = new Person();

        //ID is generated, so if it exists add it to the entity object
        //otherwise it does not matter as mysql will create an if for it.
        //the only time that we will have id is for update behaviour.
        if( parameterMap.containsKey( ID ) ){
            try {
                entity.setId( Integer.parseInt( parameterMap.get( ID )[ 0 ] ) );
            } catch( java.lang.NumberFormatException ex ) {
                throw new ValidationException( ex );
            }
        }

        //before using the values in the map, make sure to do error checking.
        //simple lambda to validate a string, this can also be place in another
        //method to be shared amoung all logic classes.
        ObjIntConsumer< String> validator = ( value, length ) -> {
            if( value == null || value.trim().isEmpty() || value.length() > length ){
                String error = "";
                if( value == null || value.trim().isEmpty() ){
                    error = "value cannot be null or empty: " + value;
                }
                if( value.length() > length ){
                    error = "string length is " + value.length() + " > " + length;
                }
                throw new ValidationException( error );
            }
        };

        String firstName = parameterMap.get (FIRST_NAME) [ 0 ];
        String lastName = parameterMap.get( LAST_NAME )[ 0 ];
        String phone = parameterMap.get( PHONE )[ 0 ];
        String address = parameterMap.get( ADDRESS )[ 0 ];
        Date birth;
        try {
            //valided by the convertStringToDateTime() inherited from parent GenericLogic.java
            birth = convertStringToDateTime(parameterMap.get( BIRTH )[ 0 ]);
        }
        catch (ValidationException e) {
            birth = new Date(); //if parsing failed, use current Date
        }

        //validate the data
        validator.accept( firstName, 50 );
        validator.accept( lastName, 50 );
        validator.accept( phone, 15 );
        validator.accept( address, 100 );

        //set values on entity
        entity.setFirstName( firstName );
        entity.setLastName( lastName );
        entity.setPhone( phone );
        entity.setAddress( address );
        entity.setBirth ( birth );

        return entity;
    }

    /**
     * this method is used to send a list of all names to be used form table column headers. by having all names in one
     * location there is less chance of mistakes.
     *
     * this list must be in the same order as getColumnCodes and extractDataAsList
     *
     * @return list of all column names to be displayed.
     */
    @Override
    public List<String> getColumnNames() {
        return Arrays.asList( "ID", "FirstName", "LastName", "Phone", "Address", "Birth" );
    }

    /**
     * this method returns a list of column names that match the official column names in the db. by having all names in
     * one location there is less chance of mistakes.
     *
     * this list must be in the same order as getColumnNames and extractDataAsList
     *
     * @return list of all column names in DB.
     */
    @Override
    public List<String> getColumnCodes() {
        return Arrays.asList( ID, FIRST_NAME, LAST_NAME, PHONE, ADDRESS, BIRTH );
    }

    /**
     * return the list of values of all columns (variables) in given entity.
     *
     * this list must be in the same order as getColumnNames and getColumnCodes
     *
     * @param e - given Entity to extract data from.
     *
     * @return list of extracted values
     */
    @Override
    public List<?> extractDataAsList( Person e ) {
        //the default format yyyy-MM-dd HH:mm:ss.S looks strange as it includes millisecond, change it
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return Arrays.asList( e.getId(), e.getFirstName(), e.getLastName(), e.getPhone(), e.getAddress(), dateFormat.format(e.getBirth()) );
    }
}

