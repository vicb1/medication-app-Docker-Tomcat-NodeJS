# Instructions for deploying web project

1. Pull entire project from https://github.gatech.edu/gt-cs6440-hit-fall2018/Smart-Medication-List
    1. To run locally:
        1. Install node: https://nodejs.org/en/download/
        1. Install Visual Studio Code: https://code.visualstudio.com/download
        1. Run Visual Studio Code.
        1. File > Open Folder > ..\Smart-Medication-List\Smart.Medication.List.Web
        1. Ctrl + `
        1. In the console panel:
            1. npm i
            1. [Enter]
            1. Wait for completion.
            1. npm start -s
            1. [Enter]
        1. Your browser should open with the url: http://localhost:3000
      1. To run with Docker:
          1. Install Docker
          1. Navigate to the ..\Smart-Medication-List\Smart.Medication.List.Web, and run the Docker commands:
              1. cd "C:\Users\Emily\git\Smart-Medication-List\Smart.Medication.List.Web"
              1. docker system prune
              1. docker image build -t sml_web .
              1. docker container run --rm -p 8080:4000 -it sml_web
          1. Go to this page to test: http://localhost:8080
          1. Windows Users - If this doesn't work, try the troubleshooting steps described here (but substitute "boot2docker-vm" with "default" or whatever your Docker VM is named): https://stackoverflow.com/a/34839818/109941
          1. When you’ve made changes to the code you can press CTRL+C in the CMD window to kill the docker run, and re-run above instructions.
1. After ensuring local changes work well, you may commit to Github and see updates ~1 minute later at this webpage.  Please ensure your updates work well on this website because this is our final product that we're "handing in"
   1. https://cs6440-f18-prj31.apps.hdap.gatech.edu/

