/**
 * 
 */
package graphs;
import graphs.CalcH.hc;

import java.util.Date;

import math.ContinuedFraction;

import org.pv.core.Utils;

/**
 * @author PV
 *
 */
public class OldTests {
	final Utils utils = Utils.getSingleton();
	final static double omega=0.5*(Math.sqrt(5)-1);
	final static double root2m1=(Math.sqrt(2)-1);
	final static double root3m1=(Math.sqrt(3)-1);

	// Method called to run the class
	public void run() {
		try {
		p("Starting run of Tests at " + new Date());
		test1();
		p("Finished run of Tests at " + new Date());
		} catch (Exception e) {e.printStackTrace(utils.getLogger());}
	}
	
	public void test0() {
		p(omega+Math.pow(omega,3)/(1-Math.pow(omega, 4)));
		
	}
	
	public void test1() {
		final double w=root3m1;
		ContinuedFraction cf = new ContinuedFraction(w,22);
		cf.init();
//		cf.q.dump();
//		for (int i = 0; i < cf.length; i++) {
//			p(((cf.q.get(i)*w)+Math.pow(-w,i+1))%1);
//			
//		}
		CalcH H = new CalcH();
		hc h = H.new hc(Math.E); // create a new hc
		ScalarSequence seq = H.calcHRatioSeq(20, 0, h);
//		seq.dumpRange();
//		double lastmax=1,max;
//		for (int m=2;m<19;m+=2) {
//			int q1=(int)cf.q.get(m);
//			int q2=(int)cf.q.get(m+2);
//			ScalarSequence sub = seq.sub(q1,q2);
//			max=sub.max;
//			p(m+": "+sub.max/lastmax+" at "+(sub.maxAt+0.)/(q2-q1));
//			lastmax=max;
//			Graph g = new Graph(250);
//			g.setTitle("m="+m+" ("+q1+","+q2+")");
//			g.plot(sub);
//			g.display(); 
//		}
//		int m=18;
//		sub.dumpRange();
//		int ix=seq.max((int)cf.q.get(m),(int)cf.q.get(m+2));
//		p("Max="+seq.get(ix)+" at "+ix);
//		p(cf.q.get(m));
//		seq.dump(cf.q.get(m)-2,cf.q.get(m)+1);
//		seq.dump(cf.q.get(m+2)-2,cf.q.get(m+2)+1);
		Graph g = new Graph(250);
		g.setTitle("Any old Title");
		g.add(seq);
//		g.setTitle((int)cf.q.get(17)+","+(int)cf.q.get(19));
//		g.plot(seq,(int)cf.q.get(17),(int)cf.q.get(19));
//		g.display(); 
//		g.save("C:/Empty/test.png");
		MultiGraph mg = new MultiGraph(2);
		mg.add(g);
		mg.add(g);
		mg.add(g);
		mg.display();
//		p((int)cf.q.get(m)+","+(int)cf.q.get(m+2));

	}
	public void test2() {
		ContinuedFraction cf = new ContinuedFraction(omega,20);
		cf.init();
//		for (int i = 0; i < cf.length; i++) {
//			p(((cf.q.get(i)*w)+Math.pow(-w,i+1))%1);
//			
//		}
		CalcH H = new CalcH();
		hc h = H.new hc(Math.E);
		int count=18;
		for (int n=1;n<count;n++) {
			double theta0=-Math.pow(-omega, n+1);
			p("n="+n+","+theta0+","+H.theta);
			ScalarSequence seq = H.calcHRatioSeq((int)cf.q.get(n-1), theta0, h);
			seq.dumpRange();
		}
	}

	// Utility method for quick printing to console
	void p(Object o) {
		utils.log(o);
	}

}
