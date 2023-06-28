package requests.patient;



import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.instance.model.api.IIdType;
import org.hl7.fhir.r4.model.DateType;
import org.hl7.fhir.r4.model.Enumerations;
import org.hl7.fhir.r4.model.Patient;

public class CreatePatient {

    public static void main(String[] args) {

        // Create a context
        FhirContext ctx = FhirContext.forR4();

        // Create a client
        IGenericClient client = ctx.newRestfulGenericClient("http://hapi.fhir.org/baseR4/");

        // Create a patient
        Patient newPatient1 = new Patient();
        Patient newPatient2 = new Patient();

//        // Configure the patient1 with  information
        newPatient1
                .addName()
                .setFamily("Wutz")
                .addGiven("Peppa")
                .addGiven("Pipi");
        newPatient1
                .addIdentifier()
                .setSystem("http://acme.org/mrn")
                .setValue("999999");

        newPatient1.
                setGender(Enumerations.AdministrativeGender.FEMALE);
        newPatient1.
                setBirthDateElement(new DateType("2007-11-12"));

        // Configure the patient2 with  information
        newPatient2
                .addName()
                .setFamily("Wutz")
                .addGiven("George")
                .addGiven("Schorsch");
        newPatient2
                .addIdentifier()
                .setSystem("http://acme.org/mrn")
                .setValue("999998");

        newPatient2.
                setGender(Enumerations.AdministrativeGender.MALE);
        newPatient2.
                setBirthDateElement(new DateType("2007-08-12"));


//         Create the resources on the server
        MethodOutcome outcome1 = client.create().resource(newPatient1).execute();
        MethodOutcome outcome2 = client.create().resource(newPatient2).execute();

        // Log the ID that the server assigned
        IIdType id1 = outcome1.getId();
        System.out.println("Created patient1 with generated ID: " + id1);
        IIdType id2 = outcome2.getId();
        System.out.println("Created patient1 with generated ID: " + id2);

    }
}




