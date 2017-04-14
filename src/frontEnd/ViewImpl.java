package frontEnd;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import ModificationFromUser.ModificationFromUser;
import ModificationFromUser.Modification_Load;
import ModificationFromUser.Modification_Save;
import backEnd.Model;
import backEnd.Bank.BankController;
import backEnd.Data.DataController;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.Tile;
import backEnd.Mode.ModeReader;
import frontEnd.CustomJavafxNodes.SingleFieldPrompt;
import frontEnd.Menus.ErrorDialog;
import frontEnd.Skeleton.SkeletonImpl;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.util.Duration;
import resources.Constants;

public class ViewImpl implements View {

	private Model myModel;
	private DataController myDataController;
	private Consumer<ModificationFromUser> myModConsumer;
	private SkeletonImpl mySkeleton;
	private SimpleBooleanProperty authorProperty;
	private Stage appStage;

	public Timeline animation = new Timeline();
	private static final double MILLISECOND_DELAY = Constants.MILLISECOND_DELAY;
	private static final double SECOND_DELAY = Constants.SECOND_DELAY;

	public ViewImpl(Model model, DataController dataController, Consumer<ModificationFromUser> inputConsumer) {
		myModel = model;
		myDataController = dataController;
		myModConsumer = inputConsumer;
		ModeReader mode = model.getModeReader();
		authorProperty = new SimpleBooleanProperty(mode.getUserModeString().equals("AUTHOR"));
		mySkeleton = new SkeletonImpl(this, model);
		appStage = new Stage();
		mySkeleton.display(appStage);

	}

	@Override
	public Node getCanvas() {
		return mySkeleton.getCanvas();
	}

	@Override
	public void play() {
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
		animation.setCycleCount(Animation.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}
	
	/**
	 * controls the animation of the State
	 */
	private void step(double delay) {
		myModel.getGameProcessController().run(delay); // TODO: TESTING ONLY
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
	public String getRunStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save() {
		String saveGameName = getSaveGameName();
		sendUserModification(new Modification_Save(saveGameName));
	}

	private String getSaveGameName() {
		List<String> dialogTitles = Arrays.asList("Save Game Utility", "Please Input a Name for your saved game");
		String promptLabel = "Saved game name:";
		String promptText = "";
		SingleFieldPrompt myDialog = new SingleFieldPrompt(dialogTitles, promptLabel, promptText);
		return myDialog.create();
	}

	@Override
	public void load() {
		String fileToLoad = null;
		sendUserModification(new Modification_Load(fileToLoad));
	}

	@Override
	public void newGame() {
		// TODO Auto-generated method stub
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
	public void step() {
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
}