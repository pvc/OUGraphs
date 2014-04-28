/**
 * 
 */
package windowexample.handlers;
import java.util.Date;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.pv.core.Utils;
import org.w3c.dom.Document;


/**
 * @author PV
 *
 */
public class ImportExamples2 {
	final Utils utils = Utils.getSingleton();

	// Method called to run the class
	public void run() {
		p("Starting run of ImportExamples2 at " + new Date());
		doWork();
		p("Finished run of ImportExamples2 at " + new Date());
	}

	/**
	 * 
	 */
	private void doWork() {
		
		try {
//			String plugins="C:/eclipseBase/eclipse/plugins/";
//			String pname="org.eclipse.swt.examples.controls_3.1.100.v3738a";
//			IProject p = utils.importExternalProject(plugins+pname);
			String pname = "org.eclipse.swt.examples";
			Document doc = utils.getDoc(pname+"/.classpath");
			doc.getElementsByTagName("classpathentry");
			utils.dump(doc);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace(utils.getLogger());
		}
		
	}

	// Utility method for quick printing to console
	void p(Object o) {
		utils.log(o);
	}

}
