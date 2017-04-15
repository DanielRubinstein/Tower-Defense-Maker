package resources;

import javafx.geometry.Point2D;

/**
 * This class holds the constants that we use in the program.
 * @author Daniel
 *
 */

public class Constants {
	//Engine constants
	public static final int defaultMoveAmount=5;
	public static final int defaultHealth=100;
	public static final Point2D defaultLocation=new Point2D(0,0);
	public static final float defaultRadius=100;
	public static final String BULLET_IMAGE_FILE="replacethis";
	
	//Animation constants
	public static final int FRAMES_PER_SECOND=60;
	public static final double MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	
	//screen setup constants
	public static final double MENU_HEIGHT = 650d;
	public static final double MENU_WIDTH = 750d;
	public static final double CANVAS_HEIGHT_FACTOR = 0.7;
	public static final double CANVAS_WIDTH_FACTOR = 0.8;
	public static final double BOTTOM_HEIGHT_FACTOR = 1 - CANVAS_HEIGHT_FACTOR;
	public static final double SIDE_WIDTH_FACTOR = 1 - CANVAS_WIDTH_FACTOR;
	public static final double CANVAS_WIDTH=MENU_WIDTH * CANVAS_WIDTH_FACTOR;
	public static final double CANVAS_HEIGHT=MENU_HEIGHT * CANVAS_HEIGHT_FACTOR;
}
