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
	final Utils utils = Utils.getSingleton();

	// Method called to run the class
	public void run() {
		p("Starting run of Fibs at " + new Date());
		// TODO insert code here
		p("Finished run of Fibs at " + new Date());
	}

	// Utility method for quick printing to console
	void p(Object o) {
		utils.log(o);
	}

	/**
	 * @param fibIndex
	 * @return
	 */
	public static int get(int fibIndex) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @param fibIndex
	 * @return
	 */
	public static long getLong(int fibIndex) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @param n
	 * @return
	 */
	public static long get(long n) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @param start
	 * @param end
	 * @return
	 */
	public static long[] range(int start, int end) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
