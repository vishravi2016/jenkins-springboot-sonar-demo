pipeline {
    agent any

    tools {
    maven "Maven-3"
    }
    stages {
        stage('checkout') {
            steps {
                git branch: 'main',
                url: 'https://github.com/vishravi2016/jenkins-springboot-sonar-demo.git'
            }
        }
        stage('build'){
            steps{
                bat 'mvn clean package'
            }
        }

         stage('test'){
             steps{
                bat 'mvn test'
             }
         }

          stage('code anylysis with sonarqube'){
                     steps{
                         withSonarQubeEnv('SonarQube'){
                            bat 'mvn sonar:sonar'
                         }
                     }
          }


    }
   post{
           success{
               echo "docker image pushed successfully"
           }
           failure{
               echo "pipeline failed"
           }
   }



}