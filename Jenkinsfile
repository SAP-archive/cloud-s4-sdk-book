#!/usr/bin/env groovy 

node {
    deleteDir()
    library "piper-library-os@google-next"
    library "s4sdk-pipeline-library@google-nexts"
    sh "git clone --depth 1 -b google-next https://github.com/SAP/cloud-s4-sdk-pipeline.git pipelines"
    load './pipelines/s4sdk-pipeline.groovy'
}
