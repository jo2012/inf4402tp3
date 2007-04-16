@echo off
setlocal
::
:: INF4402 - TP3
::
@echo INF4402 - TP3

@echo execution du Client...

javac GUIClient.java

java GUIClient -Djava.security.policy=security.policy

::pause