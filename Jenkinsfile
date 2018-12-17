#!/usr/bin/env groovy
pipeline{
    agent any

    //Define stages for the build process
    stages{
        //Define the deploy stage
        stage('Deploy'){
            steps{
                //The Jenkins Declarative Pipeline does not provide functionality to deploy to a private
                //Docker registry. In order to deploy to the Rancher Docker registry we must write a custom Groovy
                //script using the Jenkins Scripting Pipeline. This is done by placing Groovy code with in a "script"
                //element. The script below registers the Rancher Docker registry with the Docker instance used by
                //the Jenkins Pipeline, builds a Docker image of the project, and pushes it to the registry.
                script{
                    docker.withRegistry('https://build.yourdomain.com'){
                        //Build and push the web server image
                        def applicationImage = docker.build("sml2:1.0","-f ./TomcatServer/Dockerfile ./TomcatServer")
                        applicationImage.push('latest')

                        //Build and push the web UI image
                        def webGuiImage = docker.build("sml-web:1.0","-f ./NodejsServer/Dockerfile ./NodejsServer")
                        webGuiImage.push('latest')

                    }
                }
            }
        }
        //Define stage to notify Rancher
        stage('Notify') {
            steps {
                script {
                    rancher confirm: true, credentialId: 'rancher-server', endpoint: 'https://rancher.yourdomain.com/v2-beta', environmentId: '1a7', environments: '', image: 'build.yourdomain.com/sml2:latest', ports: '', service: 'myService/sml2', timeout: 50
                    rancher confirm: true, credentialId: 'rancher-server', endpoint: 'https://rancher.yourdomain.com/v2-beta', environmentId: '1a7', environments: '', image: 'build.yourdomain.com/sml-web:latest', ports: '', service: 'myService/sml-web', timeout: 50
                }
            }
        }
    }
}
