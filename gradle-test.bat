SET JAVA_HOME=C:\Tools\Java\jdk1.8.0_172
SET PATH=C:\Tools\Java\gradle-4.8\bin;%PATH%
call gradle cleanTest test -p "%~dp0."
pause 10