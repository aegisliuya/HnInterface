@echo on
title SimilarAnalyser
set   CURRENT_DIR=%cd%
echo %~dp0

set   JAVA_HOME="d:\Program Files\Java\jre6"
set   LIB_DIR=%CURRENT_DIR%\WEB-INF\lib
set   CLASS_DIR=%CURRENT_DIR%\WEB-INF\classes

java -Djava.ext.dirs=%LIB_DIR% -classpath %CLASS_DIR%  com.analyze.StartSimilarAnalyzer
@pause