pipeline {
    agent any 

    tools {
        maven '3.9.4'
        jdk 'java-17'
    }
    environment {
        SONAR_HOST_URL = 'http://54.173.146.101:9000/'
        SONAR_TOKEN = 'sqa_f59692a808e850ec41ed1cad80731eb17ba2c477'
    }

    stages {
        stage('Build Artifact And Sonar') {
            steps {
                script {
                    sh 'mvn clean verify sonar:sonar -Dsonar.qualitygate.wait=true -Dsonar.host.url=$SONAR_HOST_URL -Dsonar.login=$SONAR_TOKEN'
                }
                post {
                    success {
                        archiveArtifacts 'target/**'
                    }
                }
            }
        }

    }
}
