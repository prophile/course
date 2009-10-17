/**
 * You get to guess
 */
public class TurnTheDamnThingOnFirstException extends Exception
{
	private Object what;
	
	public TurnTheDamnThingOnFirstException ( Object theWhat )
	{
		what = theWhat;
	}
	
	public Object whatDoITurnOn ()
	{
		return what;
	}
}
