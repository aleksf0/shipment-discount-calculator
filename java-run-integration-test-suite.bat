SET PATH=C:\Tools\Java\jdk1.8.0_172\bin;%PATH%
cd "%~dp0"
java -classpath .\lib\*;build\libs\* org.junit.runner.JUnitCore fr.vinted.shipment.test.IntegrationTestSuite
pause 10