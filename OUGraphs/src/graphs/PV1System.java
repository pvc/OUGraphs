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
public class PV1System extends QuasiPeriodicSystem {
	

	private final double xBound;
	private final double lambda;

	/**
	 * @param dimension
	 * @param angularFrequency
	 */
	public PV1System(int dimension, double angularFrequency,double xBound,double lambda) {
		super(angularFrequency);
		this.xBound=xBound;
		this.lambda=lambda;
	}
	
	/* (non-Javadoc)
	 * @see graphs.QuasiPeriodicSystem#applyRule(double[])
	 */
	@Override
	public double[] applyRule(double[] it,int i,final double[] initialPoint) {
		double[] p = super.applyRule(it,i,initialPoint);
		double x = it[1];
		p[1]=lambda*g(x)*h(it[0]);
		return p;
	}

	/**
	 * @param d
	 * @return
	 */
	private double h(double d) {
		return d;
	}

	/**
	 * @param x
	 * @return
	 */
	private double g(double x) {
		if (x>xBound) {x=xBound;}
		return x;
	}
	

}
