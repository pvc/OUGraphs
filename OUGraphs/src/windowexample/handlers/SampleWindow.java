/**
 * 
 */
package windowexample.handlers;
import java.util.Date;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.pv.core.Utils;


/**
 * @author PV
 *
 */
public class SampleWindow extends ApplicationWindow {
	static final Utils utils = Utils.getSingleton();
	final ApplicationWindow window;
	/**
	 * @param parentShell
	 */
	public SampleWindow() {
		super(null);
		window=this;
	}
	public SampleWindow(Shell parentShell) {
		super(parentShell);
		window=this;
	}


	// Method called to run the class
	public void run() {
		p("Starting run of SampleWindow at " + new Date());
		doWork();
		p("Finished run of SampleWindow at " + new Date());
	}

	/**
	 * 
	 */
	private void doWork() {
//		setParentShell(utils.getShell());
		setBlockOnOpen(true); 
		int rc=open();
	
		
//		open();
//		int rc=Window.OK; // Ok=0, CANCEL=1
//		MessageDialog.openInformation(getParentShell(), "What happened?", "Window closed with code="+rc);
		p("Exit rc="+rc);
		
	}
	
/* Window methods */
	/* (non-Javadoc)
	 * @see org.eclipse.jface.window.ApplicationWindow#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	@Override
	// Called before shell creation
	protected void configureShell(Shell shell) {
//		this.setParentShell(utils.getShell());
		this.addMenuBar(); 
		
		this.addToolBar(getDefaultOrientation());
		this.addCoolBar(getDefaultOrientation());
		this.addStatusLine();
//		this.setShellStyle(SWT.CLOSE|SWT.MIN|SWT.MAX|SWT.RESIZE);
//		this.setShellStyle(SWT.SHELL_TRIM|SWT.V_SCROLL|SWT.H_SCROLL);  // or DIALOG_TRIM		
//		this.setShellStyle(SWT.V_SCROLL|SWT.H_SCROLL); 	
		
		Rectangle rect = Display.getCurrent().getPrimaryMonitor().getBounds();
//		p(rect);
		int width=400;int height=450; // must be 450 if using tray
		shell.setSize (width,height);
//		shell.setLocation(((rect.width-width)/2),(rect.height-height)/2); //center
//		shell.setBounds(0,0 , 400, 300);
		shell.setMinimumSize(200,450); // if using tray
		shell.setText ("Shell Title");
		
		setupMenu(window);
		super.configureShell(shell);
	}
	
	/**
	 * 
	 */
	private void setupSWTMenu(final Shell shell) {
		Menu bar = new Menu (shell, SWT.BAR);
		shell.setMenuBar (bar);
		MenuItem fileItem = new MenuItem (bar, SWT.CASCADE);
		fileItem.setText ("&File");
		Menu submenu = new Menu (shell, SWT.DROP_DOWN);
		fileItem.setMenu (submenu);
		MenuItem item = new MenuItem (submenu, SWT.PUSH);
		item.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				p("Menu item clicked");
				// this won't work with JFace window, since the class intercepts close events
//				((MenuItem)e.item).getMenu().getShell().close(); // safe way
//				shell.close(); //short way
				window.close();
			}
		});
		item.setAccelerator (SWT.MOD1 + 'F');
		item.setText("Exit");
		
	}
	/* (non-Javadoc)
	 * @see org.eclipse.jface.window.Window#createContents(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createContents(Composite parent) {
//		p("Content container: "+parent); // the shell
//		p("Layout: "+parent.getLayout()); //ApplicationWindowLayout
//		setupSWTMenu(parent.getShell());
		
		Composite contents = new Composite(parent, SWT.NONE);
//		contents.setLayoutData(new GridData(GridData.FILL_BOTH));
		
//		GridLayout layout = new GridLayout(2, true);
//		layout.marginHeight = 50;
//		layout.marginWidth = 50;
//		layout.verticalSpacing = 50;
//		contents.setLayout(layout);
		
		Label l1 = new Label(contents, SWT.NONE);
		l1.setText("Label 1");
		Text t1 = new Text(contents,SWT.BORDER);
		t1.setText("Initial String");
		
		Label l2 = new Label(contents, SWT.NONE);
		l2.setText("My much longer Label 2");
		Button b2 = new Button(contents,SWT.PUSH);
		b2.setText("Push Me");
		GridDataFactory.swtDefaults().applyTo(b2);
		
		Label l3 = new Label(contents, SWT.NONE);
		l3.setText("Label 3");
		Spinner spinner = new Spinner (contents, SWT.BORDER);
		spinner.setMinimum(0);
		spinner.setMaximum(1000);
		spinner.setSelection(500);
		spinner.setIncrement(1);
		spinner.setPageIncrement(100);
		GridDataFactory.swtDefaults().applyTo(spinner);
		
		Label l4 = new Label(contents, SWT.NONE);
		l4.setText("Label 4");
		final ProgressBar bar = new ProgressBar (contents, SWT.SMOOTH);
//		Rectangle clientArea = contents.getClientArea ();
//		bar.setBounds (clientArea.x, clientArea.y, 200, 32);
		
		final Display display=contents.getDisplay();
		display.timerExec(100, new Runnable() {
			int i = 0;
			public void run() {
				if (bar.isDisposed()) return;
				bar.setSelection(i++);
				if (i <= bar.getMaximum()) display.timerExec(100, this);
			}
		});
		
		
//		GridLayoutFactory.swtDefaults().generateLayout(contents);  // single column, margins 5
//		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(true).generateLayout(contents);  // no margins
		GridLayoutFactory.swtDefaults().numColumns(2).generateLayout(contents);  
		
		// slm does not exist before shell open, or after closed, so only valid during active
		// use this.setStatus("") in ApplicationWindow for setMessage - but no way to set error message!
		this.getStatusLineManager().setErrorMessage("World in Error");
		this.getStatusLineManager().setMessage("Hello World");  // error trumps normal
//		this.getStatusLineManager().setErrorMessage(""); // leaves normal message
		
//		super.createContents(parent);
//		return new Composite(parent, SWT.NONE);
		return contents;
		
	}
	
	

	/**
	 * @param win
	 */
	private void setupMenu(ApplicationWindow win) {
		IMenuManager mainMenu = win.getMenuBarManager(); 
		MenuManager menu1 = new MenuManager("Menu &1", "1");
		menu1.add(new Action("Action 1") {});
		mainMenu.add(menu1);
		MenuManager menu2 = new MenuManager("Menu &2", "2");
		menu2.add(new Action("Action 2") {});
		menu1.add(menu2);
		mainMenu.add(menu2);

	}
	// Utility method for quick printing to console
	void p(Object o) {
		utils.log(o);
	}

}
