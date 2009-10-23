public class Sum
{
	public static void main ( String[] args )
	{
		int total = 0;
		for (int i = 1; true; i++)
		{
			total += i;
			if (total > 50000000)
			{
				System.out.printf("Reached %d on iteration %d\n", total, i);
				return;
			}
		}
	}
}
