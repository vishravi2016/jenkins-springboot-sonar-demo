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
                sh 'mvn clean package'
            }
        }

         stage('test'){
             steps{
                sh 'mvn test'
             }
         }

          stage('code anylysis with sonarqube'){
                     steps{
                         withSonarQubeEnv('SonarQube'){
                            sh 'mvn sonar:sonar'
                         }
                     }
          }
          stage('Quality gate'){
            steps{
                timeout(time:2, unit: 'MINUTES'){
                    waitForQualityGate abortPipeline: true

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