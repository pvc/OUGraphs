/**
 * 
 */
package graphs;
import java.util.Date;

import org.pv.core.Utils;



/**
 * @author PV
 * Plots the growth of the numerator terms showing divergence -
 * each iteration slightly better than doubles the range(image)
 *	
 */
public class CalcFixedPointDivergence {
	final Utils utils = Utils.getSingleton();
	static final double omega=0.5*(Math.sqrt(5)-1);
//	final long[] fib={0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584, 4181, 6765, 10946, 17711, 28657, 46368, 75025, 121393, 196418, 317811, 514229, 832040, 1346269, 2178309, 3524578, 5702887, 9227465, 14930352, 24157817, 39088169};
//	final double twoPi=2*Math.PI;
	final double w=omega;
	final double wSquared=w*w;
	final double denom=w;
//	final double twoPiw=2*Math.PI*w;
	
	// Method called to run the class
	public void run() {
		p("Starting run of CalFixedPoint at " + new Date());
		int nGraphs=10;
		int nPoints=250;
		ScalarSequence x = new XSequence(-5,6,nPoints);
		MultiGraph mg = new MultiGraph(5);
		mg.setAxes(true);
		for (int n=0;n<nGraphs;n++) {
			ScalarSequence y = calc(x,n);
			Graph g=new Graph(nPoints);
			g.add(x,y);
			g.addText(50,200,"max="+y.getMax());
			g.addText(50,214,"min="+y.getMin());
			mg.add(g);
		}
		mg.display();
		
//		p(calcHRatio(1,1,new h1()));
//		p(""+theta);
//		calcHRatioSeq(15,0,new hc(Math.E)).dumpRange();
//		p(calcH(1,1,new hc(Math.E)));
		p("Finished run of CalFixedPoint at " + new Date());
	}
	
	public ScalarSequence calc(ScalarSequence xSeq,int degree) {
		final ScalarSequence y = new ScalarSequence(xSeq.getSize());
		
		for (int point=0; point<xSeq.getSize(); point++) { // calc each value of y
			double in=xSeq.get(point);
//			double out=in;
			int len=1;
			double[] termsx,termsw,prevx,prevw;
			termsx=new double[]{in};
//			termsw=new double[]{-w};
			double term=(in)*(1+w*in);
			for (int n=0;n<degree;n++) {
				len=2*len;
				prevx=termsx;
//				prevw=termsw;
				termsx=new double[len];
//				termsw=new double[len];
				for (int m=0;m<len/2;m++) {
					termsx[2*m]=f1(prevx[m]);
					termsx[2*m+1]=f2(prevx[m]);
//					termsw[2*m]=f1(prevw[m]);
//					termsw[2*m+1]=f2(prevw[m]);
					term*=(1+w*termsx[2*m])*(1+w*termsx[2*m+1]);
				}
			}
			y.add(term);
			p(""+in+"->"+term);
		}
		return y;
	}
	
	double f1(double x){
		return -w*x;
	}
	double f2(double x){
		return wSquared*x+w;
	}
	
	
	
	
	

	// Utility method for quick printing to console
	void p(Object o) {
		utils.log(o);
	}
	
	public interface Functionh {
		public double h(double theta);
	}
	public class h1 implements Functionh {
		public double h(double theta) {return theta;}
	}
	public class hc implements Functionh {
		private final double MULTIPLIER;
		public hc(double multiplier) {this.MULTIPLIER=multiplier;}
		public double h(double theta) {return MULTIPLIER*theta;}
	}

}
