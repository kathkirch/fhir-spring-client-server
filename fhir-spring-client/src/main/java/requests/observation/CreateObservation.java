package requests.observation;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.r4.model.*;

public class CreateObservation {

        public static void main(String[] args) {
            // Create a FHIR context
            FhirContext fhirContext = FhirContext.forR4();

            // Create a FHIR client pointing to the public test server
            IGenericClient client = fhirContext.newRestfulGenericClient("http://hapi.fhir.org/baseR4");

            // Create an Observation resource
            Observation observation = new Observation();

            // Set the status of the observation
            observation.setStatus(Observation.ObservationStatus.FINAL);

            // Set the code for the observation (e.g., blood pressure)
            CodeableConcept code = new CodeableConcept();
            code.addCoding()
                    .setSystem("http://loinc.org")
                    .setCode("85354-9")
                    .setDisplay("Blood pressure systolic & diastolic");
            observation.setCode(code);

            // Set the value of the observation
            Quantity value = new Quantity();
            value.setValue(120.0).setUnit("mmHg");
            observation.setValue(value);

            // Set the subject of the observation (patient peppa wutz = id 10880911)
            Reference subject = new Reference();
            subject.setReference("Patient/10880911"); // Replace with the actual patient ID
            observation.setSubject(subject);

            // Set the effective date/time of the observation
            observation.setEffective(new DateTimeType("2023-06-27T12:00:00Z"));

            // Send the create request to the server
            MethodOutcome outcome = client.create()
                    .resource(observation)
                    .execute();

            // Check the server response
            if (outcome != null && outcome.getCreated()) {
                System.out.println("Observation created successfully!");
                System.out.println("Resource ID: " + outcome.getId().getValue());
            } else {
                System.out.println("Failed to create the observation.");
            }
        }
}


