pipeline {

    agent any

    environment {
        IMAGE_NAME = "jenkinsdemo"
        IMAGE_TAG = "${BUILD_NUMBER}"
    }

    tools {
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
                sh 'mvn clean package'
            }
        }

        stage('Unit Tests') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh """
                docker build -t ${IMAGE_NAME}:${IMAGE_TAG} .
                """
            }
        }

        stage('Run Container') {
            steps {
                sh '''
                docker stop demo || true
                docker rm demo || true

                docker run -d \
                --name demo \
                -p 8080:8080 \
                ${IMAGE_NAME}:${IMAGE_TAG}
                '''
            }
        }

        stage('Health Check') {
            steps {
                sh '''
                sleep 20
                curl http://localhost:8080/health
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