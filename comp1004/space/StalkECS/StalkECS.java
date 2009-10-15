/**
 * Main app class
 */
public class StalkECS
{
	private static void printHelp ()
	{
		System.out.println("Usage: StalkECS <names>");
	}
 
	public static void main ( String[] args )
	{
		int count = args.length;
		if (count == 0)
		{
			printHelp();
			return;
		}
		else
		for (int i = 0; i < count; i++)
		{
			String victim = args[i];
			ECSMember member = new ECSMember(victim);
			System.out.println(member.emailLine());
		}
	}
}
