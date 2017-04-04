package frontEnd;

import java.util.Observable;
import java.util.Observer;

import backEnd.Model.Model;
import frontEnd.Menus.MainMenu;
import javafx.stage.Stage;

public class View implements Observer{
	private Model myModel;
	
	public void setModel(Model model) {
		this.myModel = model;
	}

	public void start(Stage stage) {
		MainMenu mainMenu = new MainMenu();
		mainMenu.setGameDataListener(myModel::initializeGame);
		mainMenu.showMenus(stage);
	}
	
	@Override
	public void update(Observable observable, Object arg) {
		if(myModel == observable){
			
		}
		
	}

}
