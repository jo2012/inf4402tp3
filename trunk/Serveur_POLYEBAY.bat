@echo off
setlocal
::
:: INF4402 - TP3
::
@echo INF4402 - TP3

@echo execution du serveur POLYEBAY...

javac ImplPolyEbay.java

java ImplPolyEbay -Djava.security.policy=security.policy

::pause