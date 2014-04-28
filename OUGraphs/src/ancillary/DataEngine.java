/**
 * 
 */
package ancillary;
import graphs.ScalarSequence;

import java.util.Date;

import org.pv.core.Utils;
/**
 * @author Paul
 *
 */
public interface DataEngine {

	/**
	 * @return
	 */
	ScalarSequence[] getData();

	/**
	 * @return
	 */
	ScalarSequence getXValues();

}
