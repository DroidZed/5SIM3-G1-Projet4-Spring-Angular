pipeline {
    agent any

    environment {
        dockerCredentials               = 'dockerCredentials'
        registry                        = 'islmm/islemnaffeti-5sim3-g1-projet4-spring-angular'
        dockerImage                     = ''
        sonarToken                      = credentials('sonarToken')
    }

    stages {
        stage('Clean Projects') {
            steps {
                dir("DevOps_Project") {
                    sh "mvn clean"
                }
            }
        }
        stage('Building project') {
            steps {
                dir("DevOps_Project") {
                    sh "mvn validate"
                    sh "mvn compile"
                }
            }
        }
        stage('Docker Image') {
            steps {
                    dir("DevOps_Project") {
                        script {
                            dockerImage = docker.build registry + ":1.0.0"
                    }
                }
            }
        }
        stage('Docker Push to hub') {
            steps {
                dir("DevOps_Project") {
                    script {
                        docker.withRegistry('', dockerCredentials) { dockerImage.push() }
                    }
                }
            }
        }
        stage("Docker Compose") {
            steps {
                dir("DevOps_Project") {
                    sh "docker compose up -d"
                }
            }
        }
        stage('Test the code') {
            steps {
                dir("DevOps_Project") {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                        sh "mvn test"
                    }
                }
            }
        }
        stage('Jacoco') {
             steps {
                dir("DevOps_Project") {
                    sh "mvn jacoco:report"
                }
             }
        }
        stage('SONAR') {
            steps {
                dir("DevOps_Project") {
                    catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                        sh "mvn sonar:sonar -Dsonar.token=$sonarToken"
                    }
                }
            }
        }
        stage('Nexus') {
            steps {
                dir("DevOps_Project") {
                    catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                        sh "mvn deploy -DskipTests"
                    }
                }
            }
        }
    }
}