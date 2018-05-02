# Steps to deploy cloud server

1) Clone the project which has src folder and pom file.

2) Make sure maven is installed on the system.

3) Navigate the folder where pom file present and execute follwing commnad
	'mvn package'

4) The maven will build the project. Once the build is completed a war file with name 'j2ee.war' is generated in the target folder.

5) This war can be used to deploy the application on any server 

Example:
Deploying was on aws beanstalk. </br>
	1) Login to amazon aws account. </br>
	2) Go to beanstalk dash board </br>
	3) select a existing beanstalk instance or create a new one and select tom cat as server </br>
	4) If creating new one provide the war file when creating the server </br>
	5) If exisiting upload the new war file to replace the old one. </br>
	6) Once successfull deployed use application link to access the application </br>