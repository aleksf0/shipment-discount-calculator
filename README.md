# shipment-discount-calculator
This is the homework assignment, which description is available at [assignment.md](https://github.com/aleksf0/shipment-discount-calculator/blob/master/assignment.md).

* Launch Gradle build (__gradle-build.bat__):
  ```
  gradle clean getDeps build srcJar testJar testSrcJar -p "%~dp0." -i
  ```

* Launch Gradle test (__gradle-test.bat__):
  ```
  gradle cleanTest test -p "%~dp0."
  ```

* Run the application (__java-run-discount-module.bat__):
  ```
  java -jar build\libs\shipment-discount-calculator.jar input.txt
  ```

* Run the integration tests (__java-run-integration-test-suite.bat__):
  ```
  java -classpath .\lib\*;build\libs\* org.junit.runner.JUnitCore fr.vinted.shipment.test.IntegrationTestSuite
  ```

* Run the unit tests (__java-run-unit-test-suite.bat__):
  ```
  java -classpath .\lib\*;build\libs\* org.junit.runner.JUnitCore fr.vinted.shipment.test.UnitTestSuite
  ```