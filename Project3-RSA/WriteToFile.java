import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.swing.JOptionPane;


public class WriteToFile 
{
	
	public WriteToFile(List<String> str, String filename) throws FileNotFoundException
	{
		
		File f = new File(filename);
		FileWriter fWriter;
		PrintWriter pWriter;
		
		try
		{
			fWriter = new FileWriter (f);
			pWriter = new PrintWriter (fWriter);

			for (int i = 0; i < str.size(); i++)
			{
				System.out.println(String.format("char = %s",str.get(i)));
				pWriter.println(str.get(i));
				
			}
			pWriter.close();
			fWriter.close();
			
			JOptionPane.showMessageDialog(null,"Write to file successful.");
		}
		catch(IOException e)
		{
			JOptionPane.showMessageDialog(null,"File not found.");
		}
		
	}
}
