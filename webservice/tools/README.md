This directory contains tools used by the application.

Flyway
======
[Flyway](http://flywaydb.org/) is a database change management tool.

To prepare for running Flyway locally, follow these steps.

* Create a copy of \application\tools\flyway\flyway.properties.sample and name it flyway.properties
* Update settings in the new file

To initialize the database for use with Flyway, follow these steps. This is a one-time process for a given database.

* Open SSMS
* Manually run \application\database\migrations\001.sql
* All future migrations should be run via Flyway using the steps below

To apply migration scripts follow these steps.

* Open a command prompt
* Navigate to the \application\tools directory
* Type: mvn flyway:migrate

To run without creating a properties file you can also do the following.

* Open command prompt
* Navigate to \application\tools
* Run the following: mvn flyway:migrate -Pcustom -Dflyway.url=? -Dflyway.user=? -Dflyway.password=?

To avoid having to use the command prompt, you can set up Run configurations in Eclipse.

* Open Run > Run Configurations...
* Right-click the Maven Build category and click New
* Set the following properties on the Main tab
	* Name (example): CRNS - Migrate DB 
	* Base directory: ${workspace_loc:/application/tools}
	* Goals: flyway:migrate	
* Click the Common tab
	* Click Run in the "Display in favorites menu" box
* Click Apply, then Close
* This run configuration will now appear at the top of the list in the following locations
	* Run > Run History...
	* The down array next to the Run As... menu item (the green circle with an arrow)

The run configuration approach listed previously uses the embedded Maven runtime. If you wish to use another runtime, you can configure external tools instead.

* Open Run > External Tools > External Tools Configuration...
* Right-click Program and select New
* Set the following properties on the Main tab
	* Name (example): CRNS - Migrate DB 
	* Location (example): C:\Program Files (x86)\Apache Software Foundation\Maven 3.3.1\bin\mvn.cmd
	* Working directory: ${workspace_loc:/application/tools-maven}
	* Arguments: flyway:migrate
* Click the Common tab
	* Click External Tools on the "Display in favorites menu" box
* Click Apply, then Close
* This run configuration will now appear at the top of the list in the following location
	* Run > External Tools...

Troubleshooting
===============
If you make changes to a script after it's been applied Flyway will fail saying that the script is not valid.  You can update the Flyway schema version table with the new hash by running the "validate" command.  Note that this will not rerun the script, it will just update the content-based meta information.
