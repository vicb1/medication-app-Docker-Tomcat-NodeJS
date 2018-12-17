package mypkg;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sml.java.entities.UMS;
import com.sml.java.utils.DrugEnglishDescription;
import org.hl7.fhir.dstu3.model.*;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.hl7.fhir.dstu3.model.Bundle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.rest.api.MethodOutcome;
import org.hl7.fhir.dstu3.model.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sml.java.entities.Team;
import com.sml.java.entities.UMS;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;

import com.sml.java.utils.DrugEnglishDescription;
import org.json.JSONObject;


public class VictorTest {
    private static final String BASEURL = "http://hapi.fhir.org/baseDstu3";

    public static void main(String[] args)  throws IOException {
        String id = "579081";
        String[] patient_meds = new String[100];
        // uncommenting this allows for generation of a patient and medications:
//        patient_meds = createPatientAndMeds();

        List<List<String>> output_json2 = new ArrayList<>();


        FhirContext ctx = FhirContext.forDstu3();
        IGenericClient client = ctx.newRestfulGenericClient(BASEURL);
        String url = "/Patient/" + id;
        Patient patient = client.read()
                .resource(org.hl7.fhir.dstu3.model.Patient.class)
                .withUrl(url)
                .execute();
        String patient_name = patient.getNameFirstRep().getNameAsSingleString();

        IParser jsonParser = ctx.newJsonParser();
        jsonParser.setPrettyPrint(true);
        output_json2.add(Arrays.asList(patient_name));

        // get med id's
        url = "/MedicationRequest?patient=" + id;
        Bundle bundle = client.search()
                .byUrl(BASEURL + url)
                .returnBundle(org.hl7.fhir.dstu3.model.Bundle.class)
                .execute();
        JSONObject meds_json = new JSONObject(jsonParser.encodeResourceToString(bundle));
        int num_meds = meds_json.getInt("total");
        for (int i = 0; i < num_meds; i++) {
            patient_meds[i] = meds_json.getJSONArray("entry").getJSONObject(i).getJSONObject("resource").getString("id");
        }

        //populate medication details
        DrugEnglishDescription drug_desc_obj = new DrugEnglishDescription();
        String drug_name;
        String drug_text;
        String umsJson;

        for (int i = 0; i < num_meds; i++) {
            List<String> output_json_inner = new ArrayList<String>();
            url = "/MedicationRequest/" + patient_meds[i];
            MedicationRequest medicationRequest =
                    client.read()
                            .resource(org.hl7.fhir.dstu3.model.MedicationRequest.class)
                            .withUrl(BASEURL + url)
                            .execute();

            output_json_inner.add(jsonParser.encodeResourceToString(medicationRequest));

            JSONObject obj = new JSONObject(jsonParser.encodeResourceToString(medicationRequest));
            drug_name = obj.getJSONObject("medicationCodeableConcept").getJSONArray("coding").getJSONObject(0).getString("display");
            if(drug_name.contains(" ")){
                drug_name = drug_name.substring(0, drug_name.indexOf(" "));
            }
            output_json_inner.add(drug_name);
            output_json_inner.add(drug_desc_obj.get(drug_name));

            drug_text = meds_json.getJSONArray("entry").getJSONObject(i).getJSONObject("resource").getJSONArray("dosageInstruction").getJSONObject(0).getString("text");
            UMS ums = new UMS(drug_text);
            try {
                umsJson = new ObjectMapper().writeValueAsString(ums);
                output_json_inner.add(umsJson);
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (JsonGenerationException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            output_json2.add(output_json_inner);
        }

//        System.out.println(output_json2.toString());

//        DrugEnglishDescription drug_desc_obj = new DrugEnglishDescription();
//
//        System.out.println(drug_desc_obj.get("insulin"));


//        URL url = new URL("http://thesite.space/drugNamesWithDescription.csv");
//        URLConnection connection = url.openConnection();
//
//        InputStreamReader input = new InputStreamReader(connection.getInputStream());
//        BufferedReader buffer = null;
//        String line = "";
//        String csvSplitBy = ",";
//
//        try {
//
//            buffer = new BufferedReader(input);
//
//            String drug = "INSULIN";
//            String ret = "Description not found";
//
//            while ((line = buffer.readLine()) != null) {
//                String[] entries = line.split(csvSplitBy);
////                System.out.println(line);
//                if (drug.equals(entries[0].trim().toUpperCase())){
//                    ret = entries[1];
//                }
////                System.out.println("room [capacity =" + entries[0] + " , price=" + entries[1]);
//            }
//
//            System.out.println(ret);
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (buffer != null) {
//                try {
//                    buffer.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }

    private static String[] createPatientAndMeds() {
        FhirContext ctx = FhirContext.forDstu3();
        IGenericClient client = ctx.newRestfulGenericClient(BASEURL);

        // Add patient
        String id = addPatient(client, "John", "Smith");

        // Add medication requests
        String mid1 = addMedicationRequest(client, id,
                "amlodipine 5 mg oral tablet",
                "386864001",
                "5 mg = 1 tab(s), PO, qDay, # 90 tab(s), 3 Refill(s)",
                1,
                1,
                "D",
                "Oral",
                5,
                "mg");

        String mid2 = addMedicationRequest(client, id,
                "apixaban 2.5 mg oral tablet",
                "698090000",
                "2.5 mg = 1 tab(s), PO, q12hr, # 180 tab(s), 1 Refill(s)",
                2,
                1,
                "D",
                "Oral",
                2.5,
                "mg");

        String mid3 = addMedicationRequest(client, id,
                "atorvastatin (atorvastatin 10 mg oral tablet)",
                "373444002",
                "10 mg = 1 tab(s), PO, qDay, # 90 tab(s), 1 Refill(s)",
                1,
                1,
                "D",
                "Oral",
                10,
                "mg");

        String mid4 = addMedicationRequest(client, id,
                "bumetanide (Bumex)",
                "387498005",
                "PO, qDay, PRN for swelling or weight gain, 0 Refill(s)",
                1,
                1,
                "D",
                "",
                0,
                "");

        String mid5 = addMedicationRequest(client, id,
                "busulfan (busulfan 2 mg oral tablet)",
                "387138002",
                "2 mg = 1 tab(s), PO, qDay, # 90 tab(s), X 90 day(s), 3 Refill(s), Stop 6/6/19",
                1,
                1,
                "D",
                "Oral",
                2,
                "mg");

        String mid6 = addMedicationRequest(client, id,
                "carvedilol (carvedilol 3.125 mg oral tablet)",
                "386870007",
                "3.125 mg = 1 tab(s), PO, BID, # 180 tab(s), 3 Refill(s)",
                2,
                1,
                "D",
                "Oral",
                3.125,
                "mg");

        String mid7 = addMedicationRequest(client, id,
                "furosemide (furosemide 40 mg oral tablet)",
                "387475002",
                "40 mg = 1 tab(s), PO, BID, # 30 tab(s), 11 Refill(s)",
                2,
                1,
                "D",
                "Oral",
                40,
                "mg");

        String mid8 = addMedicationRequest(client, id,
                "ranitidine (zantac 300 oral tablet)",
                "372755005",
                "300 mg = 1 tab(s), PO, qDay, # 90 tab(s), with evening meal, 1 Refill(s)",
                1,
                1,
                "D",
                "Oral",
                300,
                "mg");

        String mid9 = addMedicationRequest(client, id,
                "tramadol (tramadol 50 mg oral tablet)",
                "386858008",
                "50 mg = 1 tab(s), PO, q12hr, PRN for pain, # 60 tab(s), 5 Refill(s)",
                2,
                1,
                "D",
                "Oral",
                50,
                "mg");


        ///////////////// end FHIR code /////////////////

        ///////////////// enter return information into this list below  /////////////////

        String[] ids = new String[]{id, mid1, mid2, mid3, mid4, mid5, mid6, mid7, mid8, mid9};
//        Patients patients = new Patients(Arrays.asList(ids));
        return ids;
    }

    private static String addMedicationRequest(IGenericClient client, String patientId, String drugNm, String snomedCd,
                                               String instructions, int freq, double period,
                                               String period_unit, String route,
                                               double dose_value, String dose_unit) {

        // Create medication request
        MedicationRequest mr = new MedicationRequest();

        // Attach to patient ID
        mr.getSubject()
                .setReference("Patient/" + patientId);


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

    private static String addPatient(IGenericClient client, String firstName, String lastName) {

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
}
