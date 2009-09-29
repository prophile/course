import java.io.*;

// perhaps a terribly named class :)
class Stripper
{
	public boolean IsWhitespace ( byte ch )
	{
		return (ch == ' ' ||
		        ch == '\t' ||
		        ch == '\n' ||
		        ch == '\r');
	}
	
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

public class StripWSApp
{
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
