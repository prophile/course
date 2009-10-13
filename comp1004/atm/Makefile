all: ATMApplication.class Account.class ATMOverdraftException.class ATMInvalidAmountException.class

clean:
	rm -f *.class

ATMApplication.class: ATMApplication.java
	javac $^

Account.class: Account.java
	javac $^

ATMOverdraftException.class: ATMOverdraftException.java
	javac $^

ATMInvalidAmountException.class: ATMInvalidAmountException.java
	javac $^
