package ModificationFromUser;

import backEnd.ModelImpl;
import backEnd.GameData.State.Component;
import javafx.geometry.Point2D;

public class Modification_AddComponent implements ModificationFromUser {

	private Component newComp;
	private Point2D location;

	public Modification_AddComponent(Component newComp, Point2D location) {
		this.newComp = newComp;
		this.location = location;

	}

	@Override
	public void invoke(ModelImpl myModel) throws Exception {
		myModel.getState().getComponentGraph().addComponentToGrid(newComp, location);
		/**
		 * FIXME should parameter be newComp.clone() ?
		 */
	}
}
