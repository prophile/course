import java.util.Scanner;
import java.util.InputMismatchException;
import java.io.IOException;

/**
 * The main application class
 */
public class ATMApplication
{
	private Account account;
	
	/**
	 * Basic constructor: prompts user for inital balance
	 */
	public ATMApplication ()
	{
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter initial balance: ");
		while (account == null)
		{
			try
			{
				int initialBalance = scanner.nextInt();
				account = new Account(initialBalance);
				break;
			}
			catch (ATMOverdraftException exception)
			{
				System.out.print("No, that wasn't valid, try again: ");
			}
			catch (InputMismatchException exception)
			{
				System.out.println("It would appear we have conflicting definitions for 'integer'");
			}
		}
	}
	
	/**
	 * Runs the main application loop.
	 */
	public void MainLoop ()
	{
		Scanner scanner = new Scanner(System.in);
		boolean exitMainLoop = false;
		while (!exitMainLoop)
		{
			try
			{
				System.out.println("Please select your action:");
				System.out.println("\t1  Deposit");
				System.out.println("\t2  Withdraw");
				System.out.println("\t3  Balance Enquiry");
				System.out.println("\t4  Quit");
				int choice = scanner.nextInt(), amount;
				switch (choice)
				{
					case 1:
						System.out.print("Enter the amount to deposit: ");
						amount = scanner.nextInt();
						account.Deposit(amount);
						System.out.println("Your wish is my command.");
						break;
					case 2:
						System.out.print("Enter the amount to withdraw: ");
						amount = scanner.nextInt();
						account.Withdraw(amount);
						System.out.println("So be it.");
						break;
					case 3:
						System.out.println("Your balance is: " + account.Query());
						break;
					case 4:
						System.out.println("bai~~");
						exitMainLoop = true;
						break;
					default:
						//System.out.println("No, that's not an integer 1 and 4 you silly bastard");
						System.out.println("Please enter an integer between 1 and 4");
						break;
				}
			}
			catch (ATMOverdraftException exception)
			{
				System.out.println("The system does not currently support overdrafts.");
			}
			catch (ATMInvalidAmountException exception)
			{
				System.out.println("You can only withdraw and deposit positive amounts.");
			}
			catch (InputMismatchException exception)
			{
				System.out.println("It would appear we have conflicting definitions for 'integer'");
			}
		}
	}
	
	/**
	 * Main app entry point.
	 *
	 * @param args The arguments to casually ignore
	 */
	public static void main ( String[] args )
	{
		ATMApplication app;
		app = new ATMApplication();
		app.MainLoop();
	}
}
