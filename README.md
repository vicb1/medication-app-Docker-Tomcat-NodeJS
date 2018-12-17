# medication-app-on-Docker-Tomcat-NodeJS-servers

"Smart Medication List" project for a class.  This website application is a template that allows hospital patients to view their medication information.  Care was taken in ensuring this medication information is complete and easy to understand.  Patients can see their dosage schedules and amount for each drug, as well as an "english" (as opposed to "doctor" language) description of each drug.  Additionally, patients are able to view their medication in the Unified Medication Schedule format to make it easier to understand when to take their drugs.

For this application, the medication information is generated and viewed on [this public FHIR server](http://hapi.fhir.org/baseDstu3/).  In the real world, U.S. hospitals are required by the HL7 directive to use FHIR as their database.  This application would thus be easy to integrate into a hospital's system.

This app uses a frontend  

To view hosted version:
-
1. Go to https://cs6440-f18-prj31.apps.hdap.gatech.edu/

To view local Docker version:
-
1. Install Docker here: https://docs.docker.com/install/
1. Download a .ZIP file of the the project from here: https://github.gatech.edu/gt-cs6440-hit-fall2018/Smart-Medication-List
1. Unzip the file
1. Open your computer's shell program
1. Navigate to the "..\Smart-Medication-List\Smart.Medication.List.Web" directory
1. run these commands in your shell:
   1. docker image build --build-arg \_\_API_URL\_\_=http://localhost:8081 -t sml_web .
   1. docker container run --rm -p 8080:4000 -it sml_web
1. Open another shell
1. Navigate to the "..\Smart-Medication-List\net.smartmedicationlist.web" directory
1. run these commands in your shell:
   1. docker image build -t sml .
   1. docker container run --rm -p 8081:8080 -it sml
1. To use the frontend interface go here:
   1. http://localhost:8080/
1. To access the backend features of adding new patients, go here:
   1. http://localhost:8081/sml2/addPatient
   1. http://localhost:8081/sml2/addPatient2


http://localhost:8081/node
