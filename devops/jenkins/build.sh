#!/bin/bash
set -e
userid_dockerhub=''
password_dockerhub=''
email_id_dockerhub=''
registry_dockerhub=''
pushd "${WORKSPACE}/webservice"
mvn clean install
popd
mv "${WORKSPACE}/webservice/website/target/ROOT.war" ROOT.war
docker build -f ${WORKSPACE}/devops/docker/dockerfile .
docker build --rm --pull=true -t deepakintime/cts:latest .
docker login -u $userid_dockerhub -p password_dockerhub  -e $email_id_dockerhub
docker push $userid_dockerhub/$registry_dockerhub:latest
docker logout

exit 0