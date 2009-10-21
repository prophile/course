import java.io.Serializable;

/**
 * A stream, attached to a given channel, of output.
 */
abstract class TVOutputStream implements Serializable
{
	/**
	 * Display current channel information.
	 *
	 * @param outputScreen The output device.
	 */
	abstract public void display ( TV outputScreen );
}
