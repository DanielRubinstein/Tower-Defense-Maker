package frontEnd.Skeleton.ScreenGrid;

import backEnd.GameData.State.SerializableObservableGen;
import javafx.geometry.Point2D;
import javafx.scene.Node;

public abstract class GridVisualBase<T> implements GridVisual<T> {

	/**
	 * Updates the grid.
	 * @param obj
	 */
	protected abstract void updateGrid(T obj);
	
	@Override
	public abstract void addPreset(T tile, Point2D position);
	
	@Override
	public abstract Node getRoot();

	@Override
	public void update(SerializableObservableGen<T> object, T obj) {
		updateGrid(obj);
		
	}
}
