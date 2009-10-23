public class Dog
{
	private int size;
	private String name;
	
	public Dog ( String aName, int aSize )
	{
		size = aSize;
		name = aName;
	}
	
	private String barkNoise ()
	{
		switch (size)
		{
			case 1:
				return "yip yip";
			case 2:
				return "arf arf";
			case 3:
				return "bark bark";
			case 4:
				return "woof";
			case 5:
				return "ruff";
			default:
				return "moo";
		}
	}
	
	public void bark ()
	{
		System.out.println(barkNoise());
	}
	
	public String getName ()
	{
		return name;
	}
}
