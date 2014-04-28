/**
 * 
 */
package graphs;
import java.util.Date;

import org.pv.core.Utils;



/**
 * @author PV
 * Calc 'degree' iterations of F(w)/F(-w) and plot iterations as graph - should converge to 1+w 
 *
 */
public class CalcFixedPointConstant {
	final Utils utils = Utils.getSingleton();
	static final double omega=0.5*(Math.sqrt(5)-1);
	final long[] fib={0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584, 4181, 6765, 10946, 17711, 28657, 46368, 75025, 121393, 196418, 317811, 514229, 832040, 1346269, 2178309, 3524578, 5702887, 9227465, 14930352, 24157817, 39088169};
	final double twoPi=2*Math.PI;
	final double w=omega;
	final double twoPiw=2*Math.PI*w;
	
	// Method called to run the class
	public void run() {
		p("Starting run of CalFixedPoint at " + new Date());
		double x=w;
//		int nGraphs=20;
		int degree=25;
		int nPoints=250;
		MultiGraph mg = new MultiGraph(1);
		mg.setAxes(true);
//		for (int n=0;n<nGraphs;n++) {
			ScalarSequence y = calc(x,degree);
			Graph g=new Graph(nPoints);
			g.add(y);
			g.addText(0,35,"max="+y.getMax());
			g.addText(0,50,"min="+y.getMin());
			mg.add(g);
//		}
		mg.display();
		
//		p(calcHRatio(1,1,new h1()));
//		p(""+theta);
//		calcHRatioSeq(15,0,new hc(Math.E)).dumpRange();
//		p(calcH(1,1,new hc(Math.E)));
		p("Finished run of CalFixedPoint at " + new Date());
	}
	
	public ScalarSequence calc(double value,int degree) {
//		double denom=w;
		double denom=-w;
		final ScalarSequence y = new ScalarSequence(degree);
		double in=value;
//		double out=in/-w;
		int len=1;
		double[] termsx,termsw,prevx,prevw;
		termsx=new double[]{in};
		termsw=new double[]{denom};
		double term=((1+w*in)/(1+w*denom));
//		y.add(term);
		for (int n=1;n<degree;n++) {
			len=2*len;
			prevx=termsx;prevw=termsw;
			termsx=new double[len];termsw=new double[len];
			for (int m=0;m<len/2;m++) {
				termsx[2*m]=f1(prevx[m]);
				termsx[2*m+1]=f2(prevx[m]);
				termsw[2*m]=f1(prevw[m]);
				termsw[2*m+1]=f2(prevw[m]);
				term*=(1+w*termsx[2*m])*(1+w*termsx[2*m+1])/((1+w*termsw[2*m])*(1+w*termsw[2*m+1]));
			}
			y.add(term);
			p(""+n+"->"+term);
		}
		return y;
	}
	
	double f1(double x){
		return -w*x;
	}
	double f2(double x){
		return w*w*x+w;
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
