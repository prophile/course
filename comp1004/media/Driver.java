import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * Generic application driver
 */
public class Driver
{
	private static final String SERIALIZATION_FILE = "active.tv";
	
	private static TV readTVFromDisk ()
	{
		TV inputTV;
		try
		{
			// attempt to load the stored file
			FileInputStream fis = null;
			ObjectInputStream in = null;
			fis = new FileInputStream(Driver.SERIALIZATION_FILE);
			in = new ObjectInputStream(fis);
			inputTV = (TV)in.readObject();
			in.close();
			return inputTV;
		}
		catch (Exception except)
		{
			return null;
		}
	}
	
	private static TV createNewTV ()
	{
		return new TV();
	}
	
	private static void writeTVToDisk ( TV tv ) throws IOException
	{
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		fos = new FileOutputStream(Driver.SERIALIZATION_FILE);
		out = new ObjectOutputStream(fos);
		out.writeObject(tv);
		out.close();
	}
	
	/**
	 * Application entry point
	 *
	 * @param args Command-line arguments
	 */
	public static void main ( String[] args )
	{
		TV mainTV;
		Scanner inputScanner = new Scanner(System.in);
		Pattern whitespacePattern = Pattern.compile("\\p{Space}");
		inputScanner.useDelimiter(whitespacePattern);
		mainTV = readTVFromDisk();
		if (mainTV == null)
		{
			mainTV = createNewTV();
		}
		try
		{
			// do whatever action the user asks
			System.out.println("Enter your requested action");
			System.out.print("> ");
			String requestedAction = inputScanner.next();
			if (requestedAction.equals("channel"))
			{
				mainTV.setChannel(inputScanner.nextInt());
			}
			else if (requestedAction.equals("on"))
			{
				mainTV.setState(true);
			}
			else if (requestedAction.equals("off"))
			{
				mainTV.setState(false);
			}
			else if (requestedAction.equals("tune"))
			{
				int ch = inputScanner.nextInt();
				float freq = inputScanner.nextFloat();
				mainTV.attachStream(new TVTerrestrialChannel(freq), ch);
			}
			else if (requestedAction.equals("attach-dvd"))
			{
				int ch = inputScanner.nextInt();
				DVDPlayer player = new DVDPlayer();
				mainTV.attachStream(player, ch);
			}
			else if (requestedAction.equals("clear"))
			{
				mainTV.removeStream(inputScanner.nextInt());
			}
			else if (requestedAction.equals("dvd"))
			{
				int ch = inputScanner.nextInt();
				DVDPlayer player = (DVDPlayer)mainTV.getOutputStreamForChannel(ch);
				if (player == null)
				{
					System.out.println("No DVD player attached there.");
				}
				if (!inputScanner.hasNext())
				{
					System.out.print(">> ");
				}
				String subcommand = inputScanner.next();
				if (subcommand.equals("on"))
				{
					player.setState(true);
				}
				else if (subcommand.equals("off"))
				{
					player.setState(false);
				}
				else if (subcommand.equals("switch"))
				{
					player.insertDVD(new DVD(inputScanner.nextLine()));
				}
				else
				{
					System.out.println("Unknown command.");
				}
			}
			else
			{
				System.out.println("Unknown command (" + requestedAction + ")");
			}
			// actually display the TV
			mainTV.display();
			// store it
			writeTVToDisk(mainTV);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("rtfm");
		}
	}
}
