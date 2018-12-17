package com.sml.java.web;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hl7.fhir.dstu3.model.CodeableConcept;
import org.hl7.fhir.dstu3.model.Coding;
import org.hl7.fhir.dstu3.model.MedicationRequest;
import org.hl7.fhir.dstu3.model.Patient;
import org.hl7.fhir.dstu3.model.SimpleQuantity;
import org.hl7.fhir.dstu3.model.Timing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sml.java.entities.Patients;
import com.sml.java.entities.Team;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.api.IGenericClient;


// Medication Request Class //


/**
 * Servlet implementation class HelloWorldServlet
 */
@WebServlet(
        name = "addPatient2",
        urlPatterns = {"/addPatient2", "/sml2/addPatient2"},
        asyncSupported = false,
        initParams = {
                @WebInitParam(name = "name", value = "admin")
        })

public class AddPatient2 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String BASEURL = "http://hapi.fhir.org/baseDstu3";

    public AddPatient2() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        FhirContext ctx = FhirContext.forDstu3();
        IGenericClient client = ctx.newRestfulGenericClient(BASEURL);

        // Add patient
        String id = addPatient(client, "Jane","Doe");

        // Add medication requests
        String mid1 = addMedicationRequest(client, id,
                "acyclovir topical (zovirax 5% topical ointment)",
                "372729009",
                "= 1 app, Topical, q12hr, 15 qm, 3 Refills).",
                1,
                1,
                "D",
                "topical, spread on prescribed area",
                2,
                "mg");

        String mid2 = addMedicationRequest(client, id,
                "alendronate (fosamax 70 mg oral tablet)",
                "698090000",
                "1 tab(s), PO, qDay, + 13 tab(s) 3 Refil). Substitution Allowed",
                2,
                1,
                "D",
                "Oral",
                70,
                "mg");

        String mid3 = addMedicationRequest(client, id,
                "amlodipine (amlodipine 10 mg oral tablet)",
                "386864001",
                "10 mg 1 tab(s), PO, BID, 90 tab(s), 3 Refiks). Substitution Allowed",
                1,
                1,
                "D",
                "Oral",
                10,
                "mg");

        String mid4 = addMedicationRequest(client, id,
                "aspirin",
                "387458008",
                "PO, qDay, PRN for swelling or weight gain, 0 Refill(s)",
                1,
                1,
                "D",
                "Oral",
                50,
                "mg");

        String mid5 = addMedicationRequest(client, id,
                "lisinopril (lisinopril 50 mg oral tablet)",
                "386873009",
                "50 mg = 1 tabs PO, BID, 90 tab(s), 2 Refill(s)",
                1,
                1,
                "D",
                "Oral",
                50,
                "mg");



        ///////////////// end FHIR code /////////////////

        ///////////////// enter return information into this list below  /////////////////
        // then, wait ~10 seconds and see changes appear on this site: https://cs6440-f18-prj31.apps.hdap.gatech.edu/sml2/addPatient
        String[] ids = new String[]{mid1, mid2, mid3, mid4, mid5};
        Patients patients = new Patients(Arrays.asList(ids));
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(patients);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        //response.getWriter().write(jsonInString);
        response.getWriter().write("Hospital Example Patient Generator - #2\n\n\nPatient ID generated: \n"+ id +
                "\n\nLink to view patient: \nhttp://hapi.fhir.org/baseDstu3/Patient/"+id+
                "\n\n\nMedication id's generated: \n" + jsonInString +
                "\n\nLink to view one of the medications:\nhttp://hapi.fhir.org/baseDstu3/MedicationRequest/"+mid1);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] teammates = new String[]{"Allen", "Faridi", "Jim", "Krit", "Sean", "Victor3"};
        Team team = new Team(Arrays.asList(teammates));
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(team);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonInString);
    }

    /**
     * Add a new patient to the FHIR server with the given first and last name.
     * Return the ID of the newly created patient.
     * @param client
     */
    private String addPatient(IGenericClient client, String firstName, String lastName) {

        // Create patient and set name
        Patient patient = new Patient();
        patient.addName().addGiven(firstName).setFamily(lastName);

        //System.out.println(patient.getName().get(0).getNameAsSingleString());

        // Post to server
        MethodOutcome out = client.create().resource(patient).execute();

        //System.out.println(out.getId().getValue().split("/")[5]);

        // Return ID
        return out.getId().getValue().split("/")[5];
    }

    /**
     * Add medication statement to server with patient ID
     * @param client
     */
    private String addMedicationRequest(IGenericClient client, String patientId, String drugNm, String snomedCd,
                                        String instructions, int freq, double period,
                                        String period_unit, String route,
                                        double dose_value, String dose_unit){

        // Create medication request
        MedicationRequest mr = new MedicationRequest();

        // Attach to patient ID
        mr.getSubject()
                .setReference("Patient/"+patientId);


        // Set medication active and order
        mr.setStatus(MedicationRequest.MedicationRequestStatus.ACTIVE)
                .setIntent(MedicationRequest.MedicationRequestIntent.ORDER);

        // Specify medication
        mr.setMedication(new CodeableConcept()
                .addCoding(new Coding()
                        .setSystem("http://snomed.info/ct")
                        .setCode(snomedCd)
                        .setDisplay(drugNm)));

        // Add dosage and instructions
        // Free text
        mr.getDosageInstructionFirstRep()
                .setText(instructions);
        // Structured periods
        mr.getDosageInstructionFirstRep()
                .getTiming()
                .getRepeat()
                .setFrequency(freq)
                .setPeriod(period)
                .setPeriodUnit(Timing.UnitsOfTime.valueOf(period_unit));
        // Structured dose quantity
        mr.getDosageInstructionFirstRep()
                .setDose(new SimpleQuantity()
                        .setValue(dose_value)
                        .setSystem("http://unitsofmeasure.org")
                        .setUnit(dose_unit));
        // Add route
        mr.getDosageInstructionFirstRep()
                .getRoute()
                .addCoding()
                .setDisplay(route);


        // Add statements to server
        MethodOutcome out = client.create().resource(mr).execute();

        // Return ID
        return out.getId().getValue().split("/")[5];
    }

}
