/**
 * 
 */
package ancillary;
import graphs.Graph;
import graphs.MultiGraph;
import graphs.ScalarSequence;
import graphs.XSequence;

import java.util.Date;
import java.util.Iterator;
import java.util.function.DoubleFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.IntToDoubleFunction;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import math.Fibs;

import org.pv.core.Utils;
/**
 * @author Main
 * Calculate P(f,T,w,x,n)=Prod(1..n)of f(T^r(x)) given input stream of n,x or w 
 */
public class P {
	final Utils utils = Utils.getSingleton();
	long nIterations=1;
	
	class BaseMap implements IntToDoubleFunction{
		double period=1;
		double rotationNumber=0.5*(Math.sqrt(5)-1);
		double initialCondition=0;
//		double multiplier=Math.PI;
		double currentValue;
		public void reset() {currentValue=initialCondition;}
		@Override
		public double applyAsDouble(int value) {
			currentValue+=rotationNumber;
			if (currentValue>=period) {currentValue-=period;}
			return currentValue;
		}
	};
	BaseMap baseMap=new BaseMap();
	DoubleUnaryOperator baseToFibre=new DoubleUnaryOperator() {
		@Override
		public double applyAsDouble(double x) {
			return 2*Math.sin(Math.PI*x);
		}
	};
	IntToDoubleFunction prod=new IntToDoubleFunction() {
		
		@Override
		public double applyAsDouble(int n) {
			double result=1;
			for (int i=1;i<=n;i++) {result*=baseToFibre.applyAsDouble(baseMap.applyAsDouble(i));}
			return result;
		}
	};

	// Method called to run the class
	public void run() {
		p("Starting run of P at " + new Date());
//		new Graph().add(x,new ScalarSequence(calcOverInitialCondition(x))).display();
//		new Graph().add(new ScalarSequence(calcOverOrbit(500))).display();
		
//		XSequence x = new XSequence(0,2,200);
//		XSequence w = new XSequence(0.5,0.9,200);
		MultiGraph mg = new MultiGraph(3);
//		For.intOf(1,65).peek(n->nIterations=n).forEach(n->mg.add(new Graph(200).add(x,new ScalarSequence(calcOverInitialCondition(x)))));
//		For.intOf(1,65).peek(n->nIterations=n).forEach(n->mg.add(new Graph(200).add(w,new ScalarSequence(calcOverRotationNumber(w).map(z->Math.log(Math.abs(z)+1))))));
		For.intOf(5,25).map(n->Fibs.get(n)).peek(n->p("Processing:"+n)).forEach(n->mg.add(new Graph(200).add(new ScalarSequence(calcOverOrbit(n)))));
		mg.display();
		p("Finished run of P at " + new Date());
	}
	
	DoubleStream calcn(IntStream n) {
		return n.mapToDouble(prod);
	}
	double[] calcOverOrbit(int n) {
		double[] res = new double[n+1];
		res[0]=1;
		baseMap.reset();
		IntStream.rangeClosed(1, n).forEach(m->{
			double factor=baseToFibre.applyAsDouble(baseMap.applyAsDouble(m));
			if (m==1) {res[m]=factor;} else {res[m]=res[m-1]*factor;}
		});
	return res;
	}
	DoubleStream calcOverOrbit(IntStream subSequence) {
		return subSequence.mapToDouble(n->{
			baseMap.reset();
			double result=1;
			for (int i=1;i<=n;i++) {result*=baseToFibre.applyAsDouble(baseMap.applyAsDouble(i));}
			return result;
		});
	}
	DoubleStream calcOverOrbitSegments(IntStream subSequence) {
		int[] lastIndex = new int[]{1};
		double[] lastResult = new double[]{1};
		return subSequence.mapToDouble(n->{
//			baseMap.reset();
			double result=lastResult[0];
			for (int i=lastIndex[0]+1;i<=n;i++) {result*=baseToFibre.applyAsDouble(baseMap.applyAsDouble(i));}
			lastIndex[0]=n;lastResult[1]=result;
			return result;
		});
	}
	DoubleStream calcOverInitialCondition(ScalarSequence x) {
		return x.stream().map(z->{
			double result=1;
			baseMap.initialCondition=z;
			baseMap.reset();
			for (int i=1;i<=nIterations;i++) {result*=baseToFibre.applyAsDouble(baseMap.applyAsDouble(i));}
			return result;
		});
	}
	DoubleStream calcOverRotationNumber(ScalarSequence w) {
		return w.stream().map(z->{
			double result=1;
			baseMap.rotationNumber=z;
			baseMap.reset();
			for (int i=1;i<=nIterations;i++) {result*=baseToFibre.applyAsDouble(baseMap.applyAsDouble(i));}
			return result;
		});
	}
	

	// Utility method for quick printing to console
	void p(Object o) {
		utils.log(o);
	}

}
