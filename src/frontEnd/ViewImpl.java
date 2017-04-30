package frontEnd;

import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;

import ModificationFromUser.*;
import backEnd.ModelReader;
import backEnd.Bank.BankControllerImpl;
import backEnd.Bank.BankControllerReader;
import backEnd.GameData.Rules.RuleReader;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.PlayerStatusReader;
import backEnd.GameEngine.Engine.Spawning.SpawnQueues;
import backEnd.LevelProgression.LevelProgressionControllerEditor;
import backEnd.Mode.ModeReader;
import frontEnd.CustomJavafxNodes.ErrorDialog;
import frontEnd.Facebook.FacebookInteractor;
import frontEnd.Skeleton.SkeletonImpl;
import frontEnd.Skeleton.SplashScreens.SplashScreen;
import frontEnd.Skeleton.SplashScreens.SplashScreenData;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.stage.Stage;

public class ViewImpl implements View {
	private ModelReader myModel;
	private Consumer<ModificationFromUser> myModConsumer;
	private SkeletonImpl mySkeleton; 
	private SimpleBooleanProperty authorProperty;
	private Stage appStage;
	private FacebookInteractor myFB;
	private ModeReader myMode;

	public ViewImpl(ModelReader model, Consumer<ModificationFromUser> inputConsumer, FacebookInteractor fb) {
		myFB=fb;
		init(model, inputConsumer);
	}
	
	public ViewImpl(ModelReader model, Consumer<ModificationFromUser> inputConsumer) {
		init(model, inputConsumer);
	}
	
	private void init(ModelReader model, Consumer<ModificationFromUser> inputConsumer){
		myModel = model;
		myModConsumer = inputConsumer;
		myMode = model.getModeReader();
		authorProperty = myMode.getAuthorBooleanProperty();
		mySkeleton = new SkeletonImpl();
		mySkeleton.init(this, myModel);
		appStage = new Stage();
		mySkeleton.display(appStage);
	}

	@Override
	public FacebookInteractor getFb(){
		return myFB;
	}

	@Override
	public SimpleBooleanProperty getBooleanAuthorModeProperty() {
		return this.authorProperty;
	}
	
	@Override
	public SimpleStringProperty getStringGameModeProperty() {
		return myMode.getGameStringProperty();
	}
	
	@Override
	public SimpleStringProperty getStringLevelModeProperty() {
		return myMode.getLevelStringProperty();
	}

	@Override
	public void sendUserModification(ModificationFromUser mod) {
		myModConsumer.accept(mod);
	}

	@Override
	public SimpleStringProperty getRunStatus() {
		return myModel.getEngineStatus();
	}

	@Override
	public Stage getAppStage() {
		return appStage;
	}

	@Override
	public Collection<RuleReader> getRules(){
		return myModel.getRulesList();
	}
	
	@Override
	public BankControllerReader getBankControllerReader(){
		return myModel.getBankControllerReader();
	}

	@Override
	public void reportError(Exception e) {
		ErrorDialog fnf = new ErrorDialog();
		fnf.create("Error", e.getMessage());
	}

	@Override
	public LevelProgressionControllerEditor getLevelProgressionController() {
		return myModel.getLevelProgressionController();
	}

	@Override
	public PlayerStatusReader getPlayerStatus() {
		return myModel.getPlayerStatusReader();
	}

	@Override
	public Map<String, SpawnQueues> getSpawnQueues() {
		return myModel.getState().getSpawnQueues();
	}
	
	@Override
	public ModeReader getModeReader(){
		return myMode;
	}

	public void setSplashScreen(SplashScreenData data)
	{
		System.out.println("View Line 158");
		sendUserModification(Modification_GameRemote.PAUSE); 
		SplashScreen splashScreen = new SplashScreen(data);
		splashScreen.display(appStage);
	}

	@Override
	public boolean isComponentOnGrid(Component c) {
		return myModel.getState().getComponentGraph().contains(c);
	}


}