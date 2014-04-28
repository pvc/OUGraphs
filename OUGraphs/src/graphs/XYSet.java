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
public class XYSet {
	final Utils utils = Utils.getSingleton();
	int npoints=-1;
	final int capacity;
	final double[] x,y;
	
	double minx,maxx,miny,maxy;
	public XYSet() {
		capacity=100;
		x=new double[capacity];
		y=new double[capacity];
	}
	public XYSet(int size) {
		capacity=size;
		x=new double[capacity];
		y=new double[capacity];
	}
	
	public void add(double x,double y) {
		npoints++;
		this.x[npoints]=x;this.y[npoints]=y;
		if (npoints==0) {minx=maxx=x;miny=maxy=y;}
		if (x<minx) {minx=x;} else if (x>maxx) {maxx=x;} 
		if (y<miny) {miny=y;} else if (y>maxy) {maxy=y;} 
	}
	public double getx(int n) {return x[n];}
	public double gety(int n) {return y[n];}

	// Utility method for quick printing to console
	void p(Object o) {
		utils.log(o);
	}
	
	public String toString() {
		return "Points: "+npoints+"; x=["+minx+","+maxx+"]; y=["+miny+","+maxy+"]";
	}

}
