public class Helper {
	
	/** 
    * Class constructor.
    */
	private Helper () {}

	/**
	* This method is used to check if a number is prime or not
	* @param x A positive integer number
	* @return boolean True if x is prime; Otherwise, false
	*/
	public static boolean isPrime(int x) {
		
		int i, limit = (int) Math.sqrt(x);
		
		for(i=2; i<=limit; i++)
			if(x%i == 0)
				return false;
		
		return true;
	}

	/**
	* This method is used to get the largest prime factor 
	* @param x A positive integer number
	* @return int The largest prime factor of x
	*/
	public static int getLargestPrimeFactor(int x) {

    	int i;
    	int d = x;
    	
    	for(i=2; i<=x; i++) {
    		while(x%i == 0)
    			x /= i;
    	}

    	//System.out.println("Largest prime factor for " +  d + " " + (i-1));
		return i-1;
    }
}