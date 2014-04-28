/**
 * 
 */
package graphs;
import java.util.Date;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.pv.core.Utils;


/**
 * @author PV
 *
 */
public class Main {
	final Utils utils = Utils.getSingleton();
	final double w3=(Math.sqrt(3));
	final double w=(Math.sqrt(5)-1)/2;
	final double twopi=2*Math.PI;

	// Method called to run the class
	public void run() {
		p("Starting run of Main at " + new Date());
		doIt();
		p("Finished run of Main at " + new Date());
	}
	
	public void doIt() {
		try {
			DynamicalSystem ds = new QuasiPeriodicSystem(2,new double[] {w,w3},100);
//			DynamicalSystem ds = new PV1System(2,w,0.5,2.75);
//			DynamicalSystem ds = new QPProductSystem(w,Math.E);
		double[] initial = ds.newPoint(0,0);
//		ScalarSequence orbit = ds.calcOrbitProjection(initial, 60_000, 1);
		
//		double max=0;
//		for (int n=60;n<orbit.getSize();n++){
//			double next=orbit.get(n);
//			if (next>max) {max=next; p(Math.log(n)+" "+Math.log(max));}
//		}
		ScalarTransform sine = new ScalarTransform() {double calc(double d) {return Math.sin(twopi*d);}};
		int[] lengths=new int[] {425,2000,10000,20000};
//		int[] lengths=new int[] {425};
		MultiGraph mg = new MultiGraph(4);
		for (int i=0;i<lengths.length;i++) {
			PointSequence orbit = ds.calcOrbit(initial, lengths[i]);
			orbit.applyTransform(sine);
			Graph g = new Graph(200,200);
			g.add(orbit.get(0),orbit.get(1));
			mg.add(g);
//			g.display();
		}
		mg.display();
		
//		orbit.dump();
		} catch (Exception e) {e.printStackTrace(utils.getLogger());}
	}

	// Utility method for quick printing to console
	void p(Object o) {
		utils.log(o);
	}

}
