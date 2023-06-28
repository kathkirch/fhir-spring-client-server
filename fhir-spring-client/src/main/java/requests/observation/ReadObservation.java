package requests.observation;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Patient;

import java.util.List;

public class ReadObservation {

    public static void main(String[] args) {
        // Create a FHIR context
        FhirContext fhirContext = FhirContext.forR4();

        // Create a FHIR client pointing to the test server
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

        if (bundle != null && bundle.hasEntry()) {
            // Retrieve the first Patient resource from the search results
            Patient patient = (Patient) bundle.getEntry().get(0).getResource();

            // Get the patient's reference ID
            String patientId = patient.getIdElement().getIdPart();

            // Build the search query URL to retrieve the Observation resources for the patient
            String observationQueryUrl = "Observation?subject=Patient/" + patientId;

            // Send the search request to the server to retrieve the Observation resources
            Bundle observationResults = client.search()
                    .byUrl(observationQueryUrl)
                    .returnBundle(Bundle.class)
                    .execute();

            // Check the server response
            if (observationResults != null && observationResults.hasEntry()) {
                System.out.println("Observations retrieved successfully for patient: " + patientFamilyName + " " +
                        patientGivenName);

                // Iterate through the Observation resources
                List<Bundle.BundleEntryComponent> observationEntries = observationResults.getEntry();
                for (Bundle.BundleEntryComponent entry : observationEntries) {
                    Observation observation = (Observation) entry.getResource();

                    System.out.println("Observation ID: " + observation.getIdElement().getValue());
                    System.out.println("Status: " + observation.getStatus());
                    System.out.println("Code: " + observation.getCode().getCodingFirstRep().getDisplay());
                    System.out.println("Value: " + observation.getValueQuantity().getValue() + observation.getValueQuantity().getUnit());
                    System.out.println("System: " + observation.getCode().getCodingFirstRep().getSystem());
                    System.out.println("-----------------------");
                }
            } else {
                System.out.println("No Observations found for patient: " + patientFamilyName + " " +
                        patientGivenName);
            }
        } else {
            System.out.println("No matching patient found for name: " + patientFamilyName + " " +
                    patientGivenName + " and birth date: " + patientBirthDate);
        }
    }
}




