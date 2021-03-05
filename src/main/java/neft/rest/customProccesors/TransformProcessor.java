package neft.rest.customProccesors;


import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.soap.*;
import java.io.ByteArrayOutputStream;

@Component
public class TransformProcessor implements Processor {
  @Override
  public void process(Exchange exchange) throws Exception {
    HttpServletRequest request = exchange.getIn().getBody(HttpServletRequest.class);
    String requestPath = request.getPathInfo();

    /*
     If set SoapAction on base Path that would be better , can be refactored
     @begin
     */
    if (requestPath.equals("/calculator/divide")){
      exchange.getIn().setHeader("SOAPAction","http://tempuri.org/Divide");
    }
    if(requestPath.equals("/calculator/add")){
      exchange.getIn().setHeader("SOAPAction","http://tempuri.org/Add");
    }
    if(requestPath.equals("/calculator/multiply")){
      exchange.getIn().setHeader("SOAPAction","http://tempuri.org/Multiply");
    }
    if(requestPath.equals("/calculator/subtract")){
      exchange.getIn().setHeader("SOAPAction","http://tempuri.org/Subtract");
    }
    /*
     @end
     */

    /*
     * Soap transformation logic
     */
    Object addObj = exchange.getMessage().getBody(exchange.getMessage().getBody().getClass());
    MessageFactory messageFactory = MessageFactory.newInstance();
    SOAPMessage soapMessage = messageFactory.createMessage();
    SOAPPart soapPart = soapMessage.getSOAPPart();
    SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
    SOAPBody soapBody = soapEnvelope.getBody();
    JAXBContext jaxbContext = JAXBContext.newInstance(exchange.getMessage().getBody().getClass());
    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

    //Marshalling logic Below
    // output pretty printed
    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
    jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
    jaxbMarshaller.marshal(addObj, soapBody);
    soapMessage.saveChanges();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    soapMessage.writeTo(out);
    exchange.getIn().setBody(new String(out.toByteArray()));

  }
}
