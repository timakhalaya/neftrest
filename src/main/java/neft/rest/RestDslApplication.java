package neft.rest;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;



@SpringBootApplication
public class RestDslApplication {

  public static void main(String[] args) {
     ConfigurableApplicationContext dm = SpringApplication.run(RestDslApplication.class, args);

  }
}
