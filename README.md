#  Reward Micro service

## Required Versions:
JDK 11

Gradle 6.9+

## To Build
`bash
run ./gradlew clean build
`

## Initial data load
- control initial data load with property key `data.load.enabled = true`
- Data loader service load data from
  - ./resources/input/users.txt (`,` separated fields with new line separated records)
    - format `userId,userName` (please make sure no duplicate userIds present in the file)
  - ./resources/input/transactions.txt (`,` separated fields with new line separated records)
    - format `userId,amount,date` (please make sure all userIds present in the users.txt)

> Note: Error in data loader service is fatal

## local env usage
- After updating data load file you can run local server by running following command
`bash
./gradlew booRun
`
- Open Swagger UI to see available End points (in browser)

http://localhost:8080/swagger-ui/index.html

- H2 DB access
  - Open link `http://localhost:8082/h2-console` in browser
  - provide following details
    - JDBC URL: jdbc:h2:mem:db
    - user: sa
    - password: sa
- View metrics
  - open link `http://localhost:8080/actuator` in browser



