# Instructions for deploying project

1. Pull entire project from https://github.gatech.edu/gt-cs6440-hit-fall2018/Smart-Medication-List 
   1. Instruction on how to connect to our Github with Eclipse:
      1. https://www.youtube.com/watch?v=LPT7v69guVY
      1. use URI "https://github.gatech.edu/gt-cs6440-hit-fall2018/Smart-Medication-List"
      1. Use Protocol "https"
      1. Use Username and Password that you would use to login to Github
      1. Once connected to our Github project, go to File > Open project > go to your imported GIT project > open net.smartmedicationlist.web
1. Follow these instructions to be able to create a local WAR file.  Make sure to use tomcat:9.0.12 and jre11
   1. https://www.pegaxchange.com/2016/09/02/java-eclipse-tomcat/ 
1. Alternatively you can run mvn build within eclipse on the project.
   1. You can do this by right clicking on the project in Eclipse> right click on project -> run as maven clean -> run as maven install.
1. If you'd like to try deploying the WAR file locally in the Eclipse server (without using Docker), follow the instructions in step 2 then go to this URL:
   1. http://localhost:8080/smartmedicationlist/?name=Team%20EastCoasters
1. Install Docker
1. Navigate to the TomcatProject1 folder, and run the Docker commands:
   1. cd "C:\Users\Emily\git\Smart-Medication-List\net.smartmedicationlist.web"
   1. docker system prune
   1. docker image build -t sml .
   1. docker container run --rm -p 8080:8080 -it sml
1. Go to this page as an example:
   1. http://localhost:8080/?name=Team%20EastCoasters
1. When you’ve made changes to the code you can press CTRL+C in the CMD window to kill the docker run, and re-run above instructions, docker will auto-build the WAR file via Maven.
1. Go up a level to root project folder, make sure you have exited the previous run using CTRL-C.
1. run these two commands:
   1. docker-compose build
   1. docker-compose up sml
1. Repeat step 7 (clicking on URL) to verify page is up and running.
1. After you have verified docker is running, local changes can be verified by jumping straight to step 10.

  
