#!/bin/bash
set -e

pushd "${WORKSPACE}/webservice"
mvn clean install
popd
mv "${WORKSPACE}/webservice/website/target/ROOT.war" ROOT.war



cat <<EOF >Dockerfile
FROM tomcat:8-jre8
MAINTAINER Intimetec LLC
RUN ["rm", "-fr", "/usr/local/tomcat/webapps/ROOT"]
COPY ROOT.war /usr/local/tomcat/webapps/ROOT.war
CMD ["catalina.sh", "run"]
EOF

docker build --rm --pull=true -t deepakintime/cts:latest .
docker login -u deepakintime -p pa55word -e deepak.sharma@intimetec.com
docker push deepakintime/cts:latest
docker logout

exit 0