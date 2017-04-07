package frontEnd.Skeleton.UserTools;

import frontEnd.Menus.ButtonMenuImpl;
import javafx.stage.Stage;
import main.ControllerImpl;

public class Settings extends ButtonMenuImpl{
	
	public Settings(){
		this.setText("Settings");
		//this.addButton("Load", event ->);
		
		//this.addButton("Save", event ->);
		
		//this.addButton("Rules", event ->);
		
		//this.addButton("Toggle Mode", event ->);
		
		this.addSimpleButton("Restart Application", event -> {
			// prompt if ok
			
			ControllerImpl controller = new ControllerImpl();
			controller.start(new Stage());
		});
	}

}
