##### INF4402 - TP2 Peer-To-Peer Poly ##### 

all:
	javac -nowarn *.java
	
clean:
	rm -f *.class
	
run_polyebay:
	java -Djava.security.policy=policy ImplPolyEbay localhost
    
run_polypaypal:
	java -Djava.security.policy=policy ImplPolyPaypal  localhost 
	
run_creditcheck:
	java -Djava.security.policy=policy ImplCreditCheck
	
run_client:
	java -Djava.security.policy=policy GUIClient
