import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class ReadFromFile 
{
	List<String> str;

	public ReadFromFile(String filename) throws FileNotFoundException
	{
		List<String> st = new ArrayList<String>();
		String line;
		File file = new File(filename);
		Scanner sc;	    
		//Scanner in = new Scanner(new FileReader(filename));
		// check whether file exists or not
		if(file.exists())
		{     
			sc = new Scanner(file);

			// read file line-by-line, parse into words and put into array list of strings
			while (sc.hasNextLine())				
			{
				line = sc.nextLine();
				st.add(line);
			}
			sc.close();
		}
		else
		{
			JOptionPane.showMessageDialog(null,"File not found.");
		}

		this.str = st;
	} // end of readFromFile
}
