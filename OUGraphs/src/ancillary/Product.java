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
	final static Utils utils = Utils.getSingleton();
	int start=1,end=1;
	public Product() {}
	public Product(int endInclusive) {this.end=endInclusive;}
	public Product(int startInclusive, int endInclusive) {
		this.start=startInclusive;
		this.end=endInclusive;
	}

	// Method called to run the class
	public void run() {
		p("Starting run of Product at " + new Date());
//		p(new Product(1,10));
		final double w=0.5*(Math.sqrt(5)-1);
		p(Product.over(1,3).of(n->2*Math.sin(Math.PI*w*n)));
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
