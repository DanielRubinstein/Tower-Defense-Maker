package main;

import backEnd.Bank.BankController;
import backEnd.Data.DataController;
import backEnd.GameData.GameData;
import backEnd.GameData.State.State;
import backEnd.GameData.State.StateImpl;
import backEnd.GameEngine.GameProcessController;
import backEnd.Mode.Mode;
import backEnd.Mode.UserModeType;
import frontEnd.ViewImpl;
import frontEnd.Skeleton.Skeleton;
import frontEnd.Skeleton.SkeletonImpl;
import javafx.stage.Stage;

public class ControllerImpl implements Controller
{
	private ViewImpl myView;
	private BankController myBankController;
	private GameData myGameData;
	private GameProcessController myEngineController;
	private Mode myMode;
	private DataController myDataController;
	
	public void start(Stage stage){
		
		myView = new ViewImpl();
		myMode = new Mode(null, UserModeType.AUTHOR);
		SkeletonImpl skeleton = new SkeletonImpl(myView);
		skeleton.display(stage);
		myDataController = new DataController();
		
		//myGameData = myDataController.getGameData();
		//myBankController = new BankController(myDataController.getComponents(), myDataController.getTiles());
	
	}
}