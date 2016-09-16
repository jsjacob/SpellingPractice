#!/bin/sh

##
## Uncomment the appropriate Java JRE/JDK
##

# IBM 1.3.1
JRE_HOME=/opt/IBMJava2-131/jre

# Sun 1.3.1
#JRE_HOME=/usr/local/jdk1.3.1_01/jre

# Sun 1.4.1
#JRE_HOME=/usr/java/j2sdk1.4.1/jre

##
## Path to Student configuration
##

CONFIG=/usr/local/SpellingPractice/spstudent.rc

##
## Path to Student JAR
##

JAR=/usr/local/SpellingPractice/SpellingPractice-Student.jar


##
## Run Student application
##

${JRE_HOME}/bin/java -jar ${JAR} ${CONFIG}

