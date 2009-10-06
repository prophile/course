all: ATMApplication.class ATM.class ATMOverdraftException.class ATMInvalidAmountException.class

clean:
	rm -f *.class

ATMApplication.class: ATMApplication.java
	javac $^

ATM.class: ATM.java
	javac $^

ATMOverdraftException.class: ATMOverdraftException.java
	javac $^

ATMInvalidAmountException.class: ATMInvalidAmountException.java
	javac $^
