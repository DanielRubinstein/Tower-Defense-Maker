package resources;

import javafx.geometry.Point2D;

/**
 * This class holds the constants that we use in the program.
 * @author Daniel
 *
 */

public class Constants {
	public static final String DEFAULT_CSS = "/resources/css/Flatter.css";
	
	//Engine constants
	public static final double defaultMoveAmount=5;
	public static final int defaultHealth=100;
	public static final Point2D defaultLocation=new Point2D(0,0);
	public static final float defaultRadius=100;
	public static final String BULLET_IMAGE_FILE="replacethis";
	
	//Animation constants
	public static final int FRAMES_PER_SECOND=60;
	public static final double MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	
	//screen setup constants
	public static final double WINDOW_HEIGHT = 650d;
	public static final double WINDOW_WIDTH = 750d;
	private static final double SCREEN_GRID_HEIGHT_FACTOR = 0.7;
	private static final double SCREEN_GRID_WIDTH_FACTOR = 0.8;
	private static final double SCREEN_GRID_PADDING = 50;
	private static final double BOTTOM_HEIGHT_FACTOR = 1 - SCREEN_GRID_HEIGHT_FACTOR;
	private static final double SIDE_WIDTH_FACTOR = 1 - SCREEN_GRID_WIDTH_FACTOR;
	public static final double SCREEN_GRID_WIDTH = WINDOW_WIDTH * SCREEN_GRID_WIDTH_FACTOR - SCREEN_GRID_PADDING;
	public static final double SCREEN_GRID_HEIGHT = WINDOW_HEIGHT * SCREEN_GRID_HEIGHT_FACTOR - SCREEN_GRID_PADDING;
	public static final double BOTTOM_HEIGHT = BOTTOM_HEIGHT_FACTOR * WINDOW_HEIGHT;
	public static final double SIDE_WIDTH = SIDE_WIDTH_FACTOR * WINDOW_WIDTH;
}
