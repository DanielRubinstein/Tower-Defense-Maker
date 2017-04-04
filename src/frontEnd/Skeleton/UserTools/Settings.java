package frontEnd.Skeleton.UserTools;

import frontEnd.Menus.ButtonMenu;
import javafx.stage.Stage;
import main.Controller;

public class Settings extends ButtonMenu{
	
	public Settings(){
		this.setText("Settings");
		//this.addButton("Load", event ->);
		
		//this.addButton("Save", event ->);
		
		//this.addButton("Rules", event ->);
		
		//this.addButton("Toggle Mode", event ->);
		
		this.addButton("Restart Application", event -> {
			// prompt if ok
			
			Controller controller = new Controller();
			controller.start(new Stage());
		});
	}

}
