package com.sml.java.web;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.sml.java.entities.PatientInfoWithUmsList;
import com.sml.java.entities.Team;
import com.sml.java.entities.UMS;
import com.sml.java.utils.DrugEnglishDescription;
import org.hl7.fhir.dstu3.model.Bundle;
import org.hl7.fhir.dstu3.model.Patient;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

@WebServlet(
		urlPatterns = {"/node", "/sml2/node"}
)

public class NodeEndpoint extends HttpServlet {
	private static final String BASEURL = "http://hapi.fhir.org/baseDstu3";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NodeEndpoint() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		setAccessControlHeaders(response); // https://www.logicbig.com/tutorials/java-ee-tutorial/java-servlet/servlet-cors.html, allowing CORS for development purposes only. Remove after development is complete.

		String patientId = request.getParameter("patientId");
		if (patientId == null) { // for testing
			patientId = "629937";
		}

		FhirContext ctx = FhirContext.forDstu3();
		IGenericClient client = ctx.newRestfulGenericClient(BASEURL);

		PatientInfoWithUmsList patientInfoWithUmsList = new PatientInfoWithUmsList();
		try {
			patientInfoWithUmsList.patientFullName = getPatientFullName(patientId, client);
		} catch (Exception e) {
			response.sendError(404);
			return;
		}
		patientInfoWithUmsList.umsList = getUmsList(patientId, ctx, client);

		String patientInfoWithUmsListString = new Gson().toJson(patientInfoWithUmsList);

		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		out.print(patientInfoWithUmsListString);
		out.flush();
	}

	private String getPatientFullName(String id, IGenericClient client) {
		Patient patient = client.read()
				.resource(Patient.class)
				.withUrl("/Patient/" + id)
				.execute();
		return patient.getNameFirstRep().getNameAsSingleString();
	}

	private List<UMS> getUmsList(String id, FhirContext ctx, IGenericClient client) {
		String[] patient_meds = new String[100];
		IParser jsonParser = ctx.newJsonParser();
		jsonParser.setPrettyPrint(true);
		Bundle bundle = client.search()
				.byUrl(BASEURL + "/MedicationRequest?patient=" + id)
				.returnBundle(Bundle.class)
				.execute();

		JSONObject meds_json = new JSONObject(jsonParser.encodeResourceToString(bundle));
		int num_meds = meds_json.getInt("total");
		for (int i = 0; i < num_meds; i++) {
			patient_meds[i] = meds_json.getJSONArray("entry").getJSONObject(i).getJSONObject("resource").getString("id");
		}

		List<UMS> umsList = new ArrayList<>();
		for (int i = 0; i < num_meds; i++) {
			try {
				JSONObject resourceJsonObject = meds_json.getJSONArray("entry").getJSONObject(i).getJSONObject("resource");
				String drugFullName = resourceJsonObject.getJSONObject("medicationCodeableConcept").getJSONArray("coding").getJSONObject(0).getString("display");
				String drugShortName = drugFullName.split("\\s+")[0];
				String drugDescription = "";
				try {
					drugDescription = new DrugEnglishDescription().get(drugShortName);
				} catch (IOException e) {
					e.printStackTrace();
				}
				JSONObject dosageInstructionJsonObject = resourceJsonObject.getJSONArray("dosageInstruction").getJSONObject(0);
				String drugText = dosageInstructionJsonObject.getString("text");
				String route = "";
				try {
					route = dosageInstructionJsonObject.getJSONObject("route").getJSONArray("coding").getJSONObject(0).getString("display");
				} catch (JSONException e) {
					e.printStackTrace();
				}
				JSONObject doseQuantityJsonObject = dosageInstructionJsonObject.getJSONObject("doseQuantity");
				String dose = new StringJoiner(" ").add(Double.toString(doseQuantityJsonObject.getDouble("value"))).add(doseQuantityJsonObject.getString("unit")).toString();
				umsList.add(new UMS(i, drugFullName, drugDescription, route, dose,  new StringJoiner(", ").add(drugShortName).add(drugText).toString()));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return umsList;
	}

	//for Preflight
	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp) { // allowing CORS for development purposes only. Remove after development is complete.
		setAccessControlHeaders(resp);
		resp.setStatus(HttpServletResponse.SC_OK);
	}

	private void setAccessControlHeaders(HttpServletResponse resp) {
		resp.setHeader("Access-Control-Allow-Origin", "*"); // TODO: For a Production application, this would be more restrictive and would be located elsewhere
		resp.setHeader("Access-Control-Allow-Methods", "GET");
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


}