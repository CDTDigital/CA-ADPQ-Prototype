**Deployment Environment**
--------------------------

 - Created an AWS Ubuntu Server 14.04 LTS (HVM) with an elastic IP 35.163.198.30 using aws_set_up.tf and variables.tf templates in the jenkins folder.
 - Created a directory /var/jenkins-home for persistent storage of /var/jenkins_home.
 - Created two docker containers named crns-webserver and crns-mysql.
 - crns-webserver runs a tomcat:8-jre8 image and is accessible on port 8081.
 - crns-webserver runs a tomcat:8-jre8 image and is accessible on port 8081.
 - crns-mysql runs a mysql image(5.7.17) and is accessible on port 3306.


**Deployment Steps**
--------------------------
**Build.sh job:**
 
 
 - We have defined four variables named userid_ dockerhub, password_dockerhub, email_id_dockerhub and repo_dockerhub.
 - userid_dockerhub holds the user Id of the user registered with Docker Hub.
 - password_dockerhub holds the password of the user.
 - email_id_dockerhub stores the email address used while registering with Docker Hub.
 - registry_dockerhub contains the Docker Hub registry to which image is pushed.






A git webhook notifies Jenkins on every commit in the webservice folder at https://github.com/InTimeTec-Admin/CA-ADPQ-Prototype.git. And Jenkins triggers the job build.sh. and ROOT.war is created.

Once ROOT.war is successfully built, we build the dockerfile, which pulls tomcat8:jre8 image and deploy the ROOT.war on it. At this point, docker image with solution code is created.After that docker image is pushed to docker hub.


**Deploy.sh job:**


 - userid_dockerhub, holds the user Id of the user registered with Docker Hub.
 - registry_dockerhub, contains the Docker Hub registry to which image is pushed.
 - Deploy.sh pulls the image from docker hub and stop the intermediate container crns-webserver and then removes it.
 - It then runs the newly pulled docker image from docker hub.




