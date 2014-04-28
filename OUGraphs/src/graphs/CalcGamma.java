/**
 * Calc Gamma(p,q,alpha) 
 */
package graphs;
import java.util.Date;

import org.pv.core.Utils;

/**
 * @author PV
 *
 */
public class CalcGamma {
	final Utils utils = Utils.getSingleton();

	// Method called to run the class
	public void run() {
		p("Starting run of CalcGamma at " + new Date());
		gamma(19,.05);
		p("Finished run of CalcGamma at " + new Date());
	}
	
	public void gamma(final int q, final double alpha) {
		for (int p=1;p<q;p++) {
		int num=0; double irr=0;double tot=1;
			
		for (int i=1;i<q;i++) {
			num+=p; if (num>q) {num-=q;}
			irr+=alpha;
			tot*=(1.+irr/num);
//			p(i+":"+tot);
		}
		p(p+":"+tot);
		}
		
	}

	// Utility method for quick printing to console
	void p(Object o) {
		utils.log(o);
	}

}
