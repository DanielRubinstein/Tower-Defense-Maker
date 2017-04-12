package resources;

import com.sun.javafx.geom.Point2D;

/**
 * This class holds the constants that we use in the program.
 * @author Daniel
 *
 */

public class Constants {
	public static int FRAMES_PER_SECOND = 60;
	public static double SECOND_DELAY = 1.0/FRAMES_PER_SECOND;
	public static double MILLISECOND_DELAY = 1000/FRAMES_PER_SECOND;
	public static int defaultMoveAmount=5;
	public static int defaultHealth=100;
	public static Point2D defaultLocation=new Point2D(0,0);
	public static float defaultRadius=100;
	public static String BULLET_IMAGE_FILE;
}
