# medication-app-on-Docker-Tomcat-NodeJS-servers

"Smart Medication List" project for a class.  This website application is a template that allows hospital patients to view their medication information.  Care was taken in ensuring this medication information is complete and easy to understand.  Patients can see their dosage schedules and dosage amounts for each drug, as well as an "english" (as opposed to "doctor" language) description of each drug.  Additionally, patients are able to view their medication in the Unified Medication Schedule format to make it easier to understand when to take their drugs.

For this application, the medication information is generated and pulled from [this public FHIR database](http://hapi.fhir.org/baseDstu3/).  In the real world, U.S. hospitals are required by the HL7 directive to use the FHIR standard for their patient database.  This application would thus be easy to integrate into a hospital's system.

This app uses an Apache Tomcat server as the backend, to take advantage of the most popular FHIR API which is for Java, namely [HAPI FHIR](http://hapifhir.io/).  A separate server was implemented as the frontend, namely a NodeJS server, to take advantage of the easy-to-use frontend web framework it provides.


To view local Docker version:
-
1. Install Docker here: https://docs.docker.com/install/
1. Download a .ZIP file of the the project from this repository
1. Unzip the file
1. Open your computer's shell program
1. Navigate to the `...\Smart-Medication-List\Smart.Medication.List.Web` directory
1. run these commands in your shell:
   1. `docker image build --build-arg \_\_API_URL\_\_=http://localhost:8081 -t sml_web .`
   1. `docker container run --rm -p 8080:4000 -it sml_web`
1. Open another shell
1. Navigate to the `...\Smart-Medication-List\net.smartmedicationlist.web` directory
1. run these commands in your shell:
   1. `docker image build -t sml .`
   1. `docker container run --rm -p 8081:8080 -it sml`
1. Go to this URL and enter any login: http://localhost:8080/
1. On the Dashboard page.  A patient ID must be entered from [this public FHIR database](http://hapi.fhir.org/baseDstu3/), the FHIR database which this app interfaces with
   1. A new patient ID can be created in this FHIR database for testing by going to either of these pages:
      1. http://localhost:8080/sml2/addPatient
      1. http://localhost:8080/sml2/addPatient2
   1. A patient ID from either of the above pages can then be copied and pasted into the Dashboard page to view the results 

To view hosted version:
-
1. Go to this URL and enter any login: https://cs6440-f18-prj31.apps.hdap.gatech.edu/
1. On the Dashboard page.  A patient ID must be entered from [this public FHIR database](http://hapi.fhir.org/baseDstu3/), the FHIR database which this app interfaces with
   1. A new patient ID can be created in this FHIR database for testing by going to either of these pages:
      1. https://cs6440-f18-prj31.apps.hdap.gatech.edu/sml2/addPatient
      1. https://cs6440-f18-prj31.apps.hdap.gatech.edu/sml2/addPatient2
   1. A patient ID from either of the above pages can then be copied and pasted into the Dashboard page to view the results 
1. Note: this hosted version was deployed using the Jenkins Declarative Pipeline, in combination with a Rancher Docker registry.  With the right Jenkins and Github settings, we were also able to use a Github hook in order to automatically rebuild the website once a new Github commit is submitted.  To recreate this setup, you will need a Jenkins instance, as well as a Rancher instance.  Using the right settings, the `Jenkinsfile` and `docker-compose.yml` files in the root directory of this repository can be used to deploy the two Docker instances, namely the Tomcat and NodeJS servers, to your own website.
