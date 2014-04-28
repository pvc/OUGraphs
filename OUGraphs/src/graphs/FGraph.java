/**
 * 
 */
package graphs;
import java.util.Date;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

import org.pv.core.StopWatch;
import org.pv.core.Utils;
/**
 * @author Paul
 *
 */
public class FGraph {
	static Utils utils = Utils.getSingleton();
	DoubleUnaryOperator function;
	ScalarSequence x,y;
	
	public FGraph() {}
	public FGraph(DoubleUnaryOperator function) {
		this.function=function;
	}
	public FGraph(DoubleUnaryOperator function,ScalarSequence x) {
		this.function=function;
		this.x=x;
	}

	// Method called to run the class
	public void run() {
		p("Starting run of FGraph at " + new Date());
		StopWatch t=utils.getTimer();
//		utils.sleep(1000);
//		test();
		logs();
		p("Finished run of FGraph at " + new Date());
		p("Elapsed: "+t.getTotalElapsed()+" seconds");
		
	}
	
	/**
	 * 
	 */
	private void logs() {
		double x=Math.log10(76);
		double y=Math.log10(.076);
		p(""+x+";"+y);
		double z = y;
		
		double exp=Math.floor(z);
		double m = Math.pow(10,z-exp);
		p(""+m);
		
	}
	public void test() {
		x=new ScalarSequence(IntStream.rangeClosed(10, 20).mapToDouble(i->(double)i).toArray());
		function=(x->2*x);
//		DoubleStream.of(10, 20).map((i)->{p(""+i);return 0.;});
//		DoubleStream.generate(s);
		y=new ScalarSequence(x.stream().map(function).toArray());
		y.dump();
		new Graph(200,200).add(x,y).display();
		
	}

	// Utility method for quick printing to console
	void p(Object o) {
		utils.log(o);
	}

}
