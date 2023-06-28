package testserver;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;

public class TestCreatePatient {

    public static void main(String[] args) {

        // We're connecting to a DSTU1 compliant server in this example
        FhirContext ctx = FhirContext.forR4();
        String serverBase = "http://hapi.fhir.org/baseR4/";

        IGenericClient client = ctx.newRestfulGenericClient(serverBase);

        Patient patient = new Patient();
// ..populate the patient object
        patient.addIdentifier().setSystem("urn:system").setValue("12345");
        patient.addName().setFamily("Witch").addGiven("Blair");

// Invoke the server create method (and send pretty-printed JSON
// encoding to the server
// instead of the default which is non-pretty printed XML)
        MethodOutcome outcome = client.create()
                .resource(patient)
                .prettyPrint()
                .encodedJson()
                .execute();



    }
}
