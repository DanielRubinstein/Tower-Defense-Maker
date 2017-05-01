package resources.constants.numeric;

import java.util.ResourceBundle;

public class ScreenConstants {

	// screen setup constants
	private static final String SCREEN_CONSTANTS_PATH = "resources.constants.numeric.screen";
	private static final ResourceBundle SCREEN_CONSTANTS_RESOURCE = ResourceBundle.getBundle(SCREEN_CONSTANTS_PATH);

	public Double getWindowHeight() {
		return Double.valueOf(SCREEN_CONSTANTS_RESOURCE.getString("WindowHeight"));
	}

	public Double getWindowWidth() {
		return Double.valueOf(SCREEN_CONSTANTS_RESOURCE.getString("WindowWidth"));
	}

	public Double getScreenPadding() {
		return Double.valueOf(SCREEN_CONSTANTS_RESOURCE.getString("ScreenGridPadding"));
	}

	private Double getScreenGridHeightFactor() {
		return Double.valueOf(SCREEN_CONSTANTS_RESOURCE.getString("ScreenGridHeightFactor"));
	}

	private Double getScreenGridWidthFactor() {
		return Double.valueOf(SCREEN_CONSTANTS_RESOURCE.getString("ScreenGridWidthFactor"));
	}

	private Double getBottomHeightFactor() {
		return 1 - getScreenGridHeightFactor();
	}

	private Double getSideWidthFactor() {
		return 1 - getScreenGridWidthFactor();
	}

	public Double getScreenGridWidth() {
		return getWindowWidth() * getScreenGridWidthFactor() - getScreenPadding();
	}

	public Double getScreenGridHeight() {
		return getWindowHeight() * getScreenGridHeightFactor() - getScreenPadding();
	}

	public Double getBottomHeight() {
		return getBottomHeightFactor() * getWindowHeight();
	}

	public Double getSideWidth() {
		return getSideWidthFactor() * getWindowWidth()+20;
	}

}
