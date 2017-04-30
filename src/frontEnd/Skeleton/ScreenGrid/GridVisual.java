package frontEnd.Skeleton.ScreenGrid;

import backEnd.GameData.State.SerializableObserverGen;
import frontEnd.Skeleton.UserTools.SkeletonObject;
import javafx.geometry.Point2D;

public interface GridVisual<T> extends SkeletonObject, SerializableObserverGen<T>{

	void addPreset(T tile, Point2D position);

	
}
