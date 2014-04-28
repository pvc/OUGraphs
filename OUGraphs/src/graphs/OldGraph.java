/**
 * 
 */
package graphs;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.pv.core.Utils;


/**
 * @author PV
 *
 */
public class OldGraph {
	Canvas canvas;
	private static final int INTERVAL = 100;
	protected static final int POINTSONLY = 1;
	protected static final int LINEGRAPH = 0;
	final Utils utils = Utils.getSingleton();
	final ArrayList<ScalarSequence> yvals=new ArrayList<ScalarSequence>(2);
	final ArrayList<ScalarSequence> xvals=new ArrayList<ScalarSequence>(2);
	final ArrayList<int[]> lines=new ArrayList<int[]>(2);
	final ArrayList<Integer> lineColours=new ArrayList<Integer>(2);
	final ArrayList<GraphString>graphStrings=new ArrayList<GraphString>(2);
	private boolean hasTitle=false;
	Image graphic;
	Shell shell;
	int[] shape;
//	XYSet xy;
	final Point size;
	
	protected int graphType=LINEGRAPH;
	final private Display display=Display.getCurrent();
	private String title="";
	private boolean trace=true;
	private int defaultColour=SWT.COLOR_BLUE;
	private double yScaleMin=Double.NaN;
	private double yScaleMax=Double.NaN;
	private double xScaleMin=Double.NaN;
	private double xScaleMax=Double.NaN;
	private boolean yScaleMinSet=false;
	private boolean yScaleMaxSet=false;
	private boolean xScaleMinSet=false;
	private boolean xScaleMaxSet=false;
	boolean drawAxes=true;
	int[]xaxis;int[]yaxis; // defined via side-effect to avoid additional class reqmt
	int[] xticks;int[] yticks;
	double[] xtickVals;double[] ytickVals;
	private boolean xTicksSet=false;
	private double xTickUnit=1;
	private boolean yTicksSet=false;
	private double yTickUnit=1;
	private int xTickOrigin;
	private int yTickOrigin;
	private boolean xMinorTicksSet=false; private double xMinorTickUnit=1;
	private boolean yMinorTicksSet=false; private double yMinorTickUnit=1;
	private int xMinorTickOrigin;	private int[] xMinorTicks;
	private int yMinorTickOrigin;	private int[] yMinorTicks;
	private int defaultLineWidth=1;
	
	
	
	/**
	 * @param i
	 * @param j
	 */
	public OldGraph() {
		size=new Point(500,500);
	}
	public OldGraph(int x, int y) {
		size=new Point(x,y);
	}


	/**
	 * @param npoints
	 */
	public OldGraph(int npoints) {
		size=new Point(npoints,npoints);
	}
	/**
	 * @param size2
	 * @return
	 */
	
	// Method called to run the class
	public void run() {
		p("Starting run of TestShell at " + new Date());
		
		p("Finished run of TestShell at " + new Date());
	}
	
	
	
	void plot(GC gc) {
//		p("Advanced="+gc.getAdvanced()+"; AA="+gc.getAntialias()+"; SWT.ON="+SWT.ON);
//		Color blue = display.getSystemColor(SWT.COLOR_BLUE);
		gc.setBackground(display.getSystemColor(SWT.COLOR_WHITE)); 
		if (hasTitle) {gc.drawString(title, 0,10);};
		for (Iterator<GraphString> iterator = graphStrings.iterator(); iterator.hasNext();) {
			iterator.next().draw(gc);
		}
		for (int n=0;n<yvals.size();n++) {  //must execute BEFORE drawaxes!
			lines.add(fit(xvals.get(n),yvals.get(n)));
		}
		if (drawAxes) {
			gc.setForeground(display.getSystemColor(SWT.COLOR_BLACK));
			if (xaxis!=null) {
				gc.drawLine(xaxis[0],xaxis[1],xaxis[2],xaxis[3]); // x1,y1,x2,y2
				if (xTicksSet) {
					for (int i = 0; i < xticks.length; i++) {
						int pos=xticks[i];
						if (i!=xTickOrigin) {
							String label=""+xtickVals[i];
							Point rect=gc.textExtent(label);
							gc.drawText(label, pos-(rect.x/2), xaxis[1]+10);
							gc.drawLine(pos,xaxis[1],pos,xaxis[1]+10);
						}
					}
				}
				if (xMinorTicksSet) {
					for (int i = 0; i < xMinorTicks.length; i++) {
						int pos=xMinorTicks[i];
						if (i!=xMinorTickOrigin) {
						gc.drawLine(pos,xaxis[1],pos,xaxis[1]+5);
						}
					}
				}
			}		
			if (yaxis!=null) {gc.drawLine(yaxis[0],yaxis[1],yaxis[2],yaxis[3]);
			if (yTicksSet) {
				for (int i = 0; i < yticks.length; i++) {
					int pos=yticks[i];
					if (i!=yTickOrigin) {
//						p(""+i+":"+yaxis[0]+","+pos);
						String label=""+ytickVals[i];
						Point rect=gc.textExtent(label);
						gc.drawLine(yaxis[0],pos,yaxis[0]-10,pos);
						gc.drawText(label,yaxis[0]-rect.x-10,pos-(rect.y/2));
					
					}
				}
			}
			if (yMinorTicksSet) {
				for (int i = 0; i < yMinorTicks.length; i++) {
					int pos=yMinorTicks[i];
					if (i!=yMinorTickOrigin) {
					gc.drawLine(yaxis[0],pos,yaxis[0]-5,pos);
					}
				}
			}
//			gc.drawLine(yaxis[0],100,yaxis[0]-10,100);
//			gc.drawText("5", yaxis[0]-10, 100);
			}	
		}
		gc.setLineWidth(defaultLineWidth);
//		gc.setForeground(blue);
		if (graphType==POINTSONLY) {
		for (int n=0;n<shape.length/2;n++) {
		gc.drawPoint(size.x/2,shape[2*n+1]);
		}
		} else {for(int n=0;n<lines.size();n++) {
			gc.setForeground(display.getSystemColor(lineColours.get(n)));
			gc.drawPolyline(lines.get(n));
			}
		}
	}
	
	
	public Image plotImage() {
		graphic=new Image(display,size.x+1,size.y+1);
		GC gc=new GC(graphic);
		gc.setAntialias(SWT.ON);
		plot(gc);
		gc.dispose();
		return graphic;
	}
	
	public Graph display() {
		shell = new Shell(display,SWT.CLOSE);
		shell.setText(title);
		shell.setLayout(new FillLayout());
		plotImage(); // sets graphic as side effect
//		canvas = new Canvas(shell,SWT.NONE);
//		canvas.setSize(size);
//		canvas.setBackgroundImage(graphic); // gets repainted to blank
//		for (int n=0;n<5;n++) {
		Label holder=new Label(shell,SWT.NONE);
		holder.setImage(graphic);  //set by plotImage
//		}
//		l.setText("Hello");
		shell.pack();
//		p(l.getSize());
//		p(graphic);
//		shell.setImage(plotImage());
		display.asyncExec(new Runnable() { //async to allow window to be non-modal

			@Override
			public void run() {
				shell.open();
//				while (!shell.isDisposed()) {
//					if (!display.readAndDispatch()) display.sleep();
//				}
				
			}});
		return this;
	}


	/**
	 * @param xy2
	 * @param size2
	 * @return
	 */
	public int[] fit(ScalarSequence x, ScalarSequence y) {
		final int NPOINTS=x.getSize();
		p("Length of plot sequence: "+NPOINTS);
		int[] line = new int[2*NPOINTS];
		final double minx,maxx,miny,maxy;
		if (yScaleMinSet) {miny=yScaleMin;} else {	miny=y.getMin();}
		if (yScaleMaxSet) {maxy=yScaleMax;} else {	maxy=y.getMax();}
		if (xScaleMinSet) {minx=xScaleMin;} else {	minx=x.getMin();}
		if (xScaleMaxSet) {maxx=xScaleMax;} else {	maxx=x.getMax();}
			
		final double xpixel=(maxx-minx)/size.x;  // 1 pixel=#x units
		final double ypixel=(maxy-miny)/size.y;
		for (int n=0;n<NPOINTS;n++) {
			line[2*n]= ((xpixel!=0)?(int) (0.5+((x.get(n)-minx)/xpixel)):size.x/2);
			line[2*n+1]=(ypixel!=0)?(int) (0.5+((maxy-y.get(n))/ypixel)):size.y/2;
//			p(line[2*n]+","+line[2*n+1]);
		}
		xaxis=yaxis=null;
		if (maxy>0 && miny<0) {xaxis=new int[]{0,(int)(0.5+maxy/ypixel),size.x,(int)(0.5+maxy/ypixel)};} 
		if (maxx>0 && minx<0) {yaxis=new int[]{(int)(0.5-minx/xpixel),0,(int)(0.5-minx/xpixel),size.y};}
		if (xTicksSet&&xaxis!=null) {
			double tick=xTickUnit/xpixel; // #pixels/tick unit
//			p("tick="+tick);
			int originx = yaxis[0];
			int tickStart=(int)Math.ceil(minx/xTickUnit);
			int tickEnd=(int)Math.floor(maxx/xTickUnit);
			int nticks=tickEnd-tickStart+1;
			xTickOrigin=-tickStart;
//			p("nticks="+nticks);
			xticks=new int[nticks];
			xtickVals=new double[nticks];
			for (int n=tickStart;n<=tickEnd;n++) {
				xticks[n-tickStart]=originx+(int)(n*tick);
				xtickVals[n-tickStart]=n*xTickUnit;
			}
			if (xMinorTicksSet) {
				tick=xMinorTickUnit/xpixel; // #pixels/tick unit
//				p("tick="+tick);
				tickStart=(int)Math.ceil(minx/xMinorTickUnit);
				tickEnd=(int)Math.floor(maxx/xMinorTickUnit);
				nticks=tickEnd-tickStart+1;
				xMinorTickOrigin=-tickStart;
//				p("nticks="+nticks);
				xMinorTicks=new int[nticks];
				for (int n=tickStart;n<=tickEnd;n++) {
					xMinorTicks[n-tickStart]=originx+(int)(n*tick);
				}
			}
		}	
		if (yTicksSet&&yaxis!=null) {
			double tick=yTickUnit/ypixel; // #pixels/tick unit
//			p("tick="+tick);
			int originy = xaxis[1];
			int tickStart=(int)Math.ceil(miny/yTickUnit);
			int tickEnd=(int)Math.floor(maxy/yTickUnit);
			int nticks=tickEnd-tickStart+1;
			yTickOrigin=-tickStart;
//			p("nticks="+nticks);
			yticks=new int[nticks];
			ytickVals=new double[nticks];
			for (int n=tickStart;n<=tickEnd;n++) {
				yticks[n-tickStart]=originy-(int)(n*tick);
				ytickVals[n-tickStart]=n*yTickUnit;
			}
			if (yMinorTicksSet) {
				tick=yMinorTickUnit/ypixel; // #pixels/tick unit
//				p("tick="+tick);
				tickStart=(int)Math.ceil(miny/yMinorTickUnit);
				tickEnd=(int)Math.floor(maxy/yMinorTickUnit);
				nticks=tickEnd-tickStart+1;
				yMinorTickOrigin=-tickStart;
//				p("nticks="+nticks);
				yMinorTicks=new int[nticks];
				for (int n=tickStart;n<=tickEnd;n++) {
					yMinorTicks[n-tickStart]=originy+(int)(n*tick);
//					p(""+n+":"+yMinorTicks[n-tickStart]);
				}
			}
		}
		p("minx="+x.getMin()+",maxx="+x.getMax()+",miny="+y.getMin()+",maxy="+y.getMax()+",xunits="+xpixel+",yunits="+ypixel);
//		for (int m=0;m<2*NPOINTS;m+=2) {if (500!=line[m]+line[m+1]) {p(""+line[m]+" "+line[m+1]);}}
		return line;
		
	}
	void p(Object o) {
		if (trace) {utils.log(o);}
	}

	public Graph add(ScalarSequence x,ScalarSequence y) {
		yvals.add(y);
		xvals.add(x);
		lineColours.add(defaultColour);
		return this;
	}
	public Graph add(ScalarSequence x,ScalarSequence y, int colour) {
		yvals.add(y);
		xvals.add(x);
		lineColours.add(colour);
		return this;
	}
	public Graph setColour(int line,int colour) {
		lineColours.set(line,colour);
		return this;
	}
	/**
	 * @param orbit
	 * @return
	 */
	public Graph add(ScalarSequence y) {
		add(new XSequence(y),y);
		return this;
		
	}
	/**
	 * @param seq
	 */
	/**
	 * @param seq
	 * @param i
	 * @param j
	 */
	public Graph add(ScalarSequence y, int first, int last) {
		ScalarSequence sub=new ScalarSequence(last-first+1);
		for (int n=first;n<=last;n++) {
			sub.add(y.get(first+n));
		}
		add(new XSequence(sub),sub);
		return this;
		
	}
	/**
	 * @param string
	 */
	public void setTitle(String title) {
		this.title=title;
		hasTitle=true;
	}
	public Graph addText(int x,int y,String text) {
		graphStrings.add(new GraphString(x,y,text,defaultColour));
		return this;
	}
	public Graph addText(int x, int y, String text, int colour) {
		graphStrings.add(new GraphString(x,y,text,colour));
		return this;
		
	}

	
	public boolean save(String path) {
		try{
//			Image graphic = new Image(canvas.getDisplay(), canvas.getBounds());
//			GC gc = new GC(graphic);
//			canvas.print(gc);
			ImageLoader loader = new ImageLoader();
			loader.data = new ImageData[] {graphic.getImageData()};
			loader.save(path, SWT.IMAGE_PNG);
//			graphic.dispose();
//			gc.dispose();

			return true;
		} catch (Exception e) {e.printStackTrace(utils.getLogger());}
		return false;
	}
	public class GraphString{
		int x,y; String text; int colour;
		public GraphString(int x,int y,String text,int colour) {
			this.x=x;this.y=y;this.text=text;this.colour=colour;
		}
		
		public void draw(GC gc) {
			gc.setForeground(display.getSystemColor(colour));
			gc.drawString(text, x, y);
		}
	}
	/**
	 * @param drawAxes2
	 */
	public Graph setAxes(boolean b){
		drawAxes=b;
		return this;
	}
	public Graph setYScale(double lower,double higher) {
		yScaleMin=lower;yScaleMinSet=true;
		yScaleMax=higher;yScaleMaxSet=true;
		return this;
	}
	/**
	 * @param i
	 * @param j
	 */
	public Graph setTicks(float xUnits, float yUnits) {
		if (xUnits!=0) {xTicksSet=true; xTickUnit=xUnits;}
		if (yUnits!=0) {yTicksSet=true; yTickUnit=yUnits;}
		return this;
		
	}
	/**
	 * @param i
	 * @param j
	 * @param k
	 * @param l
	 */
	public Graph setTicks(float xUnits, float yUnits,float xMinorUnits,float yMinorUnits) {
		if (xUnits!=0) {xTicksSet=true; xTickUnit=xUnits;}
		if (xMinorUnits!=0) {xMinorTicksSet=true; xMinorTickUnit=xMinorUnits;}
		if (yUnits!=0) {yTicksSet=true; yTickUnit=yUnits;}
		if (yMinorUnits!=0) {yMinorTicksSet=true; yMinorTickUnit=yMinorUnits;}
		return this;
	}
	/**
	 * @param i
	 */
	public Graph setDefaultLineWidth(int defaultLineWidth) {
		this.defaultLineWidth=defaultLineWidth;
		return this;
		
	}
}	
	
