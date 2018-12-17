package mypkg;
 
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.util.List;
import java.util.ArrayList;

import com.sml.java.utils.DrugEnglishDescription;
import org.hl7.fhir.dstu3.model.Bundle;
import org.hl7.fhir.dstu3.model.Patient;
import org.hl7.fhir.dstu3.model.Bundle.BundleEntryComponent;

import com.fasterxml.jackson.databind.ObjectMapper;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;

public class HelloServlet extends HttpServlet {

@Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
               throws IOException, ServletException {
      // Set the response message's MIME type
      response.setContentType("text/html;charset=UTF-8");
      // Allocate a output writer to write the response message into the network socket
      PrintWriter out = response.getWriter();
 
      // Write the response message, in an HTML page
      try {
         out.println("<!DOCTYPE html>");
         out.println("<html><head>");
         out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
         out.println("<title>Hello, World</title></head>");
         out.println("<body>");
         
//         out.println("<h1>Hello, world!</h1>");  // says Hello
//         out.println("<p>Request URI: " + request.getRequestURI() + "</p>");
//         out.println("<p>Protocol: " + request.getProtocol() + "</p>");
//         out.println("<p>PathInfo: " + request.getPathInfo() + "</p>");
//         out.println("<p>Remote Address: " + request.getRemoteAddr() + "</p>");
         out.println("<p>Test FHIR below!2</p>");
        
         IGenericClient client = null;
         FhirContext ctx = FhirContext.forDstu3();
         String url = "/Patient/" + "17085";
         client = ctx.newRestfulGenericClient("http://hapi.fhir.org/baseDstu3");
         Patient patient2 = client.read()
                 .resource(org.hl7.fhir.dstu3.model.Patient.class)
                 .withUrl(url)
                 .execute();
         
         String patient_name = patient2.getNameFirstRep().getNameAsSingleString();
         
//         ObjectMapper mapper = new ObjectMapper();
// 		String jsonInString = mapper.writeValueAsString(patient2);
         
         out.println("<p>FHIR Patient ID lookup: <strong>" + patient_name + "</strong></p>");
//
         DrugEnglishDescription drug_desc_obj = new DrugEnglishDescription();
         out.println("<p>drug desc2: <strong>" + drug_desc_obj.get("insulin") + "</strong></p>");

         out.println("</body>");
         out.println("</html>");
      } finally {
         out.close();  // Always close the output writer
      }
   }
}

//http://www.ntu.edu.sg/home/ehchua/programming/java/javaservlets.html
