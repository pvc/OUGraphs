/**
 * 
 */
package graphs;

import java.lang.reflect.Array;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.pv.core.Utils;


/**
 * @author PV
 *
 */
public class GraphOld {
	private static final int INTERVAL = 100;
	final Utils utils = Utils.getSingleton();
	final int[] shape;
	final Point size=new Point(500,500);
	public GraphOld() {
//		shape=genData(size);
//		shape=plotLyapConvergence(size);
		shape=plotOrbit(size);
	}
	/**
	 * @param size2
	 * @return
	 */
	
	// Method called to run the class
	public void run() {
		p("Starting run of TestShell at " + new Date());
		doWork();
		p("Finished run of TestShell at " + new Date());
	}


	// Utility method for quick printing to console
	/**
	 * 
	 */
	private void doWork() {

		final Shell shell = new Shell();
//		shell.setLayout(new FillLayout());
		final Display display = shell.getDisplay();
		final Canvas canvas = new Canvas(shell,SWT.NONE);
		canvas.setSize(size.x +1,size.y +1);
		

		    canvas.addPaintListener(new PaintListener() {
		        public void paintControl(PaintEvent e) {
		        	p("PaintEvent!");
		            Rectangle clientArea = canvas.getClientArea();
		            p("ClientArea="+clientArea);
		            e.gc.setBackground(display.getSystemColor(SWT.COLOR_CYAN));
//		         e.gc.fillOval(0,0,clientArea.width,clientArea.height);
//		            for (int i = 0; i < shape.length; i++) {
//						shape[i]++;
//					}
		            e.gc.drawPolyline(shape);
		            Display display = Display.getCurrent();
		            Color blue = display.getSystemColor(SWT.COLOR_BLUE);

		            e.gc.setForeground(blue);
		            e.gc.drawLine(1+(size.x)/2, 0, 1+(size.x)/2, size.y);
		            e.gc.drawLine(0,(size.y)/2, size.x, (size.y)/2);
		            p("Origin at: "+(size.x)/2);
		            
		        }
		    }); 
/*		
		Button b = new Button(shell,SWT.PUSH); b.setText("Push Me");
		b.addSelectionListener(new SelectionAdapter() {
			boolean add=true;
			Label l;
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					if (add) {
						l = new Label(shell, SWT.NONE);l.setText("Label 0");
						l.moveAbove(shell.getChildren()[0]);
					} else {	
						l.dispose();
					} 
					add=!add;
					shell.layout(true); // force relayout with new children
					shell.pack();  //adjust size 
				} catch (Exception e1) {e1.printStackTrace(utils.getLogger());}
			}
		});
		GridLayoutFactory.swtDefaults().generateLayout(shell);333333333
*/
		shell.pack();
		shell.open();
		final class Animator extends Thread {
			boolean done=false;
			public void run() {
				while (!done) {
				try {
					Thread.sleep(100);
					canvas.update();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
				p("Animator finished!");
				
			}
			public void setDone() {done=true;}
			
		}
//		Animator a = new Animator();
//		a.run();
		display.timerExec (INTERVAL, new Runnable () {
			public void run () {
				if (canvas.isDisposed ()) return;
//				 canvas.redraw (); // <-- bad, damages more than is needed
//				GC gc = new GC (canvas);
//				Point extent = gc.stringExtent (value + '0');
//				gc.dispose ();
//				canvas.redraw (10, 10, extent.x, extent.y, false);
				display.timerExec (INTERVAL, this);
			}
		});
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) display.sleep();
		}
//		a.setDone();
		
	}

	void p(Object o) {
		utils.log(o);
	}
/*
 * Generate points for irrational rotation
 */
//	public int[] genData(Point size) {
//		double x,y;
//		final int NPOINTS=100001;
//		final int[] data=new int[2*NPOINTS];
//		final double lh=(size.x-1)/2;
//		final double lw=(size.y-1)/2;
//		final double inc1=Math.PI/100;
//		final double inc2=inc1*Math.sqrt(2);
//		int n=0;
//		for (double i = 0; i < NPOINTS; i++,n++) {
//			x=Math.sin(i*inc1);
//			y=Math.sin(i*inc2);
//			data[2*n]=(int)((x+1)*lh);
//			data[1+2*n]=(int)((y+1)*lw);
////			p("x="+data[2*n]+" y="+data[1+2*n]);
//		}
//		return data;
//	}
	
	/*
	 * Points for Grebowski attractor
	 */
	public int[] genData(Point size) {
		double x,y;
		
		final int NPOINTS=501;
		final double twopiInc=twopi/(NPOINTS-1);
		final int[] data=new int[2*NPOINTS];
		final double lh=(size.x)/2;
		final double lw=(size.y)/2;
		final double xInc=((double)size.y)/(NPOINTS-1);
		final double inc1=Math.PI/100;
		final double inc2=inc1*Math.sqrt(2);
		int n=0;
		for (double i = 0; i < NPOINTS; i++,n++) {
			double theta=twopiInc*i+0.1;
			data[2*n]=(int)(i*xInc);
			x=calc(theta); //(result is normalised to +/-1)
			data[1+2*n]=(int)(((x+x0)/x0)*lh);
			p("x="+data[2*n]+" y="+data[1+2*n]);
		}
		return data;
	}
	
	final int iterations=500;
	final double x0=3;
	final double c=2*1.5;
	final double w=(Math.sqrt(5)-1)/2;
	final double twopi=2*Math.PI;
	final double twopiw=Math.PI*(Math.sqrt(5)-1);
	final double a1=0.25,b1=0.125;
	public double calc(double theta) {
		double x=x0;
		for (int n=0;n<iterations;n++) {
//			x=c*Math.tanh(x)*Math.cos(theta+(n*w)*twopi);
			x=c*Math.tanh(x)*Math.cos(theta+(n*w)*twopi)+a1*Math.cos(theta+(n*w+b1)*twopi);
		}
		return x;
	}
	
	public int[] plotLyapConvergence(Point size) {
//		p("Plotting orbit of "+x0);
		double x,y;
		
		final int NPOINTS=501;
		final double twopiInc=twopi/(NPOINTS-1);
		final int[] data=new int[2*NPOINTS];
		final double lh=(size.x)/2;
		final double lw=(size.y)/2;
		final double xInc=((double)size.y)/(NPOINTS-1);
		final double inc1=Math.PI/100;
		final double inc2=inc1*Math.sqrt(2);
//		double theta=twopiInc*i+0.1;
		double theta=0,lyap=0,lyapTot=0,lyapAvg=0;
		double x0=3;
		int n=0; x=x0;double minx=0,maxx=NPOINTS-1,miny=x,maxy=x;
		for (double i = 0; i < NPOINTS; i++,n++) {
			data[2*n]=n;
//			p("cos="+Math.cos(theta+(n*w)*twopi));
			lyap=Math.log(Math.abs(c*Math.cos(theta+(n*w)*twopi)/(Math.pow(Math.cosh(x),2))));
//			if (lyap==Double.NaN) {p("***NaN at n="+n);}
//			if (Double.isInfinite(lyap)) {p("***Infinity at n="+n);}
			lyapTot+=lyap;
			lyapAvg=lyapTot/(n+1);
			x=c*Math.tanh(x)*Math.cos(theta+(n*w)*twopi);
//			data[1+2*n]=(int)(((x+x0)/x0)*lh);
			
//			data[1+2*n]=(int)(((-1-lyapAvg)/1.5)*lh);
			data[1+2*n]=(int)(((8+lyap)/5)*lh);
//			if (n==0) {miny=maxy=lyapAvg;} else {
//			if (lyapAvg<miny) {miny=lyapAvg;} else if (lyapAvg>maxy) {maxy=lyapAvg;} 
//			}
//			if (lyapTot<miny) {miny=lyapTot;} else if (lyapTot>maxy) {maxy=lyapTot;} 
			if (n==0) {miny=maxy=lyap;} else {
				if (lyap<miny) {miny=lyap;} else if (lyap>maxy) {maxy=lyap;} 
			}
//			if (y<miny) {miny=y;} else if (y>maxy) {maxy=y;} 
//			p("x="+data[2*n]+" y="+data[1+2*n]);
//			p("lyapTot="+lyapTot);
//			p("lyap="+lyap);
		}
		p("Points: "+NPOINTS+"; x=["+minx+","+maxx+"]; y=["+miny+","+maxy+"]");
		return data;
	}
	
	/*
	 * Plot last NPOINTS2 of orbit after calculating first NPOINTS
	 */
	public int[] plotOrbit(Point size) {
//		p("Plotting orbit of "+x0);
		double x,y;
		
		final int NPOINTS=1021;
		final int[] data=new int[2*NPOINTS];
		double xInc=size.x/(NPOINTS-1);
		final double lh=(size.x)/2;
		final double inc1=Math.PI/100;
//		double theta=twopiInc*i+0.1;
		double x0=3;double theta=0;
		int n=0; x=x0;double minx=0,maxx=NPOINTS-1,miny=x,maxy=x;
		for (double i = 0; i < NPOINTS; i++,n++) {
			data[2*n]=(int)(n*xInc);
			data[1+2*n]=(int)(((x+c)/c)*lh);
			if (n==0) {miny=maxy=x0;} else {
			if (x<miny) {miny=x;} else if (x>maxy) {maxy=x;} 
			}
//			x=c*Math.tanh(x)*Math.cos(theta+(n*w)*twopi);
			x=c*Math.tanh(x)*Math.cos(theta+(n*w)*twopi)+a1*Math.cos(theta+(n*w+b1)*twopi);
			p("x="+data[2*n]+" y="+data[1+2*n]);
		}
		p("Points: "+NPOINTS+"; x=["+minx+","+maxx+"]; y=["+miny+","+maxy+"]");
		int NPOINTS2=101;
		final int[] data2=new int[2*NPOINTS2];
		System.arraycopy(data, 2*(NPOINTS-NPOINTS2), data2, 0, 2*NPOINTS2);
		xInc=size.x/(NPOINTS2-1);
		for (n=0;n<NPOINTS2;n++) {data2[2*n]=(int)(n*xInc);}
		return data2;
	}
	

}
