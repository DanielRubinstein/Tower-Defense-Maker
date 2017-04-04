package frontEnd.Skeleton.UserTools;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This class represents the screen that the user sees when the settings button is pressed
 * @author Tim
 *
 */
public class SettingsViewImpl implements SettingsView{

	private VBox myRoot;
	public SettingsViewImpl() {
		myRoot = new VBox();
	}
	
	public void launchSettings(){
		Stage stage = new Stage();
		Scene scene = new Scene(myRoot);
		stage.setScene(scene);
		stage.show();
	}

}
