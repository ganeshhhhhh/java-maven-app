pipeline {
    agent any

    tools {
        maven 'Maven'
    }

    environment {
        IMAGE_NAME = "demo-app"
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                    credentialsId: 'github-creds',
                    url: 'https://github.com/ganeshhhhhh/java-maven-app.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonarqube') {
                    sh 'mvn sonar:sonar'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t $IMAGE_NAME .'
            }
        }

        stage('Trivy Scan') {
            steps {
                sh 'trivy image $IMAGE_NAME'
            }
        }

        stage('Deploy Container') {
            steps {
                sh '''
                docker rm -f demo || true
                docker run -d -p 8081:8080 --name demo $IMAGE_NAME
                '''
            }
        }
    }
}
