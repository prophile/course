public class Driver
{
	public static void main ( String[] args )
	{
		try
		{
			TV myTV = new TV();
			DVDPlayer myDVDPlayer = new DVDPlayer();
			myTV.attachPeripheral(myDVDPlayer, 8);
			myTV.setState(true);
			myDVDPlayer.setState(true);
			myTV.setChannel(8);
			myTV.display();
		}
		catch (Exception e)
		{
			System.out.println("rtfm");
		}
	}
}
