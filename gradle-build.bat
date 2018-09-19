SET JAVA_HOME=C:\Tools\Java\jdk1.8.0_172
SET PATH=C:\Tools\Java\gradle-4.8\bin;%PATH%
call gradle clean getDeps build srcJar testJar testSrcJar -p "%~dp0." -i
pause 10