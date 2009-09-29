JAVACFLAGS=

all: HelloWorldApp.class BasicIOApp.class
	

HelloWorldApp.class: HelloWorldApp.java
	javac $(JAVACFLAGS) $<

BasicIOApp.class: BasicIOApp.java
	javac $(JAVACFLAGS) $<

clean:
	rm -f *.class
