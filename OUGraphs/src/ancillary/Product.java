/**
 * 
 */
package ancillary;
import java.util.Date;
import java.util.function.IntToDoubleFunction;
import java.util.function.LongToDoubleFunction;

import org.pv.core.Utils;
/**
 * @author Paul
 *
 */
public class Product {
	final Utils utils = Utils.getSingleton();
	int start=1,end=1;
	public Product(int startInclusive, int endInclusive) {
		this.start=startInclusive;
		this.end=endInclusive;
	}

	// Method called to run the class
	public void run() {
		p("Starting run of Product at " + new Date());
		// TODO insert code here
		p("Finished run of Product at " + new Date());
	}

	// Utility method for quick printing to console
	void p(Object o) {
		utils.log(o);
	}
	
	public static Product over(int startInclusive, int endInclusive) {
		return new Product(startInclusive,endInclusive);
	}
	public double of(LongToDoubleFunction function) {
		double prod=1;
		for (long n=start;n<=end;n++) {
			prod*=function.applyAsDouble(n);
		}
		return prod;
	}

}
