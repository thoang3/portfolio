/*	Class to hold key, either public or private.
 * 
 */

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


public class Key 
{
	public BigInt e_d_val; // e value for public key, d value for private key
	public BigInt nVal;    // n value for both public and private key

	// default constructor
	public Key()
	{
		
	}
	
	// initialize constructor with two big integers as input
	public Key(BigInt a, BigInt b)
	{
		e_d_val = a;
		nVal = b;
	}

	// get e or d value
	public BigInt get_e_d_val()
	{
		return e_d_val;
	}
	
	// get n value
	public BigInt get_nVal()
	{
		return nVal;
	}
	
	// get pair of values of either public key or private key
	public List<BigInt> getKey()
	{
		List<BigInt> array = new ArrayList<BigInt>(2);
		array.add(e_d_val);
		array.add(nVal);

		return array;
	}

	// read public key from a local file, with filename given
	public void readPublicKeyFromFile(String filename) throws FileNotFoundException
	{
		ReadFromFile read = new ReadFromFile(filename);

		int startPosition = read.str.get(1).indexOf("<evalue>") + "<evalue>".length();
		int endPosition = read.str.get(1).indexOf("</evalue>", startPosition);
		this.e_d_val = new BigInt(read.str.get(1).substring(startPosition, endPosition));

		startPosition = read.str.get(2).indexOf("<nvalue>") + "<nvalue>".length();
		endPosition = read.str.get(2).indexOf("</nvalue>", startPosition);
		this.nVal = new BigInt(read.str.get(2).substring(startPosition, endPosition)); 
	}

	// read private key form a local file, with filename given
	public void readPrivateKeyFromFile(String filename) throws FileNotFoundException
	{
		ReadFromFile read = new ReadFromFile(filename);

		int startPosition = read.str.get(1).indexOf("<dvalue>") + "<dvalue>".length();
		int endPosition = read.str.get(1).indexOf("</dvalue>", startPosition);
		this.e_d_val = new BigInt(read.str.get(1).substring(startPosition, endPosition));

		startPosition = read.str.get(2).indexOf("<nvalue>") + "<nvalue>".length();
		endPosition = read.str.get(2).indexOf("</nvalue>", startPosition);
		this.nVal = new BigInt(read.str.get(2).substring(startPosition, endPosition)); 
	}
	
	// write public key to file, with given filename
	public void writePublicKeyToFile(String filename) throws FileNotFoundException
	{
		List<String> str = new ArrayList<String>();
		String st;
		
		str.add("<rsakey>");
		
		st = "<evalue>";
		for (int i = e_d_val.array.size()-1; i >= 0; i--)
		{
			st = st + e_d_val.array.get(i);
		}
		st = st + "</evalue>";
		
		str.add(st);
		
		st ="<nvalue>";
		for (int i = nVal.array.size()-1; i >= 0; i--)
		{
			st = st + nVal.array.get(i);
		}
		st = st + "</nvalue>";
		
		str.add(st);
		
		str.add("</rsakey>");
		
		new WriteToFile(str, filename);
	}

	// write private key to file, with given filename
	public void writePrivateKeyToFile(String filename) throws FileNotFoundException
	{
		List<String> str = new ArrayList<String>();
		String st;
		
		str.add("<rsakey>");
		
		st = "<dvalue>";
		for (int i = e_d_val.array.size()-1; i >= 0; i--)
		{
			st = st + e_d_val.array.get(i);
		}
		st = st + "</dvalue>";
		
		str.add(st);
		
		st ="<nvalue>";
		for (int i = nVal.array.size()-1; i >= 0; i--)
		{
			st = st + nVal.array.get(i);
		}
		st = st + "</nvalue>";
		
		str.add(st);
		
		str.add("</rsakey>");
		
		new WriteToFile(str, filename);
	}
}
