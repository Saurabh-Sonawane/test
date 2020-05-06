# Requirement
1. Java 8
2. Gradle 4.10 or Above (If you don't have Gradle set up you can use Gradlew available in repository)

# How to build
    1. Using Gradle 4.10 or Above

        gradle clean build

    2. Using Gradlew

        ./gradlew clean build

    Above task will create jar file in build/lib folder.

# Unit test reports
    The above command will run all unit tests, you can see the unit test reports at build/reports/tests/test/index.html

# How to run
    java -jar build/libs/Clim8-1.0-SNAPSHOT.jar
    
    This will launch tomcat on port 8080, then you can use mentioned API
    
# API Details
    This service generates Swagger API as well you can view Swagger API documentation 
    http://localhost:8080/swagger-ui.html
    
# Testing the API
    Postman collection is available in directory named postman collection, you can import that and test API