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
public class XSequence extends ScalarSequence {
//	Create a sequence of 0..n-1
	boolean simple=true;
	public XSequence(int n) {
		init(n);
	}
	public XSequence(ScalarSequence y) {
		init(y.getSize()-1);
	}
	
	public XSequence(double start, double end, int npoints) {
		init(npoints);min=start;max=end;simple=false;
	}
	
	/**
	 * @param i
	 * @param j
	 */
	public XSequence(int i, int j) {
		// TODO Auto-generated constructor stub
	}
	void init(int n) {
		index=n+1;max=n;maxAt=n;min=0;minAt=0;
		validStats=true;
	}
	
	@Override
	public void add(double x) {}
	public void set(int n,double x) {}
//	public double get(int n) {return min+n*(max-min)/index;}
	public double get(int n) {
		if (simple) {return n;} else {return min+n*(max-min)/(index-1);}
		}
	public int max(int first, int last) {return last;}
}
