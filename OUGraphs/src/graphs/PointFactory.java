/**
 * 
 */
package graphs;
import java.util.ArrayList;
import java.util.Date;

import org.pv.core.Utils;

/**
 * @author PV
 *
 */
public class PointFactory {
	final Utils utils = Utils.getSingleton();
	final int dimension;
	
	public PointFactory(int dimension) {
		this.dimension=dimension;
	}

	// Method called to run the class
	public void run() {
		p("Starting run of PointFactory at " + new Date());
		// TODO insert code here
		p("Finished run of PointFactory at " + new Date());
	}
	
	
//	public double[] newPoint() {
//		return new double[dimension];
//	}

	// Utility method for quick printing to console
	void p(Object o) {
		utils.log(o);
	}

	/**
	 * @param i
	 * @param j
	 * @return 
	 */
	public double[] newPoint(double... coordinates) {
		double[] p = new double[dimension];
		for (int n=0;n<dimension;n++) {
			if (n<coordinates.length) {p[n]=coordinates[n];} else {p[n]=0;}
		}
		return p;
	}

}
