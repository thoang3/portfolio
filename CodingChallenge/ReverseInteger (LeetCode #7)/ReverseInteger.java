// Reverse an integer without using string operations.

public class ReverseInteger {
	
	// iterative 
	public static int reverseInt(int n) {
		long retVal = 0;
		boolean flag = false;
		if (n < 0) {
			flag = true;
			n = -n;
		}

		while (n > 0) {
			System.out.println(retVal);
			retVal = retVal * 10 + n % 10;
			if (retVal > Integer.MAX_VALUE)
				return 0;
			n = n/10;
		}
		if (flag == true)
			return -(int) retVal;
		return (int) retVal;
	}

	// recursive
	public static int reverseInt2(int n, int retVal) {
		if (n == 0)
			return retVal;

		return reverseInt2(n/10, retVal * 10 + n % 10);
	}		

	// recursive
	static int base = 1;
	static int retVal = 0;

	public static int reverseInt3(int n) {
		if (n > 0) {
			reverseInt3(n/10);
			retVal = n%10 * base + retVal;
			base = base * 10;
		}

		return retVal;
	}
	
	public static void main(String[] args) {
		int n = 1534236469;

		System.out.println(reverseInt(n));
		System.out.println(reverseInt2(n, 0));
		System.out.println(reverseInt3(n));
	}
}
