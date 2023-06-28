package requests.patient;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;


public class SearchPatient {

   public static void main(String[] args) {

      // Create a context
      FhirContext ctx = FhirContext.forR4();

      // Create a client with connection to a server
      IGenericClient client = ctx.newRestfulGenericClient("http://hapi.fhir.org/baseR4/");


      Bundle results = (Bundle) client
         .search()
         .forResource(Patient.class)
         .returnBundle(Bundle.class)
         .where(Patient.FAMILY.matches().value("Wutz"))
         .returnBundle(Bundle.class)
         .execute();

      //Print the output
      System.out.println("First page: ");
      String string = ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(results);
      System.out.println(string);

   }
}



