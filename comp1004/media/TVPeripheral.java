/**
 * A generic peripheral attached to a TV
 */
abstract public class TVPeripheral extends TVOutputStream
{
	/**
	 * Display current perihperal information.
	 *
	 * @param outputScreen The output device.
	 */
	public void display ( TV outputScreen )
	{
		System.out.println("Attached peripheral: " + getClass().getName());
		System.out.println("\tShowing: " + getDisplayedTitle());
	}

	/**
	 * Find the title to display on-screen currently
	 */
	abstract String getDisplayedTitle ();
}
