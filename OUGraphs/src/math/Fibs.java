/**
 * 
 */
package math;
import java.util.Date;
import org.pv.core.Utils;
/**
 * @author Paul
 *
 */
public class Fibs {
	public static long[] fibsLong;
	final static Utils utils = Utils.getSingleton();
	final static int maxIntIndex=46;
	static {
		int nVals=60;
		fibsLong=new long[nVals];
		fibsLong[0]=0;fibsLong[1]=1;
		for (int n=2;n<nVals;n++) {
			fibsLong[n]=fibsLong[n-1]+fibsLong[n-2];
			p(""+n+"="+fibsLong[n]);
		}
	}

	// Method called to run the class
	public void run() {
		p("Starting run of Fibs at " + new Date());
		p(Integer.MAX_VALUE);
		p(""+get(46));
		p(""+get(47));
		p("Finished run of Fibs at " + new Date());
	}

	// Utility method for quick printing to console
	static void p(Object o) {
		utils.log(o);
	}

	
	public static long getLong(int fibIndex) {
		return fibsLong[fibIndex];
	}
	public static int get(int fibIndex) {
		p("evaluating:"+fibIndex);
		return (int)fibsLong[fibIndex];
	}
	

	/**
	 * @param fibIndex
	 * @return
	 */
//	public static long getLong(int fibIndex) {
//		// TODO Auto-generated method stub
//		return 0;
//	}

	/**
	 * @param n
	 * @return
	 */
//	public static long get(long n) {
//		// TODO Auto-generated method stub
//		return 0;
//	}

	/**
	 * @param start
	 * @param end
	 * @return
	 */
//	public static long[] range(int start, int end) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	

}
