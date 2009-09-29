import java.io.*;

// perhaps a terribly named class :)
/**
 * A class to strip whitespace characters.
 *
 * The main interface here is the StripWS method.
 */
class Stripper
{
	/**
	 * Determines if a particular character is whitespace.
	 *
	 * This assumes ASCII input and will check if the character is:
	 *   space
	 *   tab
	 *   line feed
	 *   carriage return
	 * There is a probably a better way to do this without assuming ASCII.
	 *
	 * @param ch The ASCII character to check
	 * @return Whether or not this is considered whitespace
	 */
	private boolean IsWhitespace ( byte ch )
	{
		return (ch == ' ' ||
		        ch == '\t' ||
		        ch == '\n' ||
		        ch == '\r');
	}

	/**
	 * Pass the input stream to the output stream devoid of extra whitespace
	 *
	 * This function does three things:
	 *   strips leading whitespace
	 *   strips trailing whitespace
	 *   reduces all whitespace to single spaces
	 *
	 * It's essentially just a finite state machine with two states: "reading
	 * whitespace" and "not reading whitespace." UTSL for more information.
	 *
	 * @param inStream Incoming file stream.
	 * @param outStream Outgoing file stream.
	 */
	public void StripWS ( InputStream inStream, OutputStream outStream )
	{
		// essentially a FSM
		boolean hitWhitespace = false, sentCharacters = false;
		byte[] inputBuffer = new byte[1];
		byte[] spaceBuffer = new byte[1];
		spaceBuffer[0] = 0x20; // space ASCII code
		try
		{
			while (inStream.available() > 0)
			{
				inStream.read(inputBuffer);
				boolean isWhitespace = IsWhitespace(inputBuffer[0]);

				// this depends on case
				if (isWhitespace && hitWhitespace)
				{
					// we've already hit a whitespace character, do nothing
				}
				else if (isWhitespace && !hitWhitespace)
				{
					// this is us hitting a whitespace character
					hitWhitespace = true;
				}
				else if (!isWhitespace && hitWhitespace)
				{
					// we return to normal characters
					// if we haven't emitted any characters yet, then this is beginning of the file
					// if we have, it's not and we emit a space
					if (sentCharacters)
						outStream.write(spaceBuffer);
					outStream.write(inputBuffer);
					hitWhitespace = false;
					sentCharacters = true;
				}
				else
				{
					// ordinary character
					outStream.write(inputBuffer);
					sentCharacters = true;
				}
			}
		}
		catch (IOException except)
		{
			System.err.println("IO error: " + except);
		}
	}
}

/**
 * Main whitespace stripper app.
 */
public class StripWSApp
{
	/**
	 * Guess what... it's the main function!
	 *
	 * This should probably not throw an exception.
	 *
	 * ...but I'm lazy?
	 *
	 * @param args Program arguments. Pass - for stdin.
	 */
	public static void main ( String[] args ) throws IOException
	{
		if (args.length == 0)
		{
			System.err.println("Usage: stripws <filename>");
		}
		else
		{
			String path = args[0];
			InputStream fp;
			boolean closeFP;
			if (path == "-")
			{
				fp = System.in;
				closeFP = false;
			}
			else
			{
				fp = new FileInputStream(path);
				closeFP = true;
			}
			Stripper strip = new Stripper();
			strip.StripWS(fp, System.out);
			if (closeFP)
			{
				fp.close();
			}
		}
	}
}
