/**
 * 
 */
package windowexample.handlers;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
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
public class BouncingBall {
	final Utils utils = Utils.getSingleton();
	  private static final int IMAGE_WIDTH = 100;
	  private static final int TIMER_INTERVAL = 10;

	  private static int x = 0;
	  private static int y = 0;

	  private static int directionX = 1;
	  private static int directionY = 1;

	  private static Canvas canvas;

	// Method called to run the class
	public void run() {
		p("Starting run of BouncingBall at " + new Date());
		doWork();
		p("Finished run of BouncingBall at " + new Date());
	}
	



		  public static void animate() {
		    x += directionX;
		    y += directionY;

		    // Determine out of bounds
		    Rectangle rect = canvas.getClientArea();
		    if (x < 0) {
		      x = 0;
		      directionX = 1;
		    } else if (x > rect.width - IMAGE_WIDTH) {
		      x = rect.width - IMAGE_WIDTH;
		      directionX = -1;
		    }
		    if (y < 0) {
		      y = 0;
		      directionY = 1;
		    } else if (y > rect.height - IMAGE_WIDTH) {
		      y = rect.height - IMAGE_WIDTH;
		      directionY = -1;
		    }

		    // Force a redraw
		    canvas.redraw();
		  }
		  
		  public void doWork() {
		    final Shell shell = new Shell();
		    final Display display = shell.getDisplay();
		    shell.setText("Animator");

		    shell.setLayout(new FillLayout());
		    canvas = new Canvas(shell, SWT.NO_BACKGROUND);
		    canvas.addPaintListener(new PaintListener() {
		      public void paintControl(PaintEvent event) {
		        // Draw the background
		        event.gc.fillRectangle(canvas.getBounds());
		        event.gc.setBackground(shell.getDisplay().getSystemColor(SWT.COLOR_RED));
		        event.gc.fillOval(x, y, IMAGE_WIDTH, IMAGE_WIDTH);
//		        // Create the image to fill the canvas
//		        Image image = new Image(shell.getDisplay(), canvas.getBounds());
//		        // Set up the offscreen gc
//		        GC gcImage = new GC(image);
//
//		        gcImage.setBackground(event.gc.getBackground());
//		        gcImage.fillRectangle(image.getBounds());
//		        gcImage.setBackground(shell.getDisplay().getSystemColor(SWT.COLOR_RED));
//		        gcImage.fillOval(x, y, IMAGE_WIDTH, IMAGE_WIDTH);
//
//		        // Draw the offscreen buffer to the screen
//		        event.gc.drawImage(image, 0, 0);
//
//		        image.dispose();
//		        gcImage.dispose();
		      }
		    });

		    
		    shell.open();
		    Runnable runnable = new Runnable() {
		      public void run() {
		        animate();
		        display.timerExec(TIMER_INTERVAL, this);
		      }
		    };
		    display.timerExec(TIMER_INTERVAL, runnable);

		    while (!shell.isDisposed()) {
		      if (!display.readAndDispatch()) {
		        display.sleep();
		      }
		    }
		    // Kill the timer
		    display.timerExec(-1, runnable);

		  }
		

	// Utility method for quick printing to console
	void p(Object o) {
		utils.log(o);
	}

}
