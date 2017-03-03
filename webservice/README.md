
Overview
========
This webservice contains one main Java project:

-Website: This project contains the code for the main website.

Code & Assets
===============

These are stored in the [new github repository](https://github.com/InTimeTec-Admin/CA-ADPQ-Prototype) and consists of the following.

* Java
* Database scripts
* Images
* CSS
* JS
* Other content files referenced by the webservice.

Prerequisites
=============
- [Java 8 JDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
- (Optional) [Maven](https://maven.apache.org/download.cgi)
	- This is only needed if you plan to build/run the project from the command line. If you only plan to use Eclipse, this isn't needed.
	- [Installation instructions](https://maven.apache.org/install.html)
- My SQL 
	- Go to (\webservice\database) for all the database scripts.

Website Project Configuration
=============================
- Go to \webservice\website\src\main\resources\config
- Modify override.properties to 
	- Set the datasource properties to control which database is used.

Running the Website Project without Eclipse
===========================================
If you don't need to make changes to Java files and you want to work in an IDE other than Eclipse for editing front-end
files you can run the application by following these steps.

1. Open a command prompt
2. Navigate to \webservice\website
3. Execute the spring-boot-run script:
	- Type mvn spring-boot:run

4. Open a browser and navigate to http://localhost:8080

*NOTE:* Once the application is running, you can edit the front-end source files in \webservice\website\src\main\webapp, and you can view the changes without restarting the application.

(Optional) Eclipse Setup
========================

- Download and install Eclipse or Spring Tool Suite
- Open Eclipse
- (Optional) Set Eclipse to hide folders of nested Maven projects
	- Windows > Preferences > Maven, check "Hide folders of physically nested modules"
	- Without this change, Maven will show duplicate folders in the webservice directory 
- Right-click in the Package Explorer and pick Import...
- Expand the Maven folder
- Click "Existing Maven Projects"
- Click Next
- In Root Directory field, browse to \webservice
- Select /pom.xml and all its child POM's in the Project listing
- (Optional) In the "Add project(s) to working set" section change "webservice" to something more meaningful, like "CRNS"
- Click Finish

*TIP:* By default, Eclipse creates a working set from the "webservice" project. In the Package Explorer, you can click the down arrow at the right and choose Top Level Elements > Working Sets to see the "webservice" working set (which you may have renamed above). The Working Set is a good way to filter out other projects you may have loaded in Eclipse.

(Optional) Running the Website Project with Eclipse
===========================================================
- If using Spring Tool Suite
	- Right click on the project in Package Explorer
	- Select Run As > Spring Boot App
	- Open a browser and navigate to the project URL
		- Website: http://localhost:8080

- If using plain Eclipse
	- Right click on the project in Package Explorer
	- Select Run As > Java Application
	- Select the project from the list and click OK
		- Website: "Application - com.intimetec.crns.web"
	- Open a browser and navigate to the project URL
		- Website: http://localhost:8080


Troubleshooting
===============
- Verify Java version used by Maven (type mvn --version). Java version must be 1.8. If JAVA_HOME is not set to the correct value, you can update system-wide or just for the current console session. If you update system-wide, you'll need to close and re-open the command window.

- "Access to DialectResolutionInfo cannot be null when 'hibernate.dialect' not set". This occurs because the database connection information is invalid. Open override.properties and confirm the values for spring.datasource.xxx, or set the spring.profiles.active value to "stubs" to not require a valid database connection.

Application Configuration
=========================
Terminology

* Environment (required)
	* The tier where the site is deployed (e.g. dev, qa, staging, production)
	* These files are named <environment>.properties
	* These files are stored at \webservice\website\src\main\resources\environments
* Deployment
	* Deployment configuration can be applied as:
		* Override (required)
			* This file is named override.properties
			* This files is loaded from \webservice\website\src\main\resources\config
			* This file can store sensitive information (such as credentials)
			* This file is created in one of two ways
				* By developers: Manually, by using override.properties.sample as a starting point
				* By Jenkins: Automatically, by creating a new override.properties from scratch as part of the build process.

The running application applies the configuration files in the order listed above, with values in later files taking precedence.  

Unmanaged Maven Libraries
=========================

Whenever possible, project dependencies should be referenced in a public Maven repository.  In cases where this isn't possible, the dependencies should be installed in the project's local Maven repository (located at \application\website\repo) and committed to source control.

The MySQL JDBC driver is an example of a dependency that is not avaible in a public Maven repository.  In order to cleanly reference this dependency in the project, it was acquired from the [MySql Download Center](https://dev.mysql.com/downloads/connector/j/) and installed into the project's local Maven repository.

The following site discuss this approach.

* [Example 1](https://devcenter.heroku.com/articles/local-maven-dependencies)

Github Repository
===================

The repository for CRNS is hosted at [here](https://github.com/InTimeTec-Admin/CA-ADPQ-Prototype).

License
===================

Copyright (c) 2017 In Time Tec under GNU License (https://www.gnu.org/licenses/agpl-3.0.en.html)
