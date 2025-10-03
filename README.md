#API TESTS FOR SALARY LOANS

###use this command for execute test:

- environment:
  - stg
  - dev
```bash
- mvn clean verify -Denvironment=stg   # this to run in staging with maven
- .\gradlew clean test aggregate -Denvironment=stg # this to run in staging with Gradle
- .\gradlew clean test aggregate -Denvironment=stg -Dcucumber.filter.tags="@smoke and @fast"
```