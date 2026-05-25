pipeline {

    agent any

    environment {
        IMAGE_NAME = "jenkinsdemo"
        IMAGE_TAG = "${BUILD_NUMBER}"
    }

    tools {
        jdk 'JDK17'
        maven 'Maven3'
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'master',
                        url: 'https://github.com/Harish20-01/JenkinsDemo.git'
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean package'
            }
        }

        stage('Unit Tests') {
            steps {
                bat 'mvn test'
            }
        }

        stage('Build Docker Image') {
            steps {
                bat 'docker build -t %IMAGE_NAME%:%IMAGE_TAG% .'
            }
        }

        stage('Run Container') {
            steps {
                bat '''
                docker stop demo
                docker rm demo
                docker run -d --name demo -p 8080:8080 %IMAGE_NAME%:%IMAGE_TAG%
                '''
            }
        }

        stage('Health Check') {
            steps {
                powershell '''
                Start-Sleep -Seconds 20
                Invoke-WebRequest http://localhost:8080/health
                '''
            }
        }
    }

    post {

        success {
            echo 'Pipeline Successful'
        }

        failure {
            echo 'Pipeline Failed'
        }

        always {
            cleanWs()
        }
    }
}