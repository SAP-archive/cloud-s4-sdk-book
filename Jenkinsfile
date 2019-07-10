#!/usr/bin/env groovy 

String pipelineVersion = "v17"

node {
    deleteDir()
    sh "git clone --depth 1 https://github.com/SAP/cloud-s4-sdk-pipeline.git -b ${pipelineVersion} pipelines"
    withEnv(["ON_K8S=true"]) {
        load './pipelines/s4sdk-pipeline.groovy'
    }

}
