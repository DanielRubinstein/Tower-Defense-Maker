package frontEnd;

import java.util.Collection;
import java.util.function.Consumer;

import ModificationFromUser.ModificationFromUser;
import backEnd.Model;
import backEnd.ModelReader;
import backEnd.Bank.BankController;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.PlayerStatusReader;
import backEnd.GameData.State.Tile;
import backEnd.LevelProgression.LevelProgressionControllerReader;
import backEnd.Mode.ModeReader;
import frontEnd.CustomJavafxNodes.ErrorDialog;
import frontEnd.Facebook.FacebookConnector;
import frontEnd.Facebook.FacebookInteractor;
import frontEnd.Skeleton.SkeletonImpl;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ViewImpl implements View {
	private ModelReader myModel;
	private Consumer<ModificationFromUser> myModConsumer;
	private SkeletonImpl mySkeleton;
	private SimpleBooleanProperty authorProperty;
	private Stage appStage;
	private FacebookInteractor myFB;
	private Rectangle mySpawnZoneRepeating;
	private Rectangle mySpawnZoneSingle;

	public ViewImpl(Model model, Consumer<ModificationFromUser> inputConsumer, FacebookInteractor fb) {
		myFB=fb;
		init(model, inputConsumer);
	}
	
	public ViewImpl(ModelReader model, Consumer<ModificationFromUser> inputConsumer) {
		init(model, inputConsumer);
	}
	
	private void init(ModelReader model, Consumer<ModificationFromUser> inputConsumer){
		myModel = model;
		myModConsumer = inputConsumer;
		ModeReader mode = model.getModeReader();
		authorProperty = mode.getAuthorBooleanProperty();
		mySkeleton = new SkeletonImpl();
		mySkeleton.init(this, myModel);
		appStage = new Stage();
		mySkeleton.display(appStage);
	}

	public FacebookInteractor getFb(){
		return myFB;
	}

	@Override
	public Node getScreenGrid() {
		return mySkeleton.getScreenGrid();
	}

	@Override
	public SimpleBooleanProperty getBooleanAuthorModeProperty() {
		return this.authorProperty;
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
	public Collection<Tile> getTilePresets() {
		return myModel.getBankController().getTileMap().values();
	}

	@Override
	public Collection<Component> getComponentPresets() {
		return myModel.getBankController().getComponentMap().values();
	}

	@Override
	public Stage getAppStage() {
		return appStage;
	}

	@Override
	public void viewRules() {
		// TODO Auto-generated method stub
	}

	@Override
	public void editRules() {
		// TODO Auto-generated method stub
	}

	@Override
	public BankController getBankController() {
		return myModel.getBankController();
	}

	@Override
	public void reportError(Exception e) {
		ErrorDialog fnf = new ErrorDialog();
		fnf.create("Error", e.getMessage());
	}

	@Override
	public LevelProgressionControllerReader getLevelProgressionController() {
		return myModel.getLevelProgressionController();
	}

	@Override
	public PlayerStatusReader getPlayerStatus() {
		return myModel.getPlayerStatusReader();
	}

}