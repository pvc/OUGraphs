/**
 * 
 */
package graphs;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.imageio.ImageIO;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.printing.Printer;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.pv.core.Utils;

import ancillary.HistoryManager;
import ancillary.ImageConverter;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

/**
 * @author PV
 *
 */
public class OldMultiGraph {
	final static Utils utils = Utils.getSingleton();
	final ArrayList<Graph> graphs=new ArrayList<Graph>(10);
	final LazyList<Image,Graph> graphImages=new LazyList<Image,Graph>(graphs);
	int ncols=3;
	Composite parent;
	private boolean drawAxes=false;
	final static HistoryManager history = HistoryManager.getInstance();
	final static String HISTORY_KEY="MG001";

	/**
	 * @param i
	 */
	public OldMultiGraph(int ncols) {
		this.ncols=ncols;
	}
	public OldMultiGraph() {
		ncols=3;
	}

	// Method called to run the class
	public void run() {
		p("Starting run of TestImages at " + new Date());
		display();
		p("Finished run of TestImages at " + new Date());
	}

	public void add(Graph g) {
		graphs.add(g);
		g.setAxes(drawAxes);
	}

	public void save(String fileName) {
		FileOutputStream outStream;
		com.itextpdf.text.Document document;
		final Shell shell = new Shell();
		try {
			outStream = new FileOutputStream(fileName);
			
			document = new Document(PageSize.A4, 70, 50, 70, 50);  //margins in points
			com.itextpdf.text.pdf.PdfWriter.getInstance(document, outStream);
			document.open(); // open the document to add data.
//			p("Adding images:"+new Date());
			PdfPTable table = new PdfPTable(ncols);
			table.setWidthPercentage(100f);
//			table.getDefaultCell().setPadding(10f);
			
//			table.addCell(com.itextpdf.text.Image.getInstance(ImageConverter.convertToAWT(graphImages.get(0).getImageData()),null));
//			BufferedImage awtImage = ImageConverter.convertToAWT(graphImages.get(0).getImageData());
//			ImageIO.write(awtImage, "png", new File("C:/OUGraphs/Test.png"));
//			com.itextpdf.text.Image pdfImage = com.itextpdf.text.Image.getInstance("C:/OUGraphs/Test.png");
			for (int n=0;n<graphs.size();n++) {
				com.itextpdf.text.Image graph = com.itextpdf.text.Image.getInstance(ImageConverter.convertToAWT(graphImages.get(n).getImageData()),null);
//				PdfPCell cell = new PdfPCell(graph);
//	            cell.setPadding(0);
	            table.addCell(graph);
			}
			int overflow=graphs.size()%ncols;
			if (overflow>0) {
				for (int n=0;n<ncols-overflow;n++) {
					table.addCell("(Blank)");
				}
			}
//			document.add(pdfImage);
			document.add(table);
			p("Added:"+new Date());
			document.close();
		} catch (DocumentException|IOException e) {
			e.printStackTrace(utils.getLogger()); return;
		} 

		//	try {
		////		Printer prt = new Printer();
		//		int start=0;int blockSize=35;
		//		for (int n=0;n<graphs.size();n+=blockSize) {
		//		int end=n+blockSize-1; if (end>=graphs.size()) {end=graphs.size()-1;}	
		//		Composite container = new Composite(shell, SWT.NONE);
		//		container.setLayout(new GridLayout(ncols,true)); 
		//		buildBlock(container, n, end);
		//		container.pack();
		////		p(container.getBounds());
		//		Image graphic = new Image(shell.getDisplay(), container.getBounds());
		//		GC gc = new GC(graphic);
		//		container.print(gc);
		//		
		//		ImageData rawImage=graphic.getImageData();
		//		p("Image="+rawImage.width+'/'+rawImage.height);
		//		BufferedImage awtImage = ImageConverter.convertToAWT(rawImage);
		////		com.itextpdf.text.Image pdfImage = com.itextpdf.text.Image.getInstance(rawImage.width,rawImage.height,1,8,rawImage.data);
		//		com.itextpdf.text.Image pdfImage = com.itextpdf.text.Image.getInstance(awtImage,null);
		//		pdfImage.scalePercent(40);
		//		document.add(pdfImage); // adding the image to pdf document.
		//		document.newPage();
		////		ImageLoader loader = new ImageLoader();
		////		loader.data = new ImageData[] {graphic.getImageData()};
		//////		String saveFile="C:/Empty/test.png";
		////		loader.save(fileName+n+".png", SWT.IMAGE_PNG);
		////		p("Saved to: "+fileName+n+".png");
		//		container.dispose();
		//		graphic.dispose();
		//		gc.dispose();
		//		}
		//	} catch (Exception e1) {e1.printStackTrace(utils.getLogger());	
		//		e1.printStackTrace(utils.getLogger());
		//	} finally {
		//		shell.dispose();
		//		document.close();
		//	}	
		////		outStream.close();
	}


	public void display() {
		final Shell shell = new Shell();
		final Display display = shell.getDisplay();
		shell.setLayout(new GridLayout()); //1 col
		GridData gridData;
		final Composite tools=new Composite(shell,SWT.NONE);
		gridData = new GridData(SWT.FILL, SWT.BOTTOM, false, false);
		tools.setLayoutData(gridData);
		RowLayout rl = new RowLayout(); rl.pack=true;rl.center=true;
		tools.setLayout(rl);
		final Label spLabel=new Label(tools,SWT.NONE);
		spLabel.setText("Columns: ");
		final Spinner sp=new Spinner(tools,SWT.NONE);
		sp.setSelection(ncols);
		//			sp.setLayoutData(rowData);
		final Button saveButton=new Button(tools,SWT.PUSH);
		saveButton.setText("Save");
		final Combo savePath=new Combo(tools,SWT.BORDER|SWT.RIGHT);
//		savePath.setText("C:/OUGraphs/Latest.pdf");
		RowData rowdata=new RowData(150,SWT.DEFAULT);
		savePath.setLayoutData(rowdata);
		
		String[] saveHistory = history.getHistory(HISTORY_KEY);
		savePath.setItems(saveHistory);
		if (saveHistory.length>0) {savePath.setText(saveHistory[0]);}
		final Button browseButton=new Button(tools,SWT.PUSH);
		browseButton.setText("Browse");
		browseButton.addSelectionListener(new SelectionAdapter() {
		      @Override
		      public void widgetSelected(SelectionEvent e) {
		        Shell shell = parent.getShell();
		        FileDialog fileDialog = new FileDialog(shell,SWT.SAVE);
		        fileDialog.setText("Select File");
		        fileDialog.setFileName("Latest.pdf");
		        fileDialog.setFilterPath("C:/OUGraphs");
//		        fileDialog.setFilterExtensions(new String[] { "*.txt" });
//		        fileDialog.setFilterNames(new String[] { "Textfiles(*.txt)" });
		        String selected = fileDialog.open();
		        if (selected!=null) {savePath.setItems(history.addHistory(HISTORY_KEY, selected));savePath.setText(selected);}
		      }
		    });
		
		

		//			Image image1 = display.getSystemImage(SWT.ICON_WORKING);
		//			Image image2 = display.getSystemImage(SWT.ICON_QUESTION);
		//			Image image3 = display.getSystemImage(SWT.ICON_ERROR);

		//			shell.setLayout(new RowLayout(SWT.VERTICAL));
		final ScrolledComposite scrollComposite = new ScrolledComposite(shell, SWT.V_SCROLL |SWT.H_SCROLL | SWT.BORDER);
		gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		scrollComposite.setLayoutData(gridData);
		//			gridData.widthHint = SWT.DEFAULT;
		//			gridData.heightHint = SWT.DEFAULT;
		//			gridData = new GridData(SWT.LEFT, SWT.BOTTOM, false, false);
		//			saveButton.setLayoutData(gridData);


		//			final Composite content = new Composite(shell, SWT.NONE);
		//			content.setLayout(new RowLayout(SWT.VERTICAL));
		//			
		parent = new Composite(scrollComposite, SWT.NONE);
		parent.setLayout(new GridLayout(ncols,true)); // equal cell widths
		//			RowLayout layout = new RowLayout(SWT.HORIZONTAL);
		//			layout.wrap = true;
		//			parent.setLayout(layout);

		//			parent.setLayout(new RowLayout(SWT.VERTICAL));
		//			final Composite parent = new Composite(holder, SWT.NONE);

		//			for(int i = 0; i <= 50; i++) {
		//				Label label = new Label(parent, SWT.NONE);
		//				if (i % 3 == 0) label.setImage(image1);
		//				if (i % 3 == 1) label.setImage(image2);
		//				if (i % 3 == 2) label.setImage(image3);
		////				label.setText("Hello"); Win cant do both
		//			}
		//			p("Adding graphs");
		buildBlock(parent,0,graphs.size()-1);
		//			for (int n=0;n<graphs.size();n++) {
		////				p("Adding graph:"+n);
		//				Label graph = new Label(parent, SWT.NONE);
		//				graph.setImage(graphImages.get(n));
		////				gridData = new GridData(SWT.LEFT, SWT.BOTTOM, false, false);
		////				saveButton.setLayoutData(gridData);
		//			}
		//			parent.pack(); //ensure scrolledcomposite has content size

		sp.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				ncols=sp.getSelection();
				//					p("new cols="+ncols);
				((GridLayout)parent.getLayout()).numColumns=ncols;
				//					shell.layout(true);	
				//					display.update();
				//					shell.redraw();
				parent.pack();
				//					parent.redraw();
				//					parent.update();

			}
		});
		saveButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try {
					String saveFile="C:/OUGraphs/Latest.pdf";
					save(saveFile);
					p("Saved to: "+saveFile);
					
//					Image graphic = new Image(parent.getDisplay(), parent.getBounds());
//					GC gc = new GC(graphic);
//					parent.print(gc);
//					String saveFile="C:/Empty/test.png";
//					ImageLoader loader = new ImageLoader();
//					loader.data = new ImageData[] {graphic.getImageData()};
//					loader.save(saveFile, SWT.IMAGE_PNG);
//					p("Saved to: "+saveFile);
//					graphic.dispose();
//					gc.dispose();
				} catch (Exception e1) {e1.printStackTrace(utils.getLogger());}	
			}
		});

		//			shell.addDisposeListener(new DisposeListener() {
		//
		//				@Override
		//				public void widgetDisposed(DisposeEvent e) {
		//					try {
		//						if (!saveButton.getSelection()) {p("Not saved");return;}
		//						p("Saving");
		//						Point r2 = parent.getSize(); //current depth
		//						p("Current: "+r2);
		//						Point r3 = parent.computeSize(SWT.DEFAULT,r2.y); //calc real width
		//						Point r4 = parent.computeSize(r3.x,SWT.DEFAULT); //calc real depthh
		////						Point rnew = parent.getSize();
		//						p("Setting to: "+r4);
		//						Rectangle r=new Rectangle(0,0,r4.x,r4.y);
		//						parent.setBounds(r);
		//						parent.layout();
		//						parent.pack();
		//						Image graphic = new Image(parent.getDisplay(), parent.getBounds());
		//						GC gc = new GC(graphic);
		//						parent.print(gc);
		//						ImageLoader loader = new ImageLoader();
		//						loader.data = new ImageData[] {graphic.getImageData()};
		//						loader.save("C:/Empty/test.png", SWT.IMAGE_PNG);
		//						p("Saved");
		//						graphic.dispose();
		//						gc.dispose();
		//					} catch (Exception e1) {e1.printStackTrace(utils.getLogger());}
		//					
		//				}});
		scrollComposite.setContent(parent);
		//			scrollComposite.setExpandVertical(true);
		//			scrollComposite.setExpandHorizontal(true);
		//			scrollComposite.addControlListener(new ControlAdapter() {
		//			shell.addControlListener(new ControlAdapter() {
		//				public void controlResized(ControlEvent e) {
		//					p("Resizing");
		//					p(e.data);
		//					Rectangle r = scrollComposite.getClientArea();
		//					p("scroller size:"+r+",w="+r.width);
		//					p(parent.computeSize(r.width, SWT.DEFAULT));
		//					Point r2 = parent.computeSize(r.width, SWT.DEFAULT); //real deptth
		//					Point rnew = parent.computeSize(SWT.DEFAULT,r2.y); //real width
		//					p(rnew);
		//					shell.layout();
		//					parent.setSize(parent.computeSize(r.width, SWT.DEFAULT));
		//					scrollComposite.setMinSize(parent.computeSize(r.width, SWT.DEFAULT));

		//				}
		//			});
		shell.pack();
		shell.open();
//		save("C:/OUGraphs/Latest.pdf");
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}


	}

	private void buildBlock(Composite container,int start,int end) {
		for (int n=start;n<=end;n++) {
			//			p("Adding graph:"+n);
			Label graph = new Label(container, SWT.BORDER);
			graph.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE));
			graph.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
			graph.setImage(graphImages.get(n));
			//			gridData = new GridData(SWT.LEFT, SWT.BOTTOM, false, false);
			//			saveButton.setLayoutData(gridData);
		}
		container.pack(); //ensure sizing is correct
	}





	// Utility method for quick printing to console
	void p(Object o) {
		utils.log(o);
	}

	public int getNcols() {
		return ncols;
	}

	public void setNcols(int ncols) {
		this.ncols = ncols;
	}
	/**
	 * @param b
	 */
	public void setAxes(boolean b) {
		drawAxes=b;
	}

}
