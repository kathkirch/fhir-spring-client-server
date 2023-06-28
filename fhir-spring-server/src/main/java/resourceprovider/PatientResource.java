package resourceprovider;

import ca.uhn.fhir.rest.annotation.IdParam;
import ca.uhn.fhir.rest.annotation.Read;
import ca.uhn.fhir.rest.annotation.RequiredParam;
import ca.uhn.fhir.rest.annotation.Search;
import ca.uhn.fhir.rest.param.StringParam;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;
import org.hl7.fhir.dstu2.model.IdType;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r5.model.Patient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PatientResource implements IResourceProvider {

    // Default resource constructor
    public PatientResource() {
    }

    // A Map to hold all the patients.
    private final Map<String, Patient> patientMap = new HashMap<String, Patient>();


    // Implementation of abstract method getResourceProvider() from IResourceProvider
    @Override
    public Class<? extends IBaseResource> getResourceType() {
        var patientClass = Patient.class;
        return Patient.class;
    }

    /**
     * "@Search" annotation indicates that this method supports the search operation.
     * Similarly we can have more Search annotations defined they can each take different parameters
     * @return
     *    This method returns a list of Patients. This list may contain multiple matching resources,
     *    or it may also be empty.nThis annotation takes a "name" parameter which specifies the parameter's
     *    name (as it will appear in the search URL). FHIR defines standardized parameter names for
     *    each resource, and these are available as constants on the individual HAPI resource classes.
     */
    @Search
    public List<Patient> search(@RequiredParam(name = Patient.SP_FAMILY) StringParam theParam) {
        List<Patient> patients = new ArrayList<Patient>();
        patients = this.searchByFamilyName(theParam.getValue());
        return patients;
    }

    /**
     * Simple implementation of the "read" method
     *
     * The "@Read" annotation indicates that this method supports the
     * read operation. Read operations should return a single resource instance.
     * This method will support a query like this http://localhost:8080/Patient/1
     */
    @Read()
    public Patient read(@IdParam IdType theId) {
        loadDummyPatients();
        Patient retVal = patientMap.get(theId.getIdPart());

        if (retVal == null) {
            throw new ResourceNotFoundException(theId);
        }
        return retVal;

    }


    // Encode the output, loop through the patients looking for matches
    private List<Patient> searchByFamilyName(String familyName){

        List<Patient> retPatients;
        loadDummyPatients();

        retPatients = patientMap.values()
                .stream()
                .filter(next -> familyName.toLowerCase().equals(next.getNameFirstRep().getFamily().toLowerCase()))
                .collect(Collectors.toList());
        return retPatients;

    }


    private void loadDummyPatients() {

        Patient patient1 = new Patient();
        Patient patient2 = new Patient();



        patient1.setId("1");
        patient1.addIdentifier().setSystem("http://optum.com/MRNs").setValue("007");
        patient1.addName().setFamily("Chakravarty").addGiven("Mithun").addGiven("A");
        patient1.addAddress().addLine("Address Line 1");
        patient1.addAddress().setCity("Mumbai");
        patient1.addAddress().setCountry("India");
        patient1.addTelecom().setValue("111-111-1111");
        this.patientMap.put("1", patient1);


        patient2.setId("2");
        patient2.addIdentifier().setSystem("http://optum.com/MRNs").setValue("007");
        patient2.addName().setFamily("Bond").addGiven("James").addGiven("J");
        patient2.addAddress().addLine("House Line ");
        patient2.addAddress().setCity("Your City");
        patient2.addAddress().setCountry("USA");
        this.patientMap.put("2", patient2);


    }


}