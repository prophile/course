import java.util.Scanner;

public class BasicIOApp
{
	public static int BEA ( int n1, int n2 )
	{
		// basic (non-extended) euclidean algorithm
		while (n2 != 0)
		{
			int t = n2;
			n2 = n1 % n2;
			n1 = t;
		}
		return n1;
	}
	
	public static void main ( String[] args )
	{
		int n1, n2;
		Scanner in = new Scanner(System.in);
		
		// grab two ints
		System.out.print("n1: ");
		// hopefully this will block
		n1 = in.nextInt();
		System.out.print("n2: ");
		n2 = in.nextInt();
		
		int gcd = BasicIOApp.BEA(n1, n2);
		System.out.println("GCD: " + gcd);
	}
}
