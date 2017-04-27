package resources.constants;

import java.util.ResourceBundle;

public class StringConstants {
	
	private static final String BUNDLE_NAME = "resources.stringConstants";
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
	
	public String getDefaultCSS(){
		return RESOURCE_BUNDLE.getString("DEFAULT_CSS");
	}
	
	public String getString(String key){
		return RESOURCE_BUNDLE.getString(key);
	}
}
