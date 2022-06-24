pipeline {
    agent any

    stages {
        stage('Clean previous version') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'SUCCESS') {
                    sh 'mvn clean'
                    sh 'docker rm -f postgres-db'
                    sh 'docker rm -f my-first-app'
                    sh 'docker rmi my-first-app_app'
                }
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn install -DskipTests=true'
            }
        }

//         stage('SonarQube check') {
//             steps {
//                 withSonarQubeEnv(credentialsId: 'sonar-tipa',
//                 installationName: 'local-sonarqube') {
//                     sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.7.0.1746:sonar'
//                 }
//             }
//         }

//         stage('Docker deployment') {
//             steps {
//                 sh 'docker-compose -f DockerCompose.yml up -d'
//             }
//         }
    }
}