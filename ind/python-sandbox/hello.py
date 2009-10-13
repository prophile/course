class helloWorldPrinter(object):
	def __init__(self, message = "Hello, world!"):
		self.message = message
	
	def setMessage(self, newMessage):
		self.message = newMessage
	
	def printHello (self):
		print self.message

myPrinter = helloWorldPrinter()
myPrinter.printHello()
