
**California Residents Notification Service (CRNS)**
------------------------------------------------

URL of Running Prototype Web application: http://35.163.198.30:8081

California Residents Notification Service (CRNS) is a web-based, responsive messaging application designed for communication between authorized administrators and California residents. It allows authorized administrators to login to the web app and generate notifications, as well as track the previous notifications and their details. The users, California residents, can manage their profile and receive emergency and non-emergency notifications via email, Short Message Service (SMS), CRNS App, Location-based tracking services that were opted during the profile registration.

To get started, users will register with the web app by providing the basic information such as name, city, phone numbers and zip code, etc. While registering, they can opt to receive notification via the services mentioned above and also, opt to receive notifications based on the geo-location of their cellphone. Once logged in, users can check for regular updates and the notifications that were opted for. Users can edit their profiles with additional information as required and also, reset passwords.

**Team Work Categories**

 The team consists of:

- Rob Tuft, Agile Coach
- Jwala Prasad, Product Manager 
- Shiva Dixit, Delivery Manager, Technical Architect, Back End Web Developer
- Inwinder Kochhar, Technical Architect, Front End Web Developer
- Jitendra Sen, Usability Tester
- Sunil Jhamnani, Front End Web Developer
- Harish Patidar, Mobile App Developer
- Bincy Samuel, Back End Web Developer
- Umesh Kumar Mehta, DevOps Engineer
- Mohan Singh, Visual Designer
- Grace Arpana, Writer/Content Designer

**Technical Approach**

We have followed an Agile and User Driven Design Process to build this prototype where all the requirements were driven by users (California residents). Scrum is the software development methodology utilized for this project:

 - We have started with interviewing users and collected the information to build user stories which were owned by Product owner for continuous refinement and prioritization.
 - Created initial UI designs following user centeric design process and presented to users, and incorporated review feedback.
 - One week sprints were used.  Each sprint was kicked off with a Sprint Planning meeting where the product owner designated the Sprint Objective. During the Sprint Planning, the development team committed to finishing the stories identified from the top of the prioritized product backlog according to their capacity as measured by their sprint velocity. 
 - At the closure of the Sprint, a Sprint Review was held to demo the user-centric features to the product owner and users which was delivered in the sprint. 
 - A Retrospective Meeting at the closure of each sprint was held to review the software development process and identify the areas that were working well and those areas that needed improvement. Changes to the process were implemented immediately.  

The product owner represented the interests of the end user and determined the details and acceptance criteria of the user stories (features) of the application. The product owner also determined the priority order of the user stories in the backlog. End users received frequent releases of the application (usually at the end of each sprint) to evaluate the application for its features and overall progress.

JIRA is used as the Scrum tool that contains the Scrum Artifacts: user stories, product backlog, sprint backlog, burndown charts, sprint reports, bug reports, test cases, and test results. 

**Further Information**
 -  We have used JIRA as Scrum tool, and the reports (in .xlsx format) of each Sprint in terms of tasks done/carry forwarded has been added to GitHub. ([Process Documents](https://github.com/InTimeTec-Admin/CA-ADPQ-Prototype/tree/master/process-docs))


**U.S. Digital Services Playbook**

Our approach is consistent with the US Digital Services Playbook. Details can be found here ([US Digital Services Playbook](https://github.com/InTimeTec-Admin/CA-ADPQ-Prototype/wiki/U.S.-Digital-Services-Playbook))


**User Stories**

User stories can be found here. ([User stories](https://github.com/InTimeTec-Admin/CA-ADPQ-Prototype/wiki/User-Stories))


**CRNS Open Source Technologies**

- For Hybrid Mobile Application
	- Apache Cordova - Framework for build cross-platform hybrid mobile application
	- Angular JS - Javascript framework for building MV* applications
	- Ionic Framework - Javascript and HTML 5 UI framework built for Angular JS
	- Toaster.js - Library for showing onscreen notification
- For Frontend Web Application
	- Angular JS - Javascript framework for building MV* applications
	- Bootstrap - HTML5 UI Framework for web application
	- jQuery - Javascript library
- For Backend Server Application/REST APIs
	- Spring - Java based MVC framework
	- Spring Security - To have session management and user authentication/authorization
	- Hibernate - ORM (Object-Relational Mapping) tool for DB connectivity
	- JPA - Java Persistence API (JPA) is a Java specification for accessing, persisting, and managing data between Java objects / classes and a relational database.

Additional information and Application Architecture can be found here ([Document](https://github.com/InTimeTec-Admin/CA-ADPQ-Prototype/wiki/Application-Architecture))


**Deployment Instructions (Development Environment)**

([Instructions](https://github.com/InTimeTec-Admin/CA-ADPQ-Prototype/blob/master/devops/README.MD))


**Build Instructions for Web**

([Instructions](https://github.com/InTimeTec-Admin/CA-ADPQ-Prototype/blob/master/webservice/README.md))


**Build Instructions for Mobile**

([Instructions](https://github.com/InTimeTec-Admin/CA-ADPQ-Prototype/blob/master/application/README.md))


**Dependencies:**

A Github account
A computer running Mac OS X 10.9.5+ with Xcode Version 6.1.1+
Download and install Virtualbox 5.0.20
Download and install Docker Toolbox version 1.11.2
To install and run CRNS, execute these commands:

$ git clone git@github.com: https://github.com/InTimeTec-Admin/CA-ADPQ-Prototype
$ cd 
$ bin/setup-virtualmachine
$ bin/run-app-in-container --env development
Access CRNS in your browser using the IP address output by the last command.

Setting up and deploying CRNS in production in Heroku is documented on the wiki.
 
 
**Release Notes**
- Mobile application build release notes can be found here ([Release Notes](https://github.com/InTimeTec-Admin/CA-ADPQ-Prototype/blob/master/builds/Release%20Notes.md))

<b>Reviewer Testing</b>

Reviewers can sign up or register on the landing page, create a profile, and opt for the required notification services. To verify the CRNS messaging and notification functionalities works as advertised, reviewers can log in as an admin user, generate and send a notification to the registered users. Login as a registered user and verify the notification received that was opted during the user profile registration. 

- Admin User can login with following credentials
	- User Name : admin
	- Password : crnsadmin

**License**

Copyright (c) 2017 In Time Tec.
