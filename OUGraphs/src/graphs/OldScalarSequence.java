/**
 * 
 */
package graphs;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.DoubleStream;

import org.pv.core.Utils;

/**
 * @author PV
 *
 */
public class OldScalarSequence {
	final Utils utils = Utils.getSingleton();
	int index=0; // pointer to next pos afterend of population of seq (difft from capacity=x.length), returned by getsize
	final int capacity;
	final double[] x;
	
	double min=Double.NEGATIVE_INFINITY,max=Double.POSITIVE_INFINITY;int minAt=0,maxAt=0;
	boolean validStats=false;
	public OldScalarSequence() {
		capacity=100;
		x=new double[capacity];
	}
	public OldScalarSequence(int size) {
		capacity=size;
		x=new double[capacity];
	}
	
	/**
	 * @param array
	 */
	public OldScalarSequence(double[] array) {
		capacity=array.length;
		index=capacity;
		x=array;
		calcStats();
	}
	public void add(double x) {
		this.x[index]=x;
//		validStats=false;
		if (index==0) {min=max=x;}
		if (x<min) {min=x;minAt=index;} else if (x>max) {max=x;maxAt=index;} 
		index++;
	}
	public double get(int n) {return x[n];}
	public double[] getArray() {return x;}
	public int getSize() {return index;}

	// Utility method for quick printing to console
	void p(Object o) {
		utils.log(o);
	}
	
	public String toString() {
		return "Points: "+(index)+"; range=["+min+","+max+"]"; 
	}
	public void dump() {
		for (int n=0;n<index;n++) {
			p(n+": "+get(n));
		}
	}
	/**
	 * @param logger
	 */
	public void dumpRange() {
		p(getSize()+" values (indexed from 0)");
		p("Min: "+min+" at index: "+minAt);
		p("Max: "+max+" at index: "+maxAt);
		
	}
	/**
	 * @param i
	 * @param j
	 */
	public void dump(int i, int j) {
		for (int n=i;n<=j;n++) {
			p(n+","+get(n));
		}
			
		
	}
	/**
	 * @param d
	 * @param e
	 */
	public void dump(double start, double end) {
		dump((int)start,(int)end);
	}
	/**
	 * @param d
	 * @param e
	 */
	public int max2(int first, int last) {
//		return max((int)first,(int)last);
		double max=get(first);int inx=first;
		for (int n=(int)first+1;n<=(int)last;n++) {
			double temp=get(n);
			if (temp>max) {max=temp;inx=n;}
		}
		return inx;
	}
	public void calcStats() {
//		return max((int)first,(int)last);
		if (index==0) {return;}
		min=max=x[0];
//		p("stats:index="+index+";x.len="+x.length);
		for (int n=1;n<index;n++) {
			p("loop:"+n);
			double temp=x[n];
			if (temp>max) {max=temp;maxAt=n;}
			else if (temp<min) {min=temp;minAt=n;}
		}
		validStats=true;
	}
	/**
	 * @param i
	 * @param j
	 * @return 
	 */
	public ScalarSequence sub(int first, int last) {
		ScalarSequence sub = new ScalarSequence(last-first+1);
		for (int n=first;n<=last;n++) {
			sub.add(get(n));
		}
		return sub;
	}
	/**
	 * @param n
	 * @param calc
	 */
	public void set(int n, double d) {
		x[n]=d;
		if (d<min) {min=d;minAt=n;} else if (d>max) {max=d;maxAt=n;} 
//		validStats=false;
	}
	/**
	 * @return
	 */
	public double getMin() {
//		if (!validStats) {calcStats();}
		return min;
	}
	public double getMax() {
//		if (!validStats) {calcStats();}
		return max;
	}
	/**
	 * 
	 */
	public DoubleStream stream() {
		return Arrays.stream(x, 0, index);
	}

}
