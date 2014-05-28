package ancillary;
/**
 * 
 */

import java.util.Date;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import org.pv.core.Utils;

/**
 * @author Paul
 *
 */
public class For {
	final static Utils utils = Utils.getSingleton();
	IntStream intStream; 
	int INT=0,LONG=1,DOUBLE=2;
	int myType=INT;
	public For(IntStream s) {intStream=s;myType=INT;}

	// Method called to run the class
	public void run() {
		p("Starting run of For at " + new Date());
		// TODO insert code here
		p("Finished run of For at " + new Date());
	}
	public static IntStream intOf(int startInclusive,int endExclusive) {
		return IntStream.range(startInclusive,endExclusive);
	}
	public static IntStream ints(int startInclusive,int endInclusive) {
		return IntStream.range(startInclusive,endInclusive);
	}
	public static IntStream intOfInc(int startInclusive,int endInclusive) {
		return IntStream.range(startInclusive,endInclusive);
	}
	public static IntStream intOf(int endExclusive) {
		return IntStream.range(0,endExclusive);
	}
	public static IntStream intOfInc(int endInclusive) {
		return IntStream.range(0,endInclusive);
	}
	public static LongStream longOf(long startInclusive,long endExclusive) {
		return LongStream.range(startInclusive,endExclusive);
	}
	public static LongStream longOfInc(long startInclusive,long endInclusive) {
		return LongStream.range(startInclusive,endInclusive);
	}
	public static LongStream longOf(long endExclusive) {
		return LongStream.range(0,endExclusive);
	}
	public static LongStream longOfInc(long endInclusive) {
		return LongStream.range(0,endInclusive);
	}

	// Utility method for quick printing to console
	void p(Object o) {
		utils.log(o);
	}


}
