We used Java 12. For the server, we used Tomcat 9.0.17

We start the project inside intelliJ. Here are the steps:
- (1) import all the maven dependencies 
- (2) Set up the Tomcat server: edit configurations > add new configuration > Tomcat server > Local then click on 'configure' and point it to the folder where tomcat is located. The project runs on http://localhost:8080/
- (3) Make sure we are using JDK 12
- (4) Checking that the artifact xplan:war exploded has been added. [and deployed by Tomcat]