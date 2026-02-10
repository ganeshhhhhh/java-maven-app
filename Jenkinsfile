pipeline {
    agent any

    tools {
        maven 'Maven'
    }

    environment {
        SONAR_TOKEN = credentials('sonar-token')
        IMAGE_NAME = "demo-app"
    }

    stages {

        stage('Checkout') {
            steps {
                git 'https://github.com/yourrepo/demo.git'
            }
        }

        stage('Compile & Test') {
            steps {
                sh 'mvn clean test'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                sh '''
                mvn sonar:sonar \
                -Dsonar.login=$SONAR_TOKEN
                '''
            }
        }

        stage('Dependency Check') {
            steps {
                dependencyCheck additionalArguments: '--scan .'
                dependencyCheckPublisher pattern: '**/dependency-check-report.xml'
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

