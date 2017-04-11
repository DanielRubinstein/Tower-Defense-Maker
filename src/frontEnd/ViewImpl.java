package frontEnd;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Consumer;

import ModificationFromUser.ModificationFromUser;
import backEnd.Model;
import backEnd.GameData.UserAttribute;
import backEnd.GameData.UserAttributeImpl;
import backEnd.GameData.State.ComponentGraph;
import backEnd.GameData.State.Tile;
import backEnd.GameData.State.TileGrid;
import backEnd.GameEngine.Component;
import backEnd.Mode.ModeReader;
import frontEnd.Skeleton.SkeletonImpl;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.stage.Stage;

public class ViewImpl implements View{
	private Model myModel;
	private Consumer<ModificationFromUser> myModConsumer;
	private SkeletonImpl mySkeleton;
	private SimpleBooleanProperty authorProperty;
	private Stage appStage;
	
	public ViewImpl(Model model,Consumer<ModificationFromUser> inputConsumer) {
		myModel = model;
		myModConsumer = inputConsumer;
		ModeReader mode = model.getModeReader();
		authorProperty = new SimpleBooleanProperty(mode.getUserModeString().equals("AUTHOR"));
		mySkeleton = new SkeletonImpl(this,model);
		appStage = new Stage();
		mySkeleton.display(appStage);
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
	public Object save() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object viewRules() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object editRules() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object load() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object newGame() {
		// TODO Auto-generated method stub
		return null;
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

}