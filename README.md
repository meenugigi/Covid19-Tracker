# Covid19-Tracker



This application displays real time covid data and has been built using Spring Boot and Mysql database. It imports raw CSV data from web and displays it in a formatted manner using a template engine - Thymeleaf.

Capabilities include:

- displays no of total confirmed cases.
- displays no of new confirmed cases.
- displays no of total deaths.
- displays no of new deaths.
- displays no of total recovered cases.
- displays no of new recoveries.
- allows user to search data by inputting country name.
- sort data in ascending or descending order of cases.
- displays the last data updated date.



To run app on localhost
- Download the zip file.
- Open Eclipse IDE.
- Click File -> Import -> (Expand)Maven -> Existing Maven Projects -> Browse -> Finish.
- Delete system.properties file from the root directory.
- Navigate to src/main/resources/application.properties.
- In application.properties file, uncomment the commented lines and comment the last two lines.
- In pom.xml, uncomment the dependency that corresponds to 'mysql-connector-java' and comment the dependency that corresponds to the 'postgresql'.
- Under src/main/java, locate 'CoronavirusTrackerApplication.java'. Right click -> Run as -> Java Application.
- Open localhost:8080

Live demo: https://meenu-covid19tracker.herokuapp.com/
