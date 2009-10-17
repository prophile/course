/**
 * A representation of a DVD
 */
public class DVD
{
	private String title;
	
	/**
	 * Basic constructor.
	 *
	 * @param aTitle The title of the DVD.
	 */
	public DVD ( String aTitle )
	{
		title = aTitle;
	}
	
	/**
	 * Fetch the title.
	 *
	 * @return The DVD title.
	 */
	public String getTitle ()
	{
		return title;
	}
	
	/**
	 * Change to a new title.
	 *
	 * @param aTitle The new title.
	 */
	public void setTitle ( String aTitle )
	{
		title = aTitle;
	}
}
