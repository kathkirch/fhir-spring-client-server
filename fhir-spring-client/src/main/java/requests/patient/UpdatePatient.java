package requests.patient;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.instance.model.api.IIdType;
import org.hl7.fhir.r4.model.*;

import java.util.List;

public class UpdatePatient {

    public static void main(String[] args) {
        // Create a FHIR context
        FhirContext fhirContext = FhirContext.forR4();

        // Create a FHIR client pointing to the FHIR server
        IGenericClient client = fhirContext.newRestfulGenericClient("http://hapi.fhir.org/baseR4/");

        // Specify the patient ID for the update
        String patientId = "10880911"; // ID von Peppa Wutz

        // Create a ContactPoint for the telephone number for contactperson
        ContactPoint contactPoint = new ContactPoint();
        contactPoint.setSystem(ContactPoint.ContactPointSystem.PHONE);
        contactPoint.setValue("0664-1234567"); // Replace with the telephone number

        // Create a ContactPoint for the telephone number for patient email
        ContactPoint contactPoint_mail = new ContactPoint();
        contactPoint_mail.setSystem(ContactPoint.ContactPointSystem.EMAIL);
        contactPoint_mail.setValue("peppa_wutz@pigmail.com"); // Replace with the telephone number

        Coding relationshipCoding = new Coding();
        relationshipCoding.setSystem("https://termgit.elga.gv.at/CodeSystem/hl7-role-code"); // Use the FHIR system for relationship codes
        relationshipCoding.setCode("MTH"); // Use the FHIR code for mother relationship
        relationshipCoding.setDisplay("Mother"); // Display name for mother relationship

        CodeableConcept relationshipConcept = new CodeableConcept();
        relationshipConcept.addCoding(relationshipCoding);

        // Create an Address for the contact person's address
        Address address = new Address();
        address.setLine(List.of(new StringType("Stallhausweg"))); // Replace with the address lines
        address.setCity("Beverly Hills"); // Replace with the city
        address.setPostalCode("90210"); // Replace with the postal code
        address.setCountry("California"); // Replace with the country

        // Create a HumanName for the contact person's name
        HumanName contactPersonName = new HumanName();
        contactPersonName.setText("Mama Wutz"); // Replace with the name of the mother

        // Create a ContactDetail for the contact person
        ContactDetail contactPerson = new ContactDetail();
        contactPerson.setTelecom(List.of(contactPoint));
        contactPerson.setName(String.valueOf(contactPersonName));

        // Retrieve the existing patient resource
        Patient patient = client.read()
                .resource(Patient.class)
                .withId(patientId)
                .execute();

//         Add the contact person to the patient resource
        patient.addContact()
                .setRelationship(List.of(relationshipConcept))
                .setName(contactPersonName)
                .setTelecom(contactPerson.getTelecom())
                .setAddress(address);

        patient.addAddress(address);
        patient.addTelecom(contactPoint_mail);

//        Update the patient resource on the FHIR server
        IIdType updatedPatientId = client.update()
                .resource(patient)
                .withId(patientId)
                .execute()
                .getId();

        System.out.println("Patient updated successfully. Updated patient ID: " + updatedPatientId.getValue());

    }
}




