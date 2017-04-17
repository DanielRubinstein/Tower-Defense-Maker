package frontEnd;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import ModificationFromUser.ModificationFromUser;
import ModificationFromUser.Modification_Load;
import ModificationFromUser.Modification_Save;
import backEnd.Model;
import backEnd.Bank.BankController;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.Tile;
import backEnd.Mode.ModeReader;
import data.DataController;
import data.XMLReadingException;
import data.GamePrep.GameLoader;
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
	private Consumer<ModificationFromUser> myModConsumer;
	private SkeletonImpl mySkeleton;
	private SimpleBooleanProperty authorProperty;
	private Stage appStage;

	public ViewImpl(Model model, Consumer<ModificationFromUser> inputConsumer) {
		myModel = model;
		myModConsumer = inputConsumer;
		ModeReader mode = model.getModeReader();
		authorProperty = new SimpleBooleanProperty(mode.getUserModeString().equals("AUTHOR"));
		mySkeleton = new SkeletonImpl();
		mySkeleton.init(this, model);
		appStage = new Stage();
		mySkeleton.display(appStage);
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
		GameLoader gL = new GameLoader();
		File fileToLoad = null;
		try {
			fileToLoad = gL.loadGame();
		} catch (XMLReadingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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