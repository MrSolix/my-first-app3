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
                    sh 'docker rm -f my-mongo-container'
                    sh 'docker rm -f postgres-db'
                    sh 'docker rm -f my-app'
                    sh 'docker rmi my-pipeline_app'
                }
            }
        }

        stage('Build') {
            steps {
                sh 'mvn install'
            }
        }

        stage('Docker deployment') {
            steps {
                sh 'docker-compose -f docker-compose.yaml up -d'
            }
        }

        stage('SonarQube check') {
            steps {
                withSonarQubeEnv(credentialsId: 'sonar-tipa',
                installationName: 'local-sonarqube') {
                    sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.1.2184:sonar'
                }
            }
        }
    }
}