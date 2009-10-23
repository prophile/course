import java.util.Scanner;

class Tables
{
	public static void main ( String[] args )
	{
		String multipleString;
		int multiple;
		if (args.length > 0)
		{
			multipleString = args[0];
		}
		else
		{
			Scanner scanner = new Scanner(System.in);
			System.out.print("> Enter value: ");
			multipleString = scanner.next();
		}
		try
		{
			multiple = (new Integer(multipleString)).intValue();
		}
		catch (Exception e)
		{
			System.out.println("That wasn't an integer, assuming 4.");
			multiple = 4;
		}
		for (int b = 1; b <= 20; b++)
		{
			System.out.printf("%2d x %2d = %3d\n", multiple, b, multiple * b);
		}
	}
}
