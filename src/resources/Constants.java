package resources;

import com.sun.javafx.geom.Point2D;

/**
 * This class holds the constants that we use in the program.
 * @author Daniel
 *
 */

public class Constants {
	public static final int defaultMoveAmount=5;
	public static final int defaultHealth=100;
	public static final Point2D defaultLocation=new Point2D(0,0);
	public static final float defaultRadius=100;
	public static final String BULLET_IMAGE_FILE="replacethis";
	public static final int FRAMES_PER_SECOND=60;
	public static final double MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
}
