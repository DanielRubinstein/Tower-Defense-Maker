package frontEnd.Skeleton.AoTools;

import javafx.scene.Parent;
import javafx.stage.Stage;

public interface CommandCenter {
	
	/**
	 * Launches the tile command center. Note that we have to clear all previous tabs. 
	 * @param x
	 * @param y
	 */
	void launch(String title, double x, double y);
	
	
	void generate(double x, double y, Stage myStage, Parent myRoot);

}
