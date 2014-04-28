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
public class CalcCFDiff {
	final Utils utils = Utils.getSingleton();

	// Method called to run the class
	public void run() {
		p("Starting run of CalcCFDiff at " + new Date());
		calc();
		p("Finished run of CalcCFDiff at " + new Date());
	}

	// Utility method for quick printing to console
	void p(Object o) {
		utils.log(o);
	}
	
	public void calc() {
		double p,q,dif,temp;
		p=1;q=1;
		final double w=0.5*(Math.sqrt(5)+1);
		for (int n=0;n<20;n++) {
			p(n+": "+(q*w-p)*q);
			temp=p;
			p+=q;q=temp;
		}
	}

}
