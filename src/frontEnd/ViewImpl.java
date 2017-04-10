package frontEnd;

import java.util.function.Consumer;

import ModificationFromUser.ModificationFromUser;
import backEnd.GameData.State.ComponentGraph;
import backEnd.GameData.State.TileGrid;
import backEnd.Mode.ModeReader;
import frontEnd.Skeleton.SkeletonImpl;
import javafx.stage.Stage;

public class ViewImpl implements ViewEditor{
	private Consumer<ModificationFromUser> myModConsumer;
	private ModeReader myMode;
	private SkeletonImpl mySkeleton;
	
	public ViewImpl(ModeReader mode, Consumer<ModificationFromUser> inputConsumer) {
		myMode = mode;
		myModConsumer = inputConsumer;
		mySkeleton = new SkeletonImpl(this);
		mySkeleton.display(new Stage());
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

}
