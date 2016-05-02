import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFileChooser;


public class fmlib {

	/**
	 * @param args
	 */
	int rows;
	int cols;
	double[][] matrix;
	FileWriter bw = null;
	Integer Nrows=0;
	Integer Ncols=0;
	String outfile="";
	
	void read() throws IOException
	{
		
		try {
			bw = new FileWriter(new File("/home/abhishek/Desktop/minicloog/output_polylib.in"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
        BufferedReader br = null;
        br = new BufferedReader(new FileReader(new File("/home/abhishek/Desktop/minicloog/output_fmlib.out") ));
        String line = "";
	
	while(line.equals("\n") || line.equals(""))
	        line = br.readLine();
	
    	String split[] =  line.split("\\s+");
    	rows = Integer.parseInt(split[0]);
    	cols = Integer.parseInt(split[1]);
        matrix = new double[rows][cols];
        int cnt=0;
        while((line = br.readLine()) != null)
        {
        	split =  line.split("\\s+");
        	for (int i = 0; i < split.length; i++)
        	{
        		matrix[cnt][i]=Integer.parseInt(split[i]);
        	}
        	cnt++;
        }
        br.close();
/*        System.out.println(" Matix is : ");
        for (int f = 0; f < matrix.length; f++) {
			for (int i = 0; i < matrix[0].length; i++) {
				System.out.print("\t"+matrix[f][i]);
			}
			System.out.println("\n");
		}
  */      normalize();
        Ncols=cols-1;
        
        for (int i = 0; i < matrix.length; i++) 
        {
        	if(matrix[i][1] == 0)
        	{
        		outfile+="1 ";
        		for (int k = 2; k < matrix[0].length; k++) 
        		{
       					outfile+=matrix[i][k];
       					outfile+=" ";
        		}	
        		outfile+="\n";
        		Nrows++;
        	}
        }

        for (int i = 0; i < matrix.length; i++) 
        {
			for (int j = 0; j < matrix.length; j++) 
			{
				if(i!=j && ((matrix[i][1]>0 && matrix[j][1]<0) || (matrix[i][1]<0 && matrix[j][1]>0)))
				{
					twoInequalitiesToOne(i,j);
				}
			}
		}
        
        bw.write(Nrows.toString()+" "+Ncols.toString()+"\n"+outfile);
        bw.close();
        generateloop();
	}
	
	void normalize()
	{
		for (int i = 0; i < matrix.length; i++) 
		{
			double factor=1;
			if(matrix[i][1] != 0)
			{
				factor=1/matrix[i][1];
				factor=Math.abs(factor);		
				for (int j = 1; j < matrix[0].length; j++) 
				{
					matrix[i][j]*=factor;
				}
			}
		}
	}
	
	void twoInequalitiesToOne(int i,int j)
	{
		/*
		 *  Positive A'x'+aX >=0
		 *  Negative A''x'-aX >=0
		 * 
		 *  Output
		 *  divide by coeff of X
		 *  A''x' >= ax >= -A'x'
		 *  A''x' >= -A'x'
		 *  x'(A''+A') > = 0
		 *   
		 */
		
		
		Nrows++;
		outfile+="1 ";
		for (int k = 2; k < matrix[0].length; k++) 
		{
			if(k!=1)
			{
	//				System.out.print(matrix[i][k]+matrix[j][k]+" ");
					outfile+=String.valueOf(new Double((matrix[i][k]+matrix[j][k])).intValue());
					outfile+=" ";
			}
		}	
	//	System.out.println("\n");
		outfile+="\n";
	}
	
	void generateloop()
	{
		Double ub=Double.MAX_VALUE;
		Double lb=(double) -1000000000;
		loop l = new loop(mymain.iterCurrent);
		
		for (int i = 0; i < matrix.length; i++) 
		{
			String upperexpr="";
			String lowerexpr="";
			if(matrix[i][1]==1)    //+ve constraint
			{
				int j;
				for (j = 2; j < matrix[0].length - 1; j++) 
				{
					if(matrix[i][j]!=0)
					{
						break;
					}
				}
				if(j==matrix[0].length-1)
				{
					lb = Double.max(lb, matrix[i][cols-1]);
				}
				else
				{
					String expression="";
					for (j = 2; j < matrix[0].length; j++) 
					{
						int temp= ((int)matrix[i][j]*-1);
						if(temp>0)
						{
							expression+=" "+temp+"*"+"t"+(mymain.iterCurrent+j-1)+((j!=matrix[0].length) ?"+":" ");	
						}
						else if(temp<0)
						{
							expression+=" "+temp+"*"+"t"+(mymain.iterCurrent+j-1);						
					
						}
					}
					lowerexpr+=expression;
				}
			}
			else if(matrix[i][1]==-1) // -ve constraint
			{
				int j;
				for (j = 2; j < matrix[0].length - 1; j++) 
				{
					if(matrix[i][j]!=0)
					{
						break;
					}
				}
				if(j==matrix[0].length-1)
				{
					ub = Double.min(ub, matrix[i][cols-1]);
				}
				else
				{
					String expression="";
					for (j = 2; j < matrix[0].length; j++) 
					{
						int temp= ((int)matrix[i][j]*-1);
						if(temp>0)
						{
							expression+=" "+temp+"*"+"t"+(mymain.iterCurrent+j-1)+((j!=matrix[0].length) ?"+":" ");	
						}
						else if(temp<0)
						{
							expression+=" "+temp+"*"+"t"+(mymain.iterCurrent+j-1);						
					
						}
					}
					upperexpr+=expression;
				}
			}
			l.extendConstraint(lowerexpr,upperexpr,true);
		}
		
		l.extendConstraint(new Integer(lb.intValue()).toString(),new Integer(ub.intValue()).toString(),false);
		l.printloop();
		try {
			l.writelooptoFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("loops "+loops);
		
	}
	

}
