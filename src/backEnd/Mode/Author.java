package backEnd.Mode;

import backEnd.GameEngine.Component;
import backEnd.State.Tile;
import backEnd.State.TileAttribute;
import javafx.geometry.Point2D;

public class Author extends Mode{

	@Override
	public void addComponent(Component component, Point2D location) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeComponent(Component component) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addTile() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addTileAttribute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeTileAttribute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveTile() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editRules() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void play() throws ModeException {
		throw new ModeException("Cannot play in author mode");
		
	}

	@Override
	public void pause() {
		myModel.getEngine.pause();
		
	}

	
}