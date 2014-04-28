/**
 * 
 */
package graphs;
import java.util.Date;

import org.pv.core.Utils;

/**
 * @author PV
 *
 */
public class Misc {
	final Utils utils = Utils.getSingleton();

	// Method called to run the class
	public void run() {
		p("Starting run of Misc at " + new Date());
		int val=997;
		p(val+" is "+(checkPrime(val)?"prime":"not prime"));
		p("Finished run of Misc at " + new Date());
	}
	
	public boolean checkPrime(int n) {
		if (n<2) {return false;}
		if (n%2==0) {if (n==2) {return true;} else {return false;}}
		boolean isPrime=true;
		int rootn=(int) Math.floor(Math.sqrt(n));
		for (int m=3;m<=rootn;m++) {
			if (n%m==0) {isPrime=false;break;}
		}
		return isPrime;
	}

	// Utility method for quick printing to console
	void p(Object o) {
		utils.log(o);
	}

}
