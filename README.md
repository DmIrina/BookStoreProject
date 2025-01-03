 ## Short description

This project represents an information system for managing a network of bookstores. The system is designed to automate business processes, optimize customer service, and enhance the organization’s operational efficiency. Its key features include inventory management, order processing, analytics, employee management, and integration with web platforms for online sales.

 ## Prerequisites
 * Java 17
 * Maven >=3.8.8
 * MySQL
    -   mysql -u root -p$mySQL_password -e "CREATE DATABASE IF NOT EXISTS bookshop;"

 * Bower
    - sudo apt-get update
    - sudo apt install nodejs-legacy
    - sudo apt-get install npm
    - sudo npm install -g bower 
    - sudo npm install -g bowerrc
 
 ## Spring REST, HATEOAS
 spring.data.rest.base-path=/api

        localhost:8381/api
        
 ## Deployment
 To run locally execute
 
      java -version
      mvn -v
      npm -v
      bower -v
      bower init
      bower install
      bower install jquery bootstrap --save
      mysql -u root -p$mySQL_password -e "CREATE DATABASE IF NOT EXISTS bookshop;"
      
      mvn clean install
      ./mvnw spring-boot:run
      

## Configuration 
     
  * [Thymelif with Angular](https://teamtreehouse.com/library/spring-basics/using-thymeleaf-to-serve-html)
    - Add a properties file at src/main/resources/application.properties
    - Add the following text at the top:
       spring.thymeleaf.mode = LEGACYHTML5
    - HTML parser, In the dependencies block of build.gradle, add Neko HTML as a runtime dependency:
      nekohtml:1.9.22'  

## Built With
Back end
  * [Spring Boot](https://github.com/spring-projects/spring-data-mongodb) - Spring Data’s mission is to provide a familiar and consistent, Spring-based programming model for data access while still retaining the special traits of the underlying data store. 
  * [Spring Data JPA](https://projects.spring.io/spring-boot/) - Takes an opinionated view of building production-ready Spring applications. Spring Boot favors convention over configuration and is designed to get you up and running as quickly as possible.
  * [Maven](https://maven.apache.org/) - Apache Maven is a software project management and comprehension tool. Based on the concept of a project object model (POM), Maven can manage a project's build, reporting and documentation from a central piece of information.
## Authors
* **Iryna Dmytriieva**
* **Anna Melnychuk**
* **Bohdan Hurov**
