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
import frontEnd.Skeleton.SplashScreens.SplashScreenImpl;
import frontEnd.Skeleton.SplashScreens.SplashScreenReader;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.stage.Stage;
import javafx.stage.Window;
import resources.constants.StringResourceBundle;

/**
 * Class that initializes the UI and serves as a connector betweel the Model and the Front End
 * @author Tim, Miguel
 *
 */
public class ViewImpl implements View {
	private ModelReader myModel;
	private Consumer<ModificationFromUser> myModConsumer;
	private SkeletonImpl mySkeleton; 
	private Stage appStage;
	private FacebookInteractor myFB;
	private StringResourceBundle strResources = new StringResourceBundle();

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
		return myModel.getModeReader().getAuthorBooleanProperty();
	}
	
	@Override
	public SimpleStringProperty getStringGameModeProperty() {
		return myModel.getModeReader().getGameStringProperty();
	}
	
	@Override
	public SimpleStringProperty getStringLevelModeProperty() {
		return myModel.getModeReader().getLevelStringProperty();
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
		fnf.create(strResources.getFromErrorMessages("Default_Error"), e.getMessage());
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

	/** 
	 * Sets the splash screen in an end-level sceanario
	 * 
	 * IN DEVELOPMENT
	 * 
	 * @param data
	 */
	public void setSplashScreen(SplashScreenReader splashScreen){
		sendUserModification(Modification_GameRemote.PAUSE); 
		splashScreen.display(appStage);
	}

	@Override
	public boolean isComponentOnGrid(Component c) {
		return myModel.getState().getComponentGraph().contains(c);
	}

	@Override
	public Window getMainWindow() {
		return appStage;
	}

	/** Closes the main application window. Cannot be undone!
	 * 
	 */
	public void closeMainWindow(){
		appStage.close();
	}

}