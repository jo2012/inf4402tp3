@echo off
setlocal
::
:: INF4402 - TP2 
::
@echo INF4402 - TP2

@echo Compilation des fichiers java...
javac -nowarn -d ./class *.java

@echo Compilation des idl rmi...
rmic -nowarn -d ./class -classpath ./class ClientImpl
rmic -nowarn -d ./class -classpath ./class PolyPaypalImpl
rmic -nowarn -d ./class -classpath ./class PolyEbayImpl
rmic -nowarn -d ./class -classpath ./class CreditCheckImpl

   
@echo Compilation terminee!
:: pause