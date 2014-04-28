/**
 * 
 */
package math;
import java.util.Date;

import org.pv.core.Utils;
/**
 * @author Paul
 *
 */
public class Rational {
	final Utils utils = Utils.getSingleton();
	private long p,q;
	/**
	 * @param integer
	 * @param integer2
	 */
	public Rational(Integer p, Integer q) {
		this.p=p.longValue();
		this.q=q.longValue();
	}

	// Method called to run the class
	public void run() {
		p("Starting run of Rational at " + new Date());
		// TODO insert code here
		p("Finished run of Rational at " + new Date());
	}

	// Utility method for quick printing to console
	void p(Object o) {
		utils.log(o);
	}

}
