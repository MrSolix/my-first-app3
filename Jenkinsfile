pipeline {
    agent any
    tools {
        maven 'maven'
        jdk 'jdk11'
    }

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

        stage('Docker deployment') {
            steps {
                sh 'docker-compose -f docker-compose.yaml up -d'
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
    }
}