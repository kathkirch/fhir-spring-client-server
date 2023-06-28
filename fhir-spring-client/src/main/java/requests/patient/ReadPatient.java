package requests.patient;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;

public class ReadPatient {

   public static void main(String[] args) {
      // Create a FHIR context
      FhirContext fhirContext = FhirContext.forR4();

      // Create a FHIR client
      IGenericClient client = fhirContext.newRestfulGenericClient("http://hapi.fhir.org/baseR4/");

      // Specify the patient's full name and birthdate
      String patientFamilyName = "Wutz";
      String patientGivenName = "Peppa";
      String patientBirthDate = "2007-11-12";

      // Create a FHIR request and execute it
      Bundle bundle = client.search()
              .forResource(Patient.class)
              .where(Patient.FAMILY.matches().value(patientFamilyName))
              .and(Patient.GIVEN.matches().value(patientGivenName))
              .and(Patient.BIRTHDATE.exactly().day(patientBirthDate))
              .returnBundle(Bundle.class)
              .execute();

      // Extract the patient resource from the bundle
      if (bundle.hasEntry()) {
         Patient patient = (Patient) bundle.getEntry().get(0).getResource();
         // Access patient information
         String patientId = patient.getIdElement().getIdPart();
         String patientName = patient.getNameFirstRep().getNameAsSingleString();
         // Print patient information
         System.out.println("Patient ID: " + patientId);
         System.out.println("Patient Name: " + patientName);
      } else {
         System.out.println("Patient not found.");
      }
   }

}



