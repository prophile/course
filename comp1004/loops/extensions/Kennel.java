public class Kennel
{
	private Dog[] dogs;
	
	public Kennel ()
	{
		dogs = new Dog[5];
		for (int i = 0; i < 5; i++)
		{
			dogs[i] = new Dog("Dog" + i, i + 1);
		}
	}
	
	public void talk ()
	{
		//dogs[3].bark();
		/*for (int i = 0; i < 5; i++)
		{
			System.out.printf("%2d ", i);
			dogs[i].bark();
		}*/
		/*int i = 0;
		while (i < 5)
		{
			System.out.printf("%2d %s ", i, dogs[i].getName());
			dogs[i].bark();
			i++;
		}*/
		for (Dog dog : dogs)
		{
			dog.bark();
		}
	}
	
	public static void main ( String[] args )
	{
		Kennel kennel = new Kennel();
		kennel.talk();
	}
}
