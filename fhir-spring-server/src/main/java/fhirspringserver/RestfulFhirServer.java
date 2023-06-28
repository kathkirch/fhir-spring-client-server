package fhirspringserver;

/*
 Setting FHIR Context
 The initialize method is automatically called when the servlet is starting up, so it can
 be used to configure the servlet to define resource providers, or set up
 configuration, interceptors, etc.
 */

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.server.RestfulServer;
import resourceprovider.PatientResource;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

@WebServlet("/*")
public class RestfulFhirServer extends RestfulServer{

    @Override
    protected void initialize()throws ServletException{

        //create a context for the appropriate version
       setFhirContext(FhirContext.forR5());

        //Register Resource Providers
        registerProvider(new PatientResource());


    }

}