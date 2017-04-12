package ModificationFromUser;

import backEnd.ModelImpl;
import backEnd.GameData.State.Component;
import javafx.geometry.Point2D;

public class Modification_MoveComponent implements ModificationFromUser {

	private Component myComp;
	private Point2D newLoc;
	
	public Modification_MoveComponent(Component myComp, Point2D newLocation) {
		this.myComp = myComp;
		this.newLoc = newLocation;

	}

	@Override
	public void invoke(ModelImpl myModel) throws Exception {
		//FIXME does myComp's location attribute need to be updated here?
		myModel.getState().getComponentGraph().removeComponent(myComp);
		myModel.getState().getComponentGraph().addComponentToGrid(myComp, newLoc);		
	}

	
}
