import java.util.regex.*;
import java.io.*;
import java.net.URL;
import java.net.MalformedURLException;
 
/**
 * A class representing a single member of ECS.
 */
public class ECSMember
{
	private String emailID;
	private String realName;
 
	private URL realNameURL ()
	{
		try
		{
			return new URL("http://www.ecs.soton.ac.uk/people/" + emailID);
		}
		catch (MalformedURLException exception)
		{
			return null;
		}
	}
 
	private String deriveRealNameFromHTML ( String htmlData )
	{
		Pattern titlePattern = Pattern.compile("<title>(.*?)</title>");
		Matcher matcher = titlePattern.matcher(htmlData);
		if (!matcher.matches())
		{
			return "ERROR: title didn't match regex (" + htmlData + ")";
		}
		return matcher.group(1);
	}
 
	// should find a better way of reporting errors than setting realName to "ERROR"
	private void fetchRealName ()
	{
		URL url = realNameURL();
		if (url == null)
		{
			realName = "ERROR: bad URL";
		}
		// fetch the URL
		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			// discard five lines: this is UGLY but it works as a temporary hac
			for (int i = 0; i < 5; i++)
			{
				reader.readLine();
			}
			realName = deriveRealNameFromHTML(reader.readLine());
		}
		catch (IOException exception)
		{
			realName = "ERROR: " + exception.getMessage();
		}
	}
 
	public ECSMember ( String id )
	{
		emailID = id;
		realName = null;		
	}
 
	public String emailID ()
	{
		return emailID;
	}
 
	public String emailAddress ()
	{
		return emailID + "@ecs.soton.ac.uk";
	}
 
	public String realName ()
	{
		if (realName == null)
			fetchRealName();
		return realName;
	}
 
	public String emailLine ()
	{
		return realName() + " <" + emailAddress() + ">";
	}
}
