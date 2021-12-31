/**************************************************************************************************
 * File: MyObjectMapperProvider.java
 * Course materials (21F) CST 8277
 * 
 * @author Teddy Yap
 * @author Mike Norman
 * @date 2020 04
 * 
 * Update By Students:
 * @author Simon Ao
 * @author Chik Matthew
 * @author Park Fred
 * @author Sun Ding
 * 
 * @date 11/12/2021
 *
 * Note:  Students do NOT need to change anything in this class.
 */
package bloodbank;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

@Provider
public class MyObjectMapperProvider extends JacksonJsonProvider implements ContextResolver<ObjectMapper> {
   // final ObjectMapper defaultObjectMapper;
   
    static ObjectMapper defaultObjectMapper;
    static {

        defaultObjectMapper = new ObjectMapper()

             .registerModule(new JavaTimeModule())

             .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false)

             .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)

             .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

     }
    
    public MyObjectMapperProvider() {

        super(defaultObjectMapper);

     }

     

     public ObjectMapper getContext(Class<?> type) {

             return defaultObjectMapper;

     }
    
    
    
    
    
    
//    
//    public MyObjectMapperProvider() {
//        defaultObjectMapper = createDefaultMapper();
//    }
//    @Override
//    public ObjectMapper getContext(Class<?> type) {
//            return defaultObjectMapper;
//        }
//    private static ObjectMapper createDefaultMapper() {
//        final ObjectMapper mapper = new ObjectMapper()
//            .registerModule(new JavaTimeModule())
//            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
//            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
//            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//        return mapper;
//    }
}