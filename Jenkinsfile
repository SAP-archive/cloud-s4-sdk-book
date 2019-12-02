
def dockerImageName = 's4sdk/mock-server'
def dockerImage = null

lock('mock-server') {
    stage('Build') {
        node('master') {
            milestone 10
            deleteDir()

            sh "git clone https://github.com/SAP/cloud-s4-sdk-book.git mock-server -b mock-server"

            dir("mock-server"){

                def buildArgs = [
                  '--pull'
                ]

                withCredentials([string(credentialsId: 'api-sap-key', variable: 'KEY')]) {
                  buildArgs.add("--build-arg API_KEY=${KEY}")
                  dockerImage = docker.build(dockerImageName, buildArgs.join(' ') + ' .')
                }
            }
        }
    }

    stage('Install') {
        milestone 20
        node('master') {
            def containerName = 'mock-server' 
            sh "docker stop ${containerName} || echo 'Stopping failed'"
            sh "docker run -d --rm -p 3000:8080 --name ${containerName} ${dockerImageName}"
            currentBuild.result = 'SUCCESS'
        }
    }
}
