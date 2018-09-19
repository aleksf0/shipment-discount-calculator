# shipment-discount-calculator
This is the homework assignment, which description is available at [assignment.md](https://github.com/aleksf0/shipment-discount-calculator/blob/master/assignment.md).

* Launch Gradle build:
  ```
  gradle clean getDeps build srcJar testJar testSrcJar -p "%~dp0." -i
  ```
  __gradle-build.bat__

* Launch Gradle test:
  ```
  gradle cleanTest test -p "%~dp0."
  ```
  __gradle-test.bat__

* Run the application:
  ```
  java -jar build\libs\shipment-discount-calculator.jar input.txt
  ```
  __java-run-discount-module.bat__

* Run the integration tests:
  ```
  java -classpath .\lib\*;build\libs\* org.junit.runner.JUnitCore fr.vinted.shipment.test.IntegrationTestSuite
  ```
  __java-run-integration-test-suite.bat__

* Run the unit tests:
  ```
  java -classpath .\lib\*;build\libs\* org.junit.runner.JUnitCore fr.vinted.shipment.test.UnitTestSuite
  ```
  __java-run-unit-test-suite.bat__
