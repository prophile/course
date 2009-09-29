JAVACFLAGS=-g

all: HelloWorldApp.class BasicIOApp.class StripWSApp.class
	

StripWSApp.class: StripWSApp.java
	javac $(JAVACFLAGS) $<

HelloWorldApp.class: HelloWorldApp.java
	javac $(JAVACFLAGS) $<

BasicIOApp.class: BasicIOApp.java
	javac $(JAVACFLAGS) $<

clean:
	rm -f *.class
