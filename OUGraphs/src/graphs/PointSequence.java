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
public class PointSequence {
	final Utils utils = Utils.getSingleton();
	int npoints=0;
	final int capacity;
	final ScalarSequence[] x;
	final PointFactory pointFactory;
	
//	final double[] min,max;
	public PointSequence(PointFactory pointFactory) {
		capacity=100;
		x=new ScalarSequence[pointFactory.dimension];
		for (int n=0;n<pointFactory.dimension;n++) {
			x[n]=new ScalarSequence(capacity);
		}
		this.pointFactory=pointFactory;
//		min=new double[pointFactory.dimension];
//		max=new double[pointFactory.dimension];
	}
	public PointSequence(PointFactory pointFactory,int nPoints) {
		capacity=nPoints;
		x=new ScalarSequence[pointFactory.dimension];
		for (int n=0;n<pointFactory.dimension;n++) {
			x[n]=new ScalarSequence(capacity);
		}
		this.pointFactory=pointFactory;
//		min=new double[pointFactory.dimension];
//		max=new double[pointFactory.dimension];
	}
	
	public void add(double[] point) {
		double xn;
		for (int n=0;n<pointFactory.dimension;n++) {
			xn=point[n];
			x[n].add(xn);
//			if (npoints==0) {min[n]=max[n]=xn;}
//			else if (xn<min[n]) {min[n]=xn;} else if (xn>max[n]) {max[n]=xn;} 
		}
		npoints++; 
	}
	public double[] getPoint(int n) {
		double[] pn = pointFactory.newPoint();
		for (int i=0;i<pointFactory.dimension;i++) {
			pn[i]=x[i].get(n);
		}
		return pn;
	}
	public ScalarSequence getProjection(int n) {return x[n];}
	public int getSize() {return npoints;}
	public double get(int point,int ordinate) {return x[ordinate].get(point);}

	// Utility method for quick printing to console
	void p(Object o) {
		utils.log(o);
	}
	
	public String toString() {
		return "Points: "+npoints; 
	}
	/**
	 * @param i
	 * @return
	 */
	public ScalarSequence get(int i) {
		// TODO Auto-generated method stub
		return x[i];
	}
	/**
	 * 
	 */
	public void dump() {
		String line;
		for (int row=0;row<npoints;row++) {
		line=""+row;
		for (int n=0;n<x.length;n++) {
			line+=","+x[n].get(row);
		}
		p(line);
		}
	}
	/**
	 * @param scalarTransform
	 */
	public void applyTransform(ScalarTransform scalarTransform) {
		for (int n=0;n<x.length;n++) {
			scalarTransform.applyTo(x[n]);
		}
	}

}
