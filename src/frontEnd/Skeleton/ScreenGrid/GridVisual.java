package frontEnd.Skeleton.ScreenGrid;

import java.util.function.Consumer;

import backEnd.GameData.State.SerializableObserverGen;
import backEnd.GameData.State.Tile;
import frontEnd.Skeleton.SkeletonObject;
import javafx.geometry.Point2D;

public interface GridVisual<T> extends SkeletonObject, SerializableObserverGen<T>{

	void addPreset(T tile, Point2D position);

	public void forEach(Consumer<? super T> method);
	
}
