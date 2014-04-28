/**
 * 
 */
package math;
import java.util.Date;
import java.util.function.IntToDoubleFunction;
import java.util.stream.IntStream;

import org.pv.core.Utils;
/**
 * @author Paul
 *
 */
public class SineSums {
	final Utils utils = Utils.getSingleton();

	// Method called to run the class
	public void run() {
		p("Starting run of SineSums at " + new Date());
//	    test();
	    utils.dump(IntStream.range(10, 20).getClass());
		p("Finished run of SineSums at " + new Date());
	}
	
	public void test() {
//		int n=7;
//		n=5;
		double p=10;
		double theta=0;
		final double x=0.5;
		 
		IntToDoubleFunction sumFunction1 = new IntToDoubleFunction() {
			
			@Override
			public double applyAsDouble(int k) {
				return Math.sin(theta+k*x);
			}
		};
		IntToDoubleFunction sumFunction2 = new IntToDoubleFunction() {
			
			@Override
			public double applyAsDouble(int k) {
				return k*Math.sin(theta+k*x);
			}
		};
		IntToDoubleFunction sumFormula1 = new IntToDoubleFunction() {
			
			@Override
			public double applyAsDouble(int n) {
				return (Math.cos(theta+x/2)-Math.cos(theta+(n+0.5)*x))/(2*Math.sin(x/2));
			}
		};
		IntToDoubleFunction sumFormula2 = new IntToDoubleFunction() {
			
			@Override
			public double applyAsDouble(int n) {
				double sinhalfx=Math.sin(x/2);
				return (Math.sin(theta+n*x)-Math.sin(theta)-2*n*Math.cos(theta+(n+0.5)*x)*sinhalfx)/(4*sinhalfx*sinhalfx);
			}
		};
		IntToDoubleFunction sumFormula3 = new IntToDoubleFunction() {
			
			@Override
			public double applyAsDouble(int n) {
				double sinhalfx=Math.sin(x/2);
				return (sinhalfx*Math.cos(x/2)-Math.sin(n*x)/p-(1-2*n/p)*Math.cos((n+0.5)*x)*sinhalfx)/(2*sinhalfx*sinhalfx);
			}
		};
		for (int n=1;n<11;n++) {
			double sum1=IntStream.rangeClosed(1, n).mapToDouble(sumFunction1).sum();
			double formulaValue1=sumFormula1.applyAsDouble(n);
		double sum2=IntStream.rangeClosed(1, n).mapToDouble(sumFunction2).sum();
		double formulaValue2=sumFormula2.applyAsDouble(n);
		double formulaValue3=sumFormula3.applyAsDouble(n);
		double sum=sum1-(2.0/p)*sum2;
//		double formulaValue=formulaValue1-(2.0/p)*formulaValue2;
		double formulaValue=formulaValue3;
		p("n="+n+"; sum="+sum+"; formula="+formulaValue);
		if (sum==formulaValue) {p("Success");} else {p("Fail");}
		}
	}

	// Utility method for quick printing to console
	void p(Object o) {
		utils.log(o);
	}

}
