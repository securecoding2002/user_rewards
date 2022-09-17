#  Reward Micro service

## Required Versions:
JDK 11

Gradle 6.9+

## Development Setup (Mac)
- install homebrew

```bash
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
```

- Install git

```bash
brew install git
```

- Clone assignment git repo

```bash
  git clone git@github.com:securecoding2002/user_rewards.git
```

- To Build

```bash
./gradlew clean build
```

- To run local service

```bash
./gradlew bootRun
```

- To format java code (uses google java format)
```bash
./gradlew goJF
```

- use swagger UI to use APIs
  [http://localhost:8080/swagger-ui/index.html]

## Initial data load
- control initial data load with property key `data.load.enabled = true`
- Data loader service load data from
  - ./resources/input/users.txt (`,` separated fields with new line separated records)
    - format `userId,userName` (please make sure no duplicate userIds present in the file)
  - ./resources/input/transactions.txt (`,` separated fields with new line separated records)
    - format `userId,amount,date` (please make sure all userIds present in the users.txt)

> Note: Error in data loader service is fatal

## additional details
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



