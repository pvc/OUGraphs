/**
 * 
 */
package graphs;
import java.util.Date;

import org.eclipse.swt.graphics.Point;
import org.pv.core.Utils;


/**
 * @author PV
 *
 */
public class DynamicalSystem {
	final Utils utils = Utils.getSingleton();
	final int dimension;
	private PointFactory pointFactory;
//	final PointFactory pf;
//	public DynamicalSystem(PointFactory pf) {
//		this.pf=pf;
//	}

	/**
	 * @param i
	 */
	public DynamicalSystem(int dimension) {
//		pf=new PointFactory(dimension);
		this.dimension=dimension;
	}

	// Method called to run the class
	public void run() {
		p("Starting run of PVSystem at " + new Date());
		// TODO insert code here
		p("Finished run of PVSystem at " + new Date());
	}
	public double[] newPoint(double... coordinates) {
		double[] p = new double[dimension];
		for (int n=0;n<dimension;n++) {
			if (n<coordinates.length) {p[n]=coordinates[n];} else {p[n]=0;}
		}
		return p;
	}
	
	public double[] applyRule(double[] it, int i, double[] initialPoint) {
		double[] out;
		out=it;
		return out;
	}
	// Calc xn from x0
	public double[] calcIterate(final double[] initial,final int n) {
		double[] it=initial;
		for (int i=0;i<n;i++) {
			it=applyRule(it,i,initial);
		}
		return it;
	}
	// if nPoints is say 100, orbit will contain 101 points
	public PointSequence calcOrbit(final double[] initialPoint,final int npoints) {
		PointSequence orbit = new PointSequence(getPointFactory(),npoints+1);
		orbit.add(initialPoint);
		double[] it=initialPoint;
		for (int i=1;i<=npoints;i++) {
			it=applyRule(it,i,initialPoint);
			orbit.add(it);
		}
		return orbit;
	}
	/**
	 * @return
	 */
	private PointFactory getPointFactory() {
		if (pointFactory==null) {pointFactory=new PointFactory(dimension);}
		return pointFactory;
	}

	public ScalarSequence calcOrbitProjection(double[] initialPoint,final int npoints,int ordinate) {
		ScalarSequence orbit = new ScalarSequence(npoints+1);
		orbit.add(initialPoint[ordinate]);
		double[] it=initialPoint;
		for (int i=1;i<=npoints;i++) {
			it=applyRule(it,i, initialPoint);
			orbit.add(it[ordinate]);
		}
		return orbit;
	}

	// Utility method for quick printing to console
	void p(Object o) {
		utils.log(o);
	}

}
