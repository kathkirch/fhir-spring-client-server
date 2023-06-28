package requests.patient;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.r5.model.Patient;

public class TestPatient {

   public static void main(String[] args) {

      // Create a context
      FhirContext ctx = FhirContext.forR5();

      // Create a client
      IGenericClient client = ctx.newRestfulGenericClient("http://localhost:8080/");


      // Read a patient with the given ID
       Patient patient1 = client.read().resource(Patient.class).withId("1").execute();

       //Print the output
       String string = ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(patient1);
       System.out.println(string);


   }
}


