REM ##
REM ## Uncomment the appropriate Java JRE/JDK
REM ##

REM # Sun 1.4.1
REM set JRE_HOME="C:\Program Files\Java\j2re1.4.1"

REM # IBM 1.3.1
set JRE_HOME="C:\WSDK\sdk\jre"

REM ##
REM ## Path to Student configuration
REM ##

set CONFIG="C:\Program Files\SpellingPractice\spstudent.rc"

REM ##
REM ## Path to Student JAR
REM ##

set JAR="C:\Program Files\SpellingPractice\SpellingPractice-Student.jar"


REM ##
REM ## Run Student application
REM ##

%JRE_HOME%\bin\java -jar %JAR% %CONFIG%

