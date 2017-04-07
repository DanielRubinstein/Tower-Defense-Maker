package frontEnd.Skeleton.UserTools;

<<<<<<< HEAD
import backEnd.State.TileImpl;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class OptionsCorner {


	private VBox myRoot;
	private SettingsView settingsDisplay;
	private OptionsSelection userOptions;
=======
/**
 * Add implementation later.
 * This interface defines the options corner to be displayed on the right side of the BottomRoot
 * @author Tim
 *
 */
public interface OptionsCorner extends SkeletonObject{
>>>>>>> 2658b55a1c7768b5bed8c8e83980357a5d9cbe74
	
	 /**
	  * Sets the sideLength to the parameter height
	  * @param height
	  */
	public void setSideLength(double height);
	
}
