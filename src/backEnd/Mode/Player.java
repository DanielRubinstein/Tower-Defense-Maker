package backEnd.Mode;

import backEnd.GameEngine.Component;
import javafx.geometry.Point2D;

public class Player extends Mode {

	@Override
	public void addComponent(Component component, Point2D location) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeComponent(Component component) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addTile() throws ModeException {
		throw new ModeException("Cannot add tiles in player mode");
		
	}

	@Override
	public void addTileAttribute() throws ModeException {
		throw new ModeException("Cannot edit tiles in player mode");
		
	}

	@Override
	public void removeTileAttribute() throws ModeException {
		throw new ModeException("Cannot edit tiles in player mode");
		
	}

	@Override
	public void moveTile() throws ModeException {
		throw new ModeException("Cannot edit tiles in player mode");
		
	}

	@Override
	public void editRules() throws ModeException {
		throw new ModeException("Cannot edit rules in player mode");
		
	}

	@Override
	public void play() {
		myModel.getEngine.gameLoop(myModel.getState());
		
	}

	@Override
	public void pause() {
		myModel.getEngine.pause();
		
	}


}