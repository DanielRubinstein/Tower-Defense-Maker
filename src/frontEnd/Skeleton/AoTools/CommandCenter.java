package frontEnd.Skeleton.AoTools;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class CommandCenter {
	private static final String DEFAULT_CSS = "/resources/css/Flatter.css";
	
	protected void generate(double x, double y, Stage myStage, Parent myRoot) {
		Scene myScene = new Scene(myRoot);
		myScene.getStylesheets().add(DEFAULT_CSS);
		myStage.setScene(myScene);
		myStage.setTitle("Command Center");
		myStage.setX(x);
		myStage.setY(y);
		myStage.show();
	}


}
