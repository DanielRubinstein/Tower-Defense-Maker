package backEnd.GameData.State;

import javafx.geometry.Point2D;

public class TileCorners {
	
	private double minX;
	private double maxX;
	private double minY;
	private double maxY;
	
	/*
	public TileCorners(Point2D tileGridPosition, double tileWidth, double tileHeight){
		minX = Math.floor(tileGridPosition.getX() * tileWidth);
		maxX = Math.floor(tileGridPosition.getX() * tileWidth) + tileWidth;
		minY = Math.floor(tileGridPosition.getY() * tileHeight);
		maxY = Math.floor(tileGridPosition.getY() * tileHeight) + tileHeight;
	}
	*/
	
	public TileCorners(Point2D tilePosition, double tileWidth, double tileHeight){
		minX = tilePosition.getX() - tileWidth / 2;
		maxX = tilePosition.getX() + tileWidth / 2;
		minY = tilePosition.getY() - tileHeight / 2;
		maxY = tilePosition.getY() + tileHeight / 2;
	}

	public double getMinX() {
		return minX;
	}

	public double getMaxX() {
		return maxX;
	}

	public double getMinY() {
		return minY;
	}

	public double getMaxY() {
		return maxY;
	}	

}
