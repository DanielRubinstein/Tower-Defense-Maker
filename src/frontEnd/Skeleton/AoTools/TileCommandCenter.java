package frontEnd.Skeleton.AoTools;

import frontEnd.Skeleton.UserTools.SkeletonObject;

public interface TileCommandCenter extends SkeletonObject{

	/**
	 * Launches the tile command center. Note that we have to clear all previous tabs. 
	 * @param x
	 * @param y
	 */
	void launch(double x, double y);

}