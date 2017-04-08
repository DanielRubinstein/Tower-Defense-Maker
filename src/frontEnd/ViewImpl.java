package frontEnd;

import java.util.function.Consumer;

import ModificationFromUser.ModificationFromUser;
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
	public void sendUserModification(ModificationFromUser mod){
		myModConsumer.accept(mod);
	}
	

	@Override
	public ModeReader getMode() {
		return myMode;
	}

}
