#!/bin/bash
set -e
userid_dockerhub=''
registry_dockerhub=''

docker pull $userid_dockerhub/$registry_dockerhub
docker stop $(docker ps -a -q --filter "name=crns-webserver") && docker rm $(docker ps -a -q --filter "name=crns-webserver")
docker run -d --name crns-webserver -p 8081:8080 $userid_dockerhub/$repo_dockerhub

exit 0