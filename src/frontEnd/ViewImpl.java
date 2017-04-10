package frontEnd;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Consumer;

import ModificationFromUser.ModificationFromUser;
import backEnd.GameData.UserAttribute;
import backEnd.GameData.UserAttributeImpl;
import backEnd.GameData.State.ComponentGraph;
import backEnd.GameData.State.TileGrid;
import backEnd.Mode.ModeReader;
import frontEnd.Skeleton.SkeletonImpl;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.stage.Stage;

public class ViewImpl implements ViewEditor{
	private Consumer<ModificationFromUser> myModConsumer;
	private ModeReader myMode;
	private SkeletonImpl mySkeleton;
	private SimpleBooleanProperty authorProperty;
	
	public ViewImpl(ModeReader mode, Consumer<ModificationFromUser> inputConsumer) {
		myMode = mode;
		myModConsumer = inputConsumer;
		authorProperty = new SimpleBooleanProperty(this.getMode().getUserModeString().equals("AUTHOR"));
		System.out.println("seting up view impl");
		mySkeleton = new SkeletonImpl(this);
		mySkeleton.display(new Stage());
	}
	
	public SimpleBooleanProperty getAuth(){
		return this.authorProperty;
	}
	
	@Override
	public void sendUserModification(ModificationFromUser mod){
		myModConsumer.accept(mod);
	}
	

	@Override
	public ModeReader getMode() {
		return myMode;
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

}