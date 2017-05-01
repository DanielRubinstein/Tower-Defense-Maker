package frontEnd.CustomJavafxNodes;

import javafx.beans.property.BooleanProperty;

/**
 * This interface defines any Node in our UI that has restricted access permissions, i.e. it should be disabled
 * under certain circumstances (like being in Player mode)
 * @author Tim
 *
 */
public interface RestrictedPermissions {

	
	/**
	 * The disabledProperty of the contents.
	 * @return
	 */
	public BooleanProperty disabledProperty();
}

