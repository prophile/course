import java.util.Hashtable;

/**
 * A TV connected to the DVD player
 */
public class TV
{
	private boolean state;
	private int channel;
	private Hashtable<Integer,TVPeripheral> connectedPeripherals;
		
	private void requireOn () throws TurnTheDamnThingOnFirstException
	{
		if (!state)
		{
			throw new TurnTheDamnThingOnFirstException(this);
		}
	}
	
	public TV ()
	{
		state = false;
		channel = 1;
		connectedPeripherals = new Hashtable<Integer,TVPeripheral>();
	}
	
	public boolean getState ()
	{
		return state;
	}
	
	public void setState ( boolean newState )
	{
		state = newState;
	}
	
	public int getChannel () throws TurnTheDamnThingOnFirstException
	{
		requireOn();
		return channel;
	}
	
	public void setChannel ( int newChannel ) throws TurnTheDamnThingOnFirstException
	{
		requireOn();
		channel = newChannel;
	}
	
	public void attachPeripheral ( TVPeripheral peripheral, int attachedChannel )
	{
		Integer basicKey = new Integer(attachedChannel);
		try
		{
			if (peripheral == null)
			{
				connectedPeripherals.remove(basicKey);
			}
			else
			{
				connectedPeripherals.put(basicKey, peripheral);
			}
		}
		catch (Exception except)
		{
			// silently failed: this must have mean they passed something silly in
			return;
		}
	}
	
	public void removePeripheral ( int attachedChannel )
	{
		attachPeripheral(null, attachedChannel);
	}
	
	public TVPeripheral getPeripheralForChannel ( int attachedChannel )
	{
		return connectedPeripherals.get(new Integer(attachedChannel));
	}
	
	public void display () throws TurnTheDamnThingOnFirstException
	{
		requireOn();
		System.out.println("You're watching channel: " + channel);
		TVPeripheral peripheral = getPeripheralForChannel(channel);
		if (peripheral != null)
		{
			System.out.println("Attached peripheral: " + peripheral.getClass().getName());
			System.out.println("\tShowing: " + peripheral.getDisplayedTitle());
		}
	}
}
