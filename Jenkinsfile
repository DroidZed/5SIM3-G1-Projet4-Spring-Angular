def SendHook(body) {
    def curlCommand = """
        curl -L -X POST -H "Content-Type: application/json" -d '$body' https://disco-hooker.onrender.com/hook
    """

    def response = sh(script: curlCommand, returnStatus: true)

    println("Exit Code: ${response}")
}

def GetBody(stageName, stageState) {
    if (stageState == 'FAILURE') {
        return """
            {
                "title": "Pipeline Angular",
                "msg": "One or more jobs **failed**:",
                "status": 0,
                "jobs": [
                    {
                        "name": "${stageName}", "value": "💢 FAIL"
                    }
                ]
            }
        """
    } else {
        return """
            {
                "title": "Pipeline Spring",
                "msg": "One or more jobs **passed**:",
                "status": 1,
                "jobs": [
                    {
                        "name": "${stageName}", "value": "💚 PASS"
                    }
                ]
            }
        """
    }
}

pipeline {
    agent any

    environment {
        DISCORD_WEBHOOK_URL             = credentials("DISCORD_WEBHOOK_URL")
        SONAR_TOKEN                     = credentials("SONAR_TOKEN")
        testClass                       = 'ProductServiceImplTest'
    }

    stages {
        stage("Octopus Start") {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    echo 'Running in compose!'
                    sh "docker compose up -d"
                    script {
                        SendHook(GetBody("Octopus Start", "${currentBuild.currentResult}"))
                    }
                }
            }
        }
        stage('Testing my Java code') {
            steps {
                dir("DevOps_Project") {
                    catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                        echo 'Testing..'
                        sh "mvn test -Dtest=$testClass"
                        script {
                            SendHook(GetBody("Octopus Start", "${currentBuild.currentResult}"))
                        }
                    }
                }
            }
        }
        stage('Code Coverage') {
            steps {
                dir("DevOps_Project") {
                    catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                        echo 'Generating code coverage files..'
                        sh "mvn jacoco:report"
                        script {
                            SendHook(GetBody("Octopus Start", "${currentBuild.currentResult}"))
                        }
                    }
                }
            }
        }
        stage('SONAR ANALYZER') {
            steps {
                dir("DevOps_Project") {
                    catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                        echo 'SonarQube running...'
                        sh "mvn sonar:sonar -Dsonar.token=$SONAR_TOKEN"
                        script {
                            SendHook(GetBody("SONAR ANALYZER", "${currentBuild.currentResult}"))
                        }
                    }
                }
            }
        }
        stage('GRAFANA') {
            steps {
                dir("DevOps_Project_Front") {
                    catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                        echo 'Angular testing...'
                        sh "ng test --watch=false"
                        script {
                            SendHook(GetBody("SONAR ANALYZER", "${currentBuild.currentResult}"))
                        }
                    }
                }
            }
        }
    }
}
