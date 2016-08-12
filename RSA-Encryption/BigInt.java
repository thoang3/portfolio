//import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


public class BigInt 
{

	public List<Integer> array;

	// default constructor
	public BigInt()
	{
		array = new ArrayList<Integer>();
	}

	// initialize constructor with input number as string 
	public BigInt(String str)
	{
		array = new ArrayList<Integer>();

		for (int i = str.length()-1 ; i >=0 ; i--)
		{
			array.add(Integer.parseInt(""+str.charAt(i)));
		}	
	}

	// initialize constructor with input number as int
	public BigInt(int num)
	{
		array = new ArrayList<Integer>();
		int temp = num;

		do 
		{
			array.add(temp % 10);
			temp = temp/10;			
		}
		while (temp > 0);
	}

	// constant BigInt 0
	public static BigInt ZERO()
	{
		return new BigInt(0);
	}

	// constant BigInt 1
	public static BigInt ONE()
	{
		return new BigInt(1);
	}

	// constant BigInt 2
	public static BigInt TWO()
	{
		return new BigInt(2);
	}

	// padding n zeros in front of the big integer
	public static BigInt padding(BigInt e, int n)
	{
		for (int i = 0; i < n; i++)
		{
			e.array.add(0);		
		}

		return e;
	}

	// dePadding all zeros in front of big integer
	public static BigInt dePadding(BigInt e)
	{
		while ((e.array.get(e.array.size()-1) == 0) && (e.array.size() > 1))
		{
			e.array.remove(e.array.size()-1);
		}
		return e;
	}

	// shift the big integer left by n position
	public static BigInt shiftLeft(BigInt val, int n)
	{
		BigInt val1 = new BigInt();

		for (int i = 0; i < n; i++)
		{
			val1.array.add(0);
		}
		val1.array.addAll(val.array);

		return val1;
	}

	// shift the big integer right by n position
	public static BigInt shiftRight(BigInt val, int n)
	{
		int m = val.array.size();

		if (n >= m)
			return new BigInt(0);

		BigInt val1 = new BigInt();

		for (int i = n; i < m; i++)
		{
			val1.array.add(val.array.get(i));
		}

		return val1;
	}

	// extract the first n digits of big integer
	public static BigInt extractFirstN(BigInt val, int n)
	{
		BigInt val1 = new BigInt();

		if (val.array.size() < n)
			return val;

		for (int i = 0; i < n; i++)
		{
			val1.array.add(val.array.get(i));
		}

		return val1;
	}

	// print BigInt in the usual order
	public static void printB(BigInt e)
	{
		for (int i = e.array.size()-1; i >= 0; i--)
		{
			System.out.print(e.array.get(i) + " ");
		}
		System.out.println();
	}

	// equal method, to check if two big integers are equal or not
	public boolean equals(BigInt val)
	{
		int m = Math.max(this.array.size(), val.array.size());

		if (this.array.size() > val.array.size())
			padding(val, this.array.size()-val.array.size());
		else if (this.array.size() < val.array.size())
			padding(this, val.array.size()-this.array.size());

		for (int i = 0; i < m; i++)
		{
			if (this.array.get(i) != val.array.get(i))
				return false;
		}

		return true;
	}

	// greater than ">"
	public boolean greaterThan(BigInt val)
	{
		dePadding(this);
		dePadding(val);

		if (this.array.size() > val.array.size())
			return true;
		else if (this.array.size() < val.array.size())
			return false;

		if (this.array.size() == val.array.size())
		{
			int i = this.array.size()-1;
			while ((this.array.get(i) == val.array.get(i)) && (i > 0))
				i--;
			if (this.array.get(i) > val.array.get(i))
				return true;
			else 
				return false;			
		}

		return true;		
	}

	// greater than or equal ">="
	public boolean greaterThanOrEqual(BigInt val)
	{
		if (this.equals(val))
			return true;
		if (this.greaterThan(val))
			return true;

		return false;
	}

	// add two BigInt numbers
	public BigInt add(BigInt val)
	{
		int m = Math.max(this.array.size(), val.array.size());
		BigInt temp = new BigInt(0);
		padding(temp, m-1);
		if (this.array.size() > val.array.size())
			padding(val, this.array.size()-val.array.size());
		else if (this.array.size() < val.array.size())
			padding(this, val.array.size()-this.array.size());

		int c = 0;

		for (int i = 0; i < m; i++)
		{
			c = c + this.array.get(i) + val.array.get(i);
			temp.array.set(i, c %10);
			c = c/10;		
		}

		if (c > 0) temp.array.add(c);

		dePadding(val);

		return dePadding(temp); 
	}

	// subtract
	public BigInt substract(BigInt val)
	{
		BigInt temp = new BigInt(0);

		if (!this.greaterThanOrEqual(val))
		{
			System.out.println("Cannot substract!");
			System.exit(1);
		}
		else
		{

			padding(val, this.array.size()-val.array.size());
			padding(temp, this.array.size());

			int c = 0;
			int carry = 0;

			for (int i = 0; i < this.array.size(); i++)
			{
				if (this.array.get(i) >= (val.array.get(i)+carry))
				{
					c = this.array.get(i) - val.array.get(i) - carry;
					carry = 0;
				}
				else 
				{
					c = 10 + this.array.get(i) - val.array.get(i) - carry;
					carry = 1;
				}
				temp.array.set(i, c);						
			}		
		}

		return dePadding(temp);
	}

	// naive multiply
	public BigInt multiply(BigInt val)
	{
		BigInt temp = new BigInt(0);

		for (int i = 0; i < this.array.size(); i++)
		{
			int j = this.array.get(i);

			for (int k = 0; k < Math.pow(10, i) * j; k++)
			{
				temp = temp.add(val);
			}	
		}		
		return temp;
	}

	// fast multiply Karatsuba method
	public static BigInt multiply2(BigInt A, BigInt B)
	{
		int n = Math.max(A.array.size(), B.array.size());

		if (n < 2) return A.multiply(B);

		n = n/2;

		BigInt A1 = new BigInt();
		BigInt A0 = new BigInt();
		BigInt B1 = new BigInt();
		BigInt B0 = new BigInt();
		BigInt Z2 = new BigInt();
		BigInt Z1 = new BigInt();
		BigInt Z0 = new BigInt();

		A1 = BigInt.shiftRight(A, n);
		B1 = BigInt.shiftRight(B, n);
		A0 = BigInt.extractFirstN(A, n);
		B0 = BigInt.extractFirstN(B, n);

		Z2 = multiply2(A1, B1);
		Z0 = multiply2(A0, B0);
		Z1 = multiply2(A1.add(A0), B1.add(B0));

		return BigInt.shiftLeft(Z2, 2* n).add(BigInt.shiftLeft(Z1.substract(Z2).substract(Z0), n)).add(Z0);
	}

	// divide
	public BigInt divide(BigInt val)
	{
		BigInt q = new BigInt(0);
		BigInt temp = this;
		int count = 0;

		while (temp.greaterThanOrEqual(val))
		{
			q.add(BigInt.ONE());
			count++;
			temp = temp.substract(val);
		}

		return new BigInt(count);
	}

	// decimal to base 2 BigInt, helper function for the below method
	public static int getIndexBase2(BigInt val)
	{
		BigInt val2 = new BigInt(1);

		int i = 0;

		while (val.greaterThan(val2))
		{
			val2 = BigInt.multiply2(val2, BigInt.TWO());			
			i++;
		}

		if (val2.equals(val)) 
		{
			return i;
		}
		else
		{
			return i-1;
		}		
	}

	// get base 2 representation of decimal big integer
	public static List<Integer> getArrayIndexBase2(BigInt val)
	{
		List<Integer> arr = new ArrayList<Integer>();
		BigInt temp = new BigInt(1);

		if (val.equals(BigInt.ZERO()))
			return arr;

		int i = BigInt.getIndexBase2(val);
		arr.add(i);

		for (int k = 0; k < i; k++)
		{
			temp = BigInt.multiply2(temp, BigInt.TWO());
		}

		arr.addAll(getArrayIndexBase2(val.substract(temp)));

		return arr;
	}

	// modulo
	public BigInt modulo(BigInt val)
	{
		BigInt temp = this;
		boolean check = true;
		int a;
		int b = val.array.size();

		while (check)
		{
			a = temp.array.size();
			if (a > b)
			{
				temp = temp.substract(BigInt.shiftLeft(val, a-b-1));			
			}
			else
				check = false;
		}

		while (temp.greaterThanOrEqual(val))
		{
			temp = temp.substract(val);
		}

		return temp;		
	}	

	// professor's method to find modular exponential, very slow
	public BigInt expoModulo(BigInt e, BigInt n)
	{
		BigInt c = BigInt.ONE();
		BigInt i = BigInt.ZERO();
		boolean check = true;

		while (check)
		{
			i = i.add(BigInt.ONE());
			c = BigInt.multiply2(this, c).modulo(n);
			if (!e.greaterThan(i))
				check = false;
		}

		return c;
	}	

	// improved method to find modular exponential, for power of 2 integer (2^m), 
	// helper function for the below method
	public BigInt moduloExpoPow2(int m, BigInt n)
	{			
		if (m == 0) 
			return this.modulo(n);	

		BigInt b = new BigInt(); 
		b = this.moduloExpoPow2(m-1, n);

		return BigInt.multiply2(b, b).modulo(n);
	}

	// faster modular exponential
	public BigInt moduloExponentiation(BigInt e, BigInt n)
	{
		BigInt c = BigInt.ONE();
		List<Integer> a = new ArrayList<Integer>();
		a = BigInt.getArrayIndexBase2(e);

		for (int k = 0; k < a.size(); k++)
		{
			c = BigInt.multiply2(c, this.moduloExpoPow2(a.get(k), n));
		}

		return c.modulo(n);
	}

	// input block M, using publicKey to encrypt into C
	public static BigInt encrypt(BigInt M, Key publicKey)
	{
		BigInt C = new BigInt();

		C = M.moduloExponentiation(publicKey.e_d_val, publicKey.nVal);
		//C = M.expoModulo(publicKey.e_d_val, publicKey.nVal);

		return C;
	}

	public static BigInt decrypt(BigInt C, Key privateKey)
	{
		BigInt M = new BigInt();

		M = C.moduloExponentiation(privateKey.e_d_val, privateKey.nVal);
		//M = C.expoModulo(privateKey.e_d_val, privateKey.nVal);

		return M;
	}

	// Greatest common divisor of two big integers
	public BigInt GCD(BigInt val)
	{
		BigInt _this = new BigInt();
		BigInt _val = new BigInt();
		_this = this;
		_val = val;

		if (_this.equals(new BigInt(0)))
			return _val;

		while (!_val.equals(new BigInt(0))) 
		{
			if (_this.greaterThan(_val))
				_this = _this.substract(_val);
			else
				_val = _val.substract(_this);
		}

		return _this;	
	}

}