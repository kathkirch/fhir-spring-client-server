package fhirspringserver;

/*
Setup Servlet Context
Bean Annotation for FHIR
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FhirSpringServerApp {

	public static void main(String[] args) {
		SpringApplication.run(FhirSpringServerApp.class, args);
	}

	@Bean
	public ServletRegistrationBean ServletRegistrationBean(){
		ServletRegistrationBean registration=new ServletRegistrationBean(new RestfulFhirServer(),"/*");
		registration.setName("FhirServlet");
		return registration;
	}
}





