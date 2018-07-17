#!/usr/bin/env groovy 

node {
    deleteDir()
    library "piper-library-os@feature/k8s"
    library "s4sdk-pipeline-library@feature/k8s"
    sh "git clone --depth 1 -b feature/k8s https://github.com/SAP/cloud-s4-sdk-pipeline.git pipelines"
    load './pipelines/s4sdk-pipeline.groovy'
}
