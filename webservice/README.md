
Overview
========
This webservice contains one main Java project:

-Website: This project contains the code for the main website. 

Code & Assets
===============

These are stored in the [new github repository](https://github.com/InTimeTec-Admin/CA-ADPQ-Prototype) and consists of the following.

* Java
* Database scripts
* JSON
* Images
* CSS
* JS
* Other content files referenced by the webservice.(be it hard-coded in the templates, or dynamically read from the database or JSON)

Prerequisites
=============
- [Java 8 JDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
- (Optional) [Maven](https://maven.apache.org/download.cgi)
	- This is only needed if you plan to build/run the project from the command line. If you only plan to use Eclipse, this isn't needed.
	- [Installation instructions](https://maven.apache.org/install.html)
- (Optional) My SQL 
	- The website project can be run in stub mode to avoid the need for a local database.
	- See the "Getting Started" section in the database README file (at \webservice\database) for version information.

Website Project Configuration
=============================
- Go to \webservice\website\src\main\resources\config
- Copy override.properties.sample and rename to override.properties
	- Set the datasource properties to control which database is used.
	
To run the application without needing a local database, no more changes are necessary. In this mode the application
will run using hard-coded data provided by DAO's in the com.intimetec.crns.daos.stubs package.

To run the application against a database, do the following:

- Comment out (with a leading #) the line starting with "spring.profiles.active"
- Update the properties beginning with "spring.datasource" with values for your given database
	- Refer to the README file in the \webservice\database folder for more information on obtaining a database backup

Running the Website Project without Eclipse
===========================================
If you don't need to make changes to Java files and you want to work in an IDE other than Eclipse for editing front-end
files you can run the application by following these steps.

1. Open a command prompt
2. Navigate to \webservice\website
3. Execute the spring-boot-run script:
	- Windows: Type spring-boot-run
	- Mac OS or Linux: Type ./spring-boot-run.sh
				Or
	Using the Maven Plugin:
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

Unit Tests
==========
To run the unit tests for a given project, you need to make a copy of the associated properties file (found at \webservice\<module>\src\test\resources\config\override.properties.sample) similar to the copies made above for the application. In these new properties files you need to update the required settings (e.g. database connection information) for you local configuration.

Application Configuration
=========================
Terminology

* Core (required)
	* The base logic/configuration that is shared by all sites, in all environments
	* These files are stored at \webservice\website\src\main\resources\core
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

Stub Data
=========
When running in stub mode the application displays data read from a JSON file packaged in the application.

* The JSON file containing the stub data is located at \webservice\website\src\main\resources\stubs\data.json
* The content of the JSON file is generated by the SQL script at \webservice\website\src\main\resources\stubs\data.sql

*NOTE:* Stub data currently contains en-us content only. It's possible to generate localized stub content by pointing data.sql to a localized database, but the new data.json file should not be committed. It would be possible to update the initialization logic to support choosing the appropriate data-xx.json file (similar to how the appropriate properties files are loaded) but this has not yet been implemented.

Mail Service
============
When admin publish any notification requests, the website saves the request into the database and also sends the notification via email to the recipient configured in the properties files.  When running in stub mode, the application configures a stub email service which doesn't do anything.  When not in stub mode, the application must be configured to run against an SMTP server (be it real or fake) via the spring.mail.xxx properties in the properties files.  If you don't have a real SMTP server than you can use, [Papercut](https://papercut.codeplex.com/) provides a fake SMTP server that is a great alternative.

Unmanaged Maven Libraries
=========================

Whenever possible, project depencies should be referenced in a public Maven repository.  In cases where this isn't possible, the depencies should be installed in the project's local Maven repository (located at \application\website\repo) and committed to source control.

The MySQL JDBC driver is an example of a dependency that is not avaible in a public Maven repository.  In order to cleanly reference this dependency in the project, it was acquired from the [MySql Download Center](https://dev.mysql.com/downloads/connector/j/) and installed into the project's local Maven repository.

The following site discuss this approach.

* [Example 1](https://devcenter.heroku.com/articles/local-maven-dependencies)

Github Repository
===================

The repository for CRNS is hosted at [here](https://github.com/InTimeTec-Admin/CA-ADPQ-Prototype).