def SendHook(body) {
    def curlCommand = """
        curl -L -X POST -H "Content-Type: application/json" -d '$body' https://disco-hooker.onrender.com/hook
    """

    def response = sh(script: curlCommand, returnStatus: true)

    println("Exit Code: ${response}")
}

def GetBody(stageName, stageState) {
    if (stageState != 'SUCCESS') {
        return """
            {
                "title": "Pipeline MISC",
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
                "title": "Pipeline MISC",
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
    }

    stages {
        stage("Octopus Start") {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    echo 'Running in compose!'
                    sh "docker compose start"
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
                        sh "mvn test -Dtest=ProductServiceImplTest -Dspring.profiles.active=test"
                        script {
                            SendHook(GetBody("Testing my Java code", "${currentBuild.currentResult}"))
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
                            SendHook(GetBody("Code Coverage", "${currentBuild.currentResult}"))
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
        stage("Discord Notify") {
            steps {
                script {
                    def body = """
                                {
                                  "title": "Pipeline MISC",
                                  "msg": "Pipeline ran **smoothly**",
                                  "status": 1
                                }
                        """

                    SendHook(body)
                }
            }
        }
    }
}
