package demo.reactAdmin.crud.entities.deserializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import demo.reactAdmin.crud.entities.Customer;


public class CustomCustomerSerializer extends StdSerializer<Customer> {

    public CustomCustomerSerializer() { 
        this(null); 
    } 

    public CustomCustomerSerializer(Class<Customer> t) {
        super(t); 
    }

    @Override
    public void serialize(
      Customer value, JsonGenerator gen, SerializerProvider arg2) 
      throws IOException, JsonProcessingException {gen.writeString(value.id.toString());
    }

}