/**
 * A class representing an Account.
 *
 * Missing feature: cannot handle overdrafts
 * Bug: balance should be arbitrary-precision, if user has more than about 20 million they'll overflow;
 *      these are customers that the bank will REALLY want to be keeping happy
 */
public class Account
{
	private int balance = 0;
	
	/**
	 * Basic constructor.
	 *
	 * @param initialBalance Initial account balance.
	 * @throws ATMOverdraftException You get to guess when this gets thrown.
	 */
	public Account ( int initialBalance ) throws ATMOverdraftException
	{
		if (initialBalance < 0)
			throw new ATMOverdraftException();
		balance = initialBalance;
	}
	
	/**
	 * Gives the current balance
	 *
	 * @return Current balance, shock horror.
	 */
	public int Query ()
	{
		return balance;
	}
	
	/**
	 * Deposit an amount.
	 *
	 * @param amount Guess. Go on.
	 * @throws ATMInvalidAmountException This happens if you pass <= 0 to amount. Don't do it.
	 */
	public void Deposit ( int amount ) throws ATMInvalidAmountException
	{
		if (amount <= 0)
		{
			throw new ATMInvalidAmountException();
		}
		balance += amount;
	}
	
	/**
	 * Withdraw... an amount.
	 *
	 * @param amount Number of moneys to withdraw.
	 * @throws ATMInvalidAmountException Trying to withdraw 0 or less will cause this.
	 * @throws ATMOverdraftException Whoops, tried to withdraw more than the current balance.
	 */
	public void Withdraw ( int amount ) throws ATMInvalidAmountException, ATMOverdraftException
	{
		if (amount <= 0)
		{
			throw new ATMInvalidAmountException();
		}
		if (amount > balance)
		{
			throw new ATMOverdraftException();
		}
		balance -= amount;
	}
}
