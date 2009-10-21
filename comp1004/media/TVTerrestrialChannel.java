/**
 * A terrestrial channel as built in to the TV.
 */
public class TVTerrestrialChannel extends TVOutputStream
{
	private float frequency;
	
	/**
	 * Basic constructor.
	 *
	 * @param newFrequency The channel frequency
	 */
	public TVTerrestrialChannel ( float newFrequency )
	{
		frequency = newFrequency;
	}
	
	/**
	 * Get the frequency of the channel
	 */
	public float getFrequency ()
	{
		return frequency;
	}
	
	/**
	 * Set the frequency of the channel
	 */
	public void setFrequency ( float newFrequency )
	{
		frequency = newFrequency;
	}
	
	/**
	 * Display to the output screen
	 */
	public void display ( TV outputScreen )
	{
		System.out.println("Terrestrial channel at frequency: " + frequency);
	}
}
