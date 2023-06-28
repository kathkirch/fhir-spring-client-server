package testserver;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;

public class TestServerClient {

    /*
     * This class contains hints for the tasks outlined in TestApplication
     */

    public static void main(String[] args) {

        // We're connecting to a DSTU1 compliant server in this example
        FhirContext ctx = FhirContext.forR4();
        String serverBase = "http://hapi.fhir.org/baseR4/";

        IGenericClient client = ctx.newRestfulGenericClient(serverBase);

// Perform a search
        Bundle results = client
                .search()
                .forResource(Patient.class)
                .where(Patient.FAMILY.matches().value("Witch")) // matches genau nach dem pattern
                .returnBundle(Bundle.class)
                .execute();

        System.out.println("Found " + results.getEntry().size() + " patients named 'Piggy'");



    }


}