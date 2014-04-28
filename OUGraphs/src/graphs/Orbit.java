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
public class Orbit extends XYSet {
	final Utils utils = Utils.getSingleton();

	// Method called to run the class
	public void run() {
		p("Starting run of Orbit at " + new Date());
		// TODO insert code here
		p("Finished run of Orbit at " + new Date());
	}

	// Utility method for quick printing to console
	void p(Object o) {
		utils.log(o);
	}

}
