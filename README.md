# SpringBootMVC
Spring Boot Web MVC Application

## This project includes and uses belows
* Spring Boot
* Spring MVC for the web part
* Spring Data
* Spring Security (Database authentication, authorization)
* Thymeleaf for the template engine
* Embedded Tomcat as application server.

## For Running application
* Fill the datasource properties as mentioned below for Oracle on `application.properties` file
 ``` 
  spring.datasource.url=jdbc:oracle:thin:@db:1525:DB
  spring.datasource.username=DBUSER
  spring.datasource.password=PASSWORD
  spring.datasource.driverClassName=oracle.jdbc.driver.OracleDriver
  spring.jpa.hibernate.dialect=org.hibernate.dialect.Oracle10gDialect
 ```
 
* At first run;
  1. comment out this property on `application.properties` file for creating tables, then you can comment in
	  > spring.jpa.hibernate.ddl-auto=update
  
  2. Run `init.sql` on your database

* Run application on 8080 port (You can change running port on `application.properties` file)
