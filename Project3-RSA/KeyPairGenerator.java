/*	Class to generate public and private keys.
 * 	Users have options to either choose two random prime numbers from a given file,
 * 	or they could enter primes of their owns.
 * 
 */

import java.io.FileNotFoundException;


public class KeyPairGenerator 
{
	public BigInt p, q; // primes
	public BigInt nVal, eVal, dVal, phiVal;
	public Key publicKey, privateKey;

	// generate keys from given primes stored in local file
	public KeyPairGenerator(String pubName, String privName) throws FileNotFoundException
	{			
		ReadFromFile read = new ReadFromFile("primes.txt");
		int rand1, rand2;		

		rand1 = (int) (Math.random() * read.str.size());
		do
		{
			rand2 = (int) (Math.random() * read.str.size());
		}
		while (rand2 == rand1);

		this.p = new BigInt(read.str.get(rand1));
		this.q = new BigInt(read.str.get(rand2));

		//this.nVal = this.p.multiply(this.q);
		//this.phiVal = this.p.substract(BigInt.ONE()).multiply(this.q.substract(BigInt.ONE()));
		this.nVal = BigInt.multiply2(this.p, this.q);
		this.phiVal = BigInt.multiply2(this.p.substract(BigInt.ONE()), this.q.substract(BigInt.ONE()));
		generateEVal();
		generateDVal();

		publicKey = new Key(eVal, nVal);
		privateKey = new Key(dVal, nVal);

		publicKey.writePublicKeyToFile(pubName);
		privateKey.writePrivateKeyToFile(privName);
	}

	// user input two primes a and b
	public KeyPairGenerator(String a, String b, String pubName, String privName) throws FileNotFoundException
	{
		this.p = new BigInt(a);
		this.q = new BigInt(b);
		//this.nVal = this.p.multiply(this.q);
		//this.phiVal = this.p.substract(BigInt.ONE()).multiply(this.q.substract(BigInt.ONE()));
		this.nVal = BigInt.multiply2(this.p, this.q);
		this.phiVal = BigInt.multiply2(this.p.substract(BigInt.ONE()), this.q.substract(BigInt.ONE()));
		generateEVal();
		generateDVal();

		publicKey = new Key(eVal, nVal);
		privateKey = new Key(dVal, nVal);

		publicKey.writePublicKeyToFile(pubName);
		privateKey.writePrivateKeyToFile(privName);
	}

	// get e value from phi value
	public void generateEVal()
	{
		boolean check = true;
		int k = 1;

		while (check == true)
		{
			k++;
			BigInt e = new BigInt(k);

			if (phiVal.GCD(e).equals(BigInt.ONE()))
			{
				this.eVal = e;
				check = false;	
			}
		}
	}

	// get d value from phi value
	public void generateDVal()
	{
		boolean check = true;
		BigInt d = new BigInt(1);
		while (check == true)
		{
			d = d.add(BigInt.ONE());
			BigInt.printB(d);
			//if (eVal.multiply(d).modulo(phiVal).equals(BigInt.ONE()))
			if (BigInt.multiply2(eVal, d).modulo(phiVal).equals(BigInt.ONE()))
			{
				this.dVal = d;
				check = false;			
			}
		}
	}

	// check if given number is prime
	public boolean isPrime(int n) 
	{
		if (n <= 1) {
			return false;
		}
		for (int i = 2; i < Math.sqrt(n); i++) 
		{
			if (n % i == 0) 
				return false;
		}
		return true;
	}

	/*
	public static void main(String[] args) throws FileNotFoundException
	{

		KeyPairGenerator t = new KeyPairGenerator("10723", "10831");

		BigInt.printB(t.p);
		BigInt.printB(t.q);

		BigInt.printB(t.nVal);
		BigInt.printB(t.phiVal);
		BigInt.printB(t.publicKey.e_d_val);

		BigInt.printB(t.privateKey.e_d_val);
		BigInt.printB(t.publicKey.nVal);
		t.publicKey.writePublicKeyToFile("pubkeyNew.txt");
		t.privateKey.writePrivateKeyToFile("prikeyNew.txt");

	}
	 */
}
