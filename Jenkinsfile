pipeline {
    agent any

    tools {
    maven "Maven-3"
    }
     environment {
            KUBECONFIG='/var/jenkins_home/.kube/config'
            DOCKER_IMAGE = 'vishravi1975/springboot-k8s-demo'
            DOCKER_CREDENTIALS = 'dockerhub-creds'
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

          stage('Docker Build') {
                      steps {
                          sh 'docker build -t $DOCKER_IMAGE:latest .'
                      }
          }

           stage('Docker Login and Push') {
                      steps {
                          withCredentials([usernamePassword(
                              credentialsId: "$DOCKER_CREDENTIALS",
                              usernameVariable: 'DOCKER_USERNAME',
                              passwordVariable: 'DOCKER_PASSWORD'
                          )]) {
                              sh '''
                              echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin
                              docker push $DOCKER_IMAGE:latest
                              '''
                          }
                      }
           }

            stage('Deploy to Kubernetes') {
                       steps {
                           sh '''
                           kubectl apply -f k8s/deployment.yaml
                           kubectl apply -f k8s/service.yaml
                           kubectl rollout status deployment/springboot-app-deployment
                           '''
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