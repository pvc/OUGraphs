/**
 * 
 */
package windowexample.handlers;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.dialogs.IOverwriteQuery;
import org.eclipse.ui.wizards.datatransfer.IImportStructureProvider;
import org.eclipse.ui.wizards.datatransfer.ImportOperation;
import org.pv.core.Utils;


/**
 * @author PV
 *
 */
public class ImportExamples {
	static final Utils utils = Utils.getSingleton();

	// Method called to run the class
	public void run() {
		p("Starting run of ImportExamples at " + new Date());
		doWork();
		p("Finished run of ImportExamples at " + new Date());
	}

	/**
	 * 
	 */
	private void doWork() {
		
	}

	public static void importContent(Object source, IPath dstPath, IImportStructureProvider provider, List filesToImport, IProgressMonitor monitor) throws CoreException {
		IOverwriteQuery query = new IOverwriteQuery() {
			public String queryOverwrite(String file) {
				return ALL;
			}
		};
		try {
			ImportOperation op = new ImportOperation(dstPath, source, provider, query);
			op.setCreateContainerStructure(false);
			if (filesToImport != null) {
				op.setFilesToImport(filesToImport);
			}
			op.run(monitor);
		} catch (InvocationTargetException e) {
			IStatus status = new Status(IStatus.ERROR, "PVImporter", IStatus.ERROR, e.getMessage(), e);
			throw new CoreException(status);
		} catch (InterruptedException e) {
			throw new OperationCanceledException(e.getMessage());
		}
	}



	// Utility method for quick printing to console
	void p(Object o) {
		utils.log(o);
	}

}
