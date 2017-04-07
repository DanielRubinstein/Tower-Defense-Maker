package frontEnd;

import java.util.Observable;
import java.util.Observer;
import java.util.function.Consumer;

import backEnd.GameData;
import backEnd.Model.Model;
import frontEnd.Menus.GameSelection;
import frontEnd.Menus.MainMenu;
import frontEnd.Skeleton.SkeletonImpl;
import javafx.stage.Stage;

public class View implements Observer{
	private Model myModel;
	private SkeletonImpl mySkeleton;
	private Consumer<GameData> gameDataConsumer;
	
	public void setGameDataListener(Consumer<GameData> gameDataConsumer) {
		this.gameDataConsumer = gameDataConsumer;
	}

	public void start(Stage stage) {
		GameSelection gS = new GameSelection(gameDataConsumer);
		MainMenu mainMenu = new MainMenu();
		mainMenu.setGameSelection(gS);
		mainMenu.showMenus(stage);
	}
	
	
	
	public void setModel(Model model) {
		this.myModel = model;
	}
	
	@Override
	public void update(Observable observable, Object arg) {
		if(myModel == observable){
			if(mySkeleton == null){
				createNewSkeleton();
			}
			
		}
		
	}

	private void createNewSkeleton() {
		mySkeleton = new SkeletonImpl();
		
		
	}

}
