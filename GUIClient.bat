@echo off
setlocal
::
:: INF4402 - TP3
::
@echo INF4402 - TP3

@echo execution du client de POLYEBAY...

javac GUIClient.java

java GUIClient -Djava.security.policy=security.policy

::pause