package neft.rest;

import neft.rest.customProccesors.TransformProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.dataformat.soap.SoapJaxbDataFormat;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.tempuri.*;


@Component
public class RestDslRoutes extends RouteBuilder {

  @Value("${soap.endpoint}")
  private String soapEndpoint;

  @Autowired
  private TransformProcessor tranformer;

  public void configure() throws Exception {

    final SoapJaxbDataFormat soap = new SoapJaxbDataFormat("org.tempuri");


    /*
    Здесь конфигурируем REST service
     */
    restConfiguration()
      /*
      Using servlet
      use Servlet for REST API
      Spring - > default servlet container Tomcat
       */
      .component("servlet")
      /*
       * Marshall/unmarshall between Java object automatically
       */
      .bindingMode(RestBindingMode.auto)
      .dataFormatProperty("prettyPrint", "true")
      .clientRequestValidation(true)
      // turn on openApi api-doc
      .apiContextPath("/api-doc")
      .apiProperty("api.title", "Calculator API")
      .apiProperty("api.version", "1.0.0");
    /*
     * REST Config
     */
    rest()
      .path("/calculator") // This makes the API available at http://host:port/$CONTEXT_ROOT/api
      //Consumes/Produces ->  application/json
      .consumes("application/json")
      .produces("application/json")


      //   POST - Adding two numbers {a} + {b}
      //Route request to validation and then send to output !!!
      .post("/add")
      .description("POST endpoint for adding 2 numbers")
      .id("add")
      .type(Add.class)
      .outType(AddResponse.class)
      .to("direct:transformIntoSoap")


      //  POST - using to subtract two integers {a} - {b}
      //Route request to validation and then send to output !!!
      .post("/subtract")
        .description("POST endpoint for subtracting 2 numbers")
        .id("subtract")
        .type(Subtract.class)
        .outType(SubtractResponse.class)
        .to("direct:transformIntoSoap")

    //  POST - multiply integers {a} * {b}
      .post("/multiply")
      .description("POST endpoint for multiplying 2 numbers")
      .id("multiply")
      .type(Multiply.class)
      .outType(MultiplyResponse.class)
      .to("direct:transformIntoSoap")

    //  POST - divide integers {a} / {b}
      .post("/divide")
      .description("POST divide for multiplying 2 numbers")
      .id("divide")
      .type(Divide.class)
      .outType(DivideResponse.class)
      .to("direct:transformIntoSoap");

    /*
     * Define a custom exception handler
     @begin
     */
    onException(Exception.class)
      .handled(true)
      .bean(PrepareErrorResponse.class)
      .log("Error response processed");
    /*
     @end
     */




      /*
      @begin validation
       Validation Pipe can be used for all parts of the program
       Since we have 2 numbers for all operations
       */
//      from("direct:validate")
//        .process((Exchange exchange)->{
//          System.out.println(exchange.getMessage().getBody().getClass() + " lorem ipsum ");
//          Divide requestBody = exchange.getMessage().getBody(Divide.class);
//          if(requestBody == null ||
//            String.valueOf(requestBody.getIntA()).isEmpty() ||
//            String.valueOf(requestBody.getIntB()).isEmpty()
//          ){
//            exchange.getIn().setBody("{ error: Bad Request}");
//            exchange.getIn().setHeader(Exchange.CONTENT_TYPE, "application/json");
//            exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, 400);
//          }
//        })
//        .choice()
//        .when(exchange -> {
//          Object header = exchange.getIn().getHeader(Exchange.HTTP_RESPONSE_CODE);
//          return header != null && header.equals(400);
//        })
//        .stop()
//        .otherwise()
//        .to("direct:transformIntoSoap")
//        .endChoice();
      /*
      @end validation
       */
      /*
       * @begin transformation into SOAP XML
       */
      from("direct:transformIntoSoap")
        .setHeader("Content-Type", constant("text/xml;charset=utf-8"))
        .process(tranformer)
        .recipientList(simple(soapEndpoint))
        .unmarshal(soap)
        .marshal(new JacksonDataFormat())
        .unmarshal().json()
        .endRest();



      /*
       * @end soap transformation
       */


  }

}
