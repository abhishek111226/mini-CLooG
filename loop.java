import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;




public class loop {
		String l="max(";
		String u="min(";
		String var;
		loop(int i)
		{
			var="t"+i;
		}
		void extendConstraint(String l,String u,boolean flag)
		{
			if(l!="")
				this.l+=l+ ((flag == true) ? "," : " ");
			if (u!="")
				this.u+=u+((flag == true) ? "," : " ");
		}
		void printloop()
		{
	//		System.out.println(" for ( "+var+"="+l+") ;"+var+"<="+u+") ; "+var+"++"+")");
		}
		void writelooptoFile() throws IOException 
		{
			File output = new File("/home/abhishek/Desktop/minicloog/loopnest.out");
			if(!output.exists()) {
			    output.createNewFile();
			} 
			String originalFile = "" ;
			BufferedReader br = new BufferedReader(new FileReader(output));
			String line="";
			while((line=br.readLine())!=null)
			{
				originalFile+=line;
			}
			if(originalFile.length()==0)
			{
				originalFile="";
			}

			String newLoop;
			if(mymain.iterCurrent==1)
				newLoop="\n"+"for ( "+var+"="+l+") ;"+var+"<="+u+") ; "+var+"++"+")";
			else
				newLoop="for ( "+var+"="+l+") ;"+var+"<="+u+") ; "+var+"++"+")";

			BufferedWriter bw = new BufferedWriter(new FileWriter("/home/abhishek/Desktop/minicloog/loopnest.out"));
			if(mymain.iterCurrent==mymain.iterTotal)
			{
				newLoop+="\n"+originalFile;
				String[] split = newLoop.split("\n");
				for (int i = 0; i < split.length; i++) 
				{
					for (int j = 0; j < i; j++) 
					{
						bw.write("\t");
					}
					bw.write(split[i]);
					bw.write("\n");
				}
			}
			else
			{
				bw.write(newLoop);
				bw.write("\n");
				bw.write(originalFile);
			}
			bw.close();
		}
}
