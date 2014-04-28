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
public class CalcResidues {
	final Utils utils = Utils.getSingleton();

	// Method called to run the class
	public void run() {
		p("Starting run of CalcResidues at " + new Date());
		test1();
		p("Finished run of CalcResidues at " + new Date());
	}

	/**
	 * Plot normalised product of [(rp) mod q]/q for r=1 to k<q.
	 */
	private void test1() {
//		final int q=377;
//		final int q=997;
		final int q=144;
		MultiGraph mg=new MultiGraph(5);
		ScalarSequence seqMaxAt=new ScalarSequence(q);
		ScalarSequence seqMax=new ScalarSequence(q);
//		seqm.add(0);
		for (int p=1;p<q;p++) {
		int res=p;
		double tot=1;
		ScalarSequence seq=new ScalarSequence(q);
		for (int n=1;n<q;n++) {  // calc set of h(rp/q) over r
//			p(res);
//			if (res==0) {p("Hit zero at p="+p+";n="+n);} // will hit if (p,q)>1
			tot*=(Math.E*res)/q;
			seq.add(tot);
			res+=p; if (res>=q) {res-=q;}
		}
		Graph g=new Graph(250);  // build graph of this residue's values
		g.setTitle("p="+p+",maxAt="+(seq.maxAt+1)+" (="+seq.max+")");
		g.add(seq);
//		p("Added graph:"+p);
		mg.add(g);
//		seqMax.add(Math.log(seq.max));
//		seqMaxAt.add(seq.maxAt);
		}
//		Graph g=new Graph(600);  // graph of maximiser for each residue
//		g.setTitle("residue vs Max");  
//		g.add(seqMax);
//		g.display();
//		seqMax.dumpRange();
//		seqMax.dump(990,995);
//		seqMaxAt.dump(990,995);
//		p("About to display");
		mg.display();
//		mg.save("C:/Empty/Residues"+q+".png");
	}
	
	

	// Utility method for quick printing to console
	void p(Object o) {
		utils.log(o);
	}

}
