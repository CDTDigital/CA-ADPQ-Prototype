#!/bin/bash
set -e

docker pull deepakintime/cts
docker stop $(docker ps -a -q --filter "name=crns-webserver") && docker rm $(docker ps -a -q --filter "name=crns-webserver")
docker run -d --name crns-webserver -p 8081:8080 deepakintime/cts

exit 0