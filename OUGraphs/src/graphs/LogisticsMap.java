/**
 * 
 */
package graphs;
import java.util.Date;

import org.pv.core.Utils;

/**
 * @author PV
 * Dynamics of map fx=w.x.(1-x)
 *
 */
public class LogisticsMap {
	final Utils utils = Utils.getSingleton();
	double w=3.5; // the parameter - 1-4, best values are around 3.5
	final int graphs=6;
	double theta=0; // set as a side effect to avoid overhead of creating composite return args
	public LogisticsMap() {}
	public LogisticsMap(double w) {this.w=w;}
	// Method called to run the class
	public void run() {
		p("Starting run of LogisticsMap at " + new Date());
		p("Parameter="+w);
		MultiGraph mg = new MultiGraph(2);
		ScalarSequence seq = baseInterval(250);
		for (int i = 0; i < graphs; i++) {
		ScalarSequence seq2 = iteraten(seq,(int)Math.pow(2,i));
//		seq2.dump();
		Graph g = new Graph(500);
		g.add(seq2);
//		g.add(seq);
		mg.add(g);
		}
		mg.display();

//		calcHRatioSeq(15,0,new hc(Math.E)).dumpRange();
//		p(calcH(1,1,new hc(Math.E)));
		p("Finished run of LogisticsMap at " + new Date());
	}
	
	public ScalarSequence baseInterval(int intervals) {
		ScalarSequence seq = new ScalarSequence(intervals+1);
		for (int n=0;n<=intervals;n++) {
			seq.add(((double)n)/intervals);
		}
		return seq;
	}
//	public ScalarSequence line01(int intervals) {
//		ScalarSequence seq = new ScalarSequence(intervals+1);
//		for (int n=0;n<=intervals;n++) {
//			seq.add(((double)n)/intervals);
//		}
//		return seq;
//	}
	
	public ScalarSequence iteraten(final ScalarSequence seq,int n) {
		ScalarSequence seqold = seq;
		ScalarSequence seqnew=null;;
		for (int m=0;m<n;m++) {
			seqnew=iterate(seqold);
			seqold=seqnew;
		}
		return seqnew;
	}
	/*
	 * Note 2-w>theta0>=-w works ok. Returns length=terms, first is 1,based on theta0
	 */
	public ScalarSequence iterate(final ScalarSequence seq) {
		final int terms=seq.getSize();
		ScalarSequence seq2 = new ScalarSequence(terms);
		for (int n=0;n<terms;n++) {
			double x=seq.get(n);
			double term=w*(x-x*x);
//			double term=w*0.25*Math.sin(Math.PI*x);
			seq2.add(term);
		}
		return seq2;
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
