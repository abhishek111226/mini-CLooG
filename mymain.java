import java.io.IOException;


public class mymain 
{
	static int iterTotal;
	static int iterCurrent;
	public static void main(String[] args) 
	{
		if(args.length==2)
		{
			iterTotal=Integer.parseInt(args[0]);
			iterCurrent=Integer.parseInt(args[1]);
		}
		fmlib fm = new fmlib();
		try {
			fm.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

