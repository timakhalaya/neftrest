package neft.rest;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.camel.Message;
import org.apache.camel.util.json.JsonObject;

import javax.ws.rs.core.MediaType;

public class PrepareErrorResponse {

  @Handler
  public void prepareErrorResponse(Exchange exchange) {
    //Initially set as a Bad Request
    JsonObject errorMessage = new JsonObject();
    //define message here as well
    Message msg = exchange.getOut();
    msg.setHeader(Exchange.CONTENT_TYPE, MediaType.APPLICATION_JSON);
    msg.setHeader(Exchange.HTTP_RESPONSE_CODE, 400);

    Throwable cause = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Throwable.class);
    errorMessage.put("Error","Bad Request");
    System.out.println(" Debugging exception in here "+cause);
    //process only if exception is there
    if(cause instanceof Exception){
      // In case if format of request is Invalid
      if(cause instanceof InvalidFormatException){
        // We can log if needed
        System.out.println(" Invalid Format Exception " + cause.toString());
      }
      // If type String Passed instead of Int
      if(cause instanceof UnrecognizedPropertyException ||
        cause instanceof JsonParseException){
        // We can log if needed
        msg.setHeader(Exchange.HTTP_RESPONSE_CODE, 422);
        System.out.println("UnrecognizedPropertyException || JsonParseException " + cause.toString());
      }

      msg.setBody(errorMessage);
    }
    // we need to do the fault=false below in order to prevent a
    // HTTP 500 error code from being returned
  }
}
