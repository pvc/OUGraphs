/**
 * Continued Fraction calculation
 */
package graphs;
import java.util.Date;

import math.ContinuedFraction;

import org.pv.core.Utils;

/**
 * @author PV
 *
 */
public class OldContinuedFraction {
	final Utils utils = Utils.getSingleton();
	public ScalarSequence a=null;
	public ScalarSequence q=null;
	public ScalarSequence p=null;
	public ScalarSequence e=null;
	final int length;
	final double irrational;

	public OldContinuedFraction() {
		length=10;
		irrational=CalcH.omega;
	}
	public OldContinuedFraction(double irrational) {
		length=10;
		this.irrational=irrational;
	}
	public OldContinuedFraction(double irrational, int precision) {
		length=precision;
		this.irrational=irrational;
	}
	
	public void init() {
		calca();
		calcq();
		calcp();
		calce();
	}
	// Method called to run the class
	public void run() {
		p("Starting run of CalcCF at " + new Date());
//		ContinuedFraction cf = new ContinuedFraction(CalcH.omega,40);
//		ContinuedFraction cf = new ContinuedFraction(Math.pow(2.,1./4)-1);
		ContinuedFraction cf = new ContinuedFraction(Math.pow(2.,1./2)-1);
		cf.init(); 
		cf.a.dump();
		p("Finished calcs");
//		for (int n=0;n<cf.length;n++) {
//			p(n+":"+cf.p.get(n)/cf.q.get(n));
//		}
//		p(""+cf.irrational);
		p("Finished run of CalcCF at " + new Date());
	}
	
	public void calca() {
		double x=irrational,an;
		a=new ScalarSequence(length);
		for (int n=0;n<length;n++) {
			an=Math.floor(x);a.add(an);
			x=1/(x-an);
//			p(n+","+(int)an);
 		}
		
	}
	
	public void calcq() {
		q=new ScalarSequence(length);
		double prevq=1,curq=a.get(1);
		q.add(prevq);q.add(curq);
		for (int n=2;n<length;n++) {
			double qn=a.get(n)*curq+prevq;
			q.add(qn);prevq=curq;curq=qn;
//			p(n+","+(int)qn);
		}
//		q.dump();
	}
	
	public void calcp() {
		p=new ScalarSequence(length);
		double prevp=a.get(0),curp=a.get(1);
		p.add(prevp);p.add(curp);
		for (int n=2;n<length;n++) {
			double pn=a.get(n)*curp+prevp;
			p.add(pn);prevp=curp;curp=pn;
//			p(n+","+(int)pn);
		}
//		p.dump();
	}
	
	public void calce() {
		e=new ScalarSequence(length);
		for (int n=0;n<length-1;n++) {
			e.add((q.get(n)*irrational-p.get(n))*q.get(n+1));
//			p(n+","+e.get(n));
		}
//		e.dump();
	}
 
	// Utility method for quick printing to console
	void p(Object o) {
		utils.log(o);
	}

}
