## TSWGROUP TEAM ##
## INF4402 - TP2 ##

out = class
cur_dir = .
idl_pay = PolyPaypalImpl
idl_ebay = PolyEbayImpl
idl_cli = ClientImpl
idl_cre = CreditCheckImpl

all:
	javac -nowarn -d $(out) $(cur_dir)/*.java
	rmic -nowarn -d $(out) -classpath $(out) $(idl_cli)
	rmic -nowarn -d $(out) -classpath $(out) $(idl_pay)
	rmic -nowarn -d $(out) -classpath $(out) $(idl_ebay)
	rmic -nowarn -d $(out) -classpath $(out) $(idl_cre)

clean:
	rm -f $(out)/*.class

clear:
	rm -f $(out)/*.class
  