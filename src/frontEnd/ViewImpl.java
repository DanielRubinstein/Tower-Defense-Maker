package frontEnd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import ModificationFromUser.ModificationFromUser;
import ModificationFromUser.Modification_Load;
import ModificationFromUser.Modification_Save;
import backEnd.Model;
import backEnd.Attribute.AttributeOwnerReader;
import backEnd.Data.DataController;
import backEnd.GameData.UserAttribute;
import backEnd.GameData.UserAttributeImpl;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.ComponentGraph;
import backEnd.GameData.State.Tile;
import backEnd.GameData.State.TileGrid;
import backEnd.Mode.ModeReader;
import frontEnd.CustomJavafxNodes.SingleFieldPrompt;
import frontEnd.Skeleton.SkeletonImpl;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.stage.Stage;
import javafx.util.Duration;
import resources.Constants;

public class ViewImpl implements View{

	private Model myModel;
	private DataController myDataController;
	private Consumer<ModificationFromUser> myModConsumer;
	private SkeletonImpl mySkeleton;
	private SimpleBooleanProperty authorProperty;
	private Stage appStage;
	
	public Timeline animation = new Timeline();
	private static final double MILLISECOND_DELAY = Constants.MILLISECOND_DELAY;
	private static final double SECOND_DELAY = Constants.SECOND_DELAY;

	
	public ViewImpl(Model model,DataController dataController, Consumer<ModificationFromUser> inputConsumer) {
		myModel = model;
		myDataController = dataController;
		myModConsumer = inputConsumer;
		ModeReader mode = model.getModeReader();
		authorProperty = new SimpleBooleanProperty(mode.getUserModeString().equals("AUTHOR"));
		mySkeleton = new SkeletonImpl(this,model);
		appStage = new Stage();
		mySkeleton.display(appStage);
		
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		//animation.play();
		
	}
	public void addToCanvas(AttributeOwnerReader ao){
		mySkeleton.addToCanvas(ao);
	}

	/**
	 * controls the animation of the State
	 */
	private void step(double delay){
		myModel.getGameProcessController().run(delay); //TODO: TESTING ONLY
	}

	
	public SimpleBooleanProperty getBooleanAuthorModeProperty(){
		return this.authorProperty;
	}
	
	@Override
	public void sendUserModification(ModificationFromUser mod){
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
	public TileGrid getTileGrid() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ComponentGraph getComponentGraph() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public Collection<UserAttribute> getUserAttributes() {
		// TODO find way to bind these backend values so that it is updated in the frontend
		// could do bindings
		// or observables
		Collection<UserAttribute> m = new ArrayList<UserAttribute>();
		m.add(new UserAttributeImpl("Score", 3000d));
		m.add(new UserAttributeImpl("Level", 17d));
		return Collections.unmodifiableCollection(m);
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

}