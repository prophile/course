import java.io.Serializable;

/**
 * A DVD player.
 */
public class DVDPlayer extends TVPeripheral implements Serializable
{
	private boolean state;
	private DVD currentDVD;
	
	private void requireOn () throws TurnTheDamnThingOnFirstException
	{
		if (!state)
		{
			throw new TurnTheDamnThingOnFirstException(this);
		}
	}
	
	/**
	 * Basic constructor.
	 */
	public DVDPlayer ()
	{
		state = false;
	}
	
	/**
	 * Get current state.
	 *
	 * @return Whether the player is on.
	 */
	public boolean getState ()
	{
		return state;
	}
	
	/**
	 * Set current state
	 */
	public void setState ( boolean newState )
	{
		state = newState;
	}
	
	/**
	 * Get the current DVD.
	 */
	public DVD getCurrentDVD () throws TurnTheDamnThingOnFirstException
	{
		requireOn();
		return currentDVD;
	}
	
	/**
	 * Set the new current DVD.
	 */
	public void insertDVD ( DVD newDVD ) throws TurnTheDamnThingOnFirstException
	{
		requireOn();
		currentDVD = newDVD;
	}
	
	/**
	 * Get the DVD title
	 */
	public String getDisplayedTitle ()
	{
		if (currentDVD == null)
		{
			return "NO DVD";
		}
		else
		{
			return currentDVD.getTitle();
		}
	}
}
