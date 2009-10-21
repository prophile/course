import java.util.Hashtable;

/**
 * A TV connected to the DVD player
 */
public class TV
{
	private boolean state;
	private int channel;
	private Hashtable<Integer,TVOutputStream> connectedStreams;
		
	private void requireOn () throws TurnTheDamnThingOnFirstException
	{
		if (!state)
		{
			throw new TurnTheDamnThingOnFirstException(this);
		}
	}
	
	/**
	 * Generic constructor
	 */
	public TV ()
	{
		state = false;
		channel = 1;
		connectedStreams = new Hashtable<Integer,TVOutputStream>();
	}
	
	/**
	 * Get power state.
	 *
	 * @return Whether the TV is on
	 */
	public boolean getState ()
	{
		return state;
	}
	
	/**
	 * Change power state.
	 *
	 * @param newState The new state of the system
	 */
	public void setState ( boolean newState )
	{
		state = newState;
	}
	
	/**
	 * Get the current channel
	 *
	 * @return The current channel number
	 */
	public int getChannel () throws TurnTheDamnThingOnFirstException
	{
		requireOn();
		return channel;
	}
	
	/**
	 * Set the current channel
	 *
	 * @param newChannel The new channel
	 */
	public void setChannel ( int newChannel ) throws TurnTheDamnThingOnFirstException
	{
		requireOn();
		channel = newChannel;
	}
	
	/**
	 * Attach a streams to a channel
	 *
	 * @param stream The stream object to attach
	 * @param attachedChannel The channel to which the stream will be attached
	 */
	public void attachStream ( TVOutputStream stream, int attachedChannel )
	{
		Integer basicKey = new Integer(attachedChannel);
		try
		{
			if (stream == null)
			{
				connectedStreams.remove(basicKey);
			}
			else
			{
				connectedStreams.put(basicKey, stream);
			}
		}
		catch (Exception except)
		{
			// silently failed: this must have mean they passed something silly in
			return;
		}
	}
	
	/**
	 * Remove the stream attached to a given channel
	 *
	 * @param attachedChannel The channel from which to remove the stream
	 */
	public void removeStream ( int attachedChannel )
	{
		attachStream(null, attachedChannel);
	}
	
	/**
	 * Get the attached output stream on a given channel
	 *
	 * @param attachedChannel The channel from which to fetch the output stream
	 * @return Either the attached stream or null if none.
	 */
	public TVOutputStream getOutputStreamForChannel ( int attachedChannel )
	{
		return connectedStreams.get(new Integer(attachedChannel));
	}
	
	/**
	 * Print current information to stdout
	 */
	public void display () throws TurnTheDamnThingOnFirstException
	{
		requireOn();
		System.out.println("You're watching channel: " + channel);
		TVOutputStream stream = getOutputStreamForChannel(channel);
		if (stream != null)
		{
			stream.display(this);
		}
		else
		{
			// no stream on the current channel, show nothing
			System.out.println("The screen is black.");
			System.out.println("You are eaten by a grue.");
		}
	}
}
