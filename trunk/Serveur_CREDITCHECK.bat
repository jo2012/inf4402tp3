@echo off
setlocal
::
:: INF4402 - TP3
::
@echo INF4402 - TP3

@echo execution du serveur de POLYEBAY...

javac ImplCreditCheck.java

java ImplCreditCheck -Djava.security.policy=security.policy

::pause