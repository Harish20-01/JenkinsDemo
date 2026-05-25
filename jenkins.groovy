pipeline {

    agent any

    environment {
        IMAGE_NAME = "jenkinsDemo"
        IMAGE_TAG = "${BUILD_NUMBER}"
    }

    tools {
        jdk 'JDK17'
        maven 'Maven3'
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                        url: 'https://github.com/your-repo/springboot-demo.git'
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
                docker build \
                -t ${IMAGE_NAME}:${IMAGE_TAG} .
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