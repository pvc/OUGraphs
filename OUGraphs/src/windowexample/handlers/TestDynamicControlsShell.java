/**
 * 
 */
package windowexample.handlers;
import java.util.Date;

import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.pv.core.Utils;


/**
 * @author PV
 *
 */
public class TestDynamicControlsShell {
	final Utils utils = Utils.getSingleton();

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
		Display display = shell.getDisplay();
		Label l1 = new Label(shell, SWT.NONE);l1.setText("Label 1");
		Label l2 = new Label(shell, SWT.NONE);l2.setText("Label 2");
		Label l3 = new Label(shell, SWT.NONE);l3.setText("Label 3");
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
		GridLayoutFactory.swtDefaults().generateLayout(shell);
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) display.sleep();
		}
	}

	void p(Object o) {
		utils.log(o);
	}

}
