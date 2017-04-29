package resources.constants;

import java.util.ResourceBundle;
import java.util.Set;

public class StringResourceBundle {

	private static final String STRING_CONSTANTS_PATH = "resources.constants.stringResourceBundle";
	private static final ResourceBundle STRING_CONSTANTS_RESOURCE = ResourceBundle.getBundle(STRING_CONSTANTS_PATH);
	
	public String getFromStringConstants(String str){
		return STRING_CONSTANTS_RESOURCE.getString(str);
	}
	
	public Set<String> getKeysFromStringConstants(){
		return STRING_CONSTANTS_RESOURCE.keySet();
	}
	
	private static final String ATTRIBUTE_TYPES_PATH = "resources.allAttributeTypes";
	private static final ResourceBundle ATTRIBUTE_TYPES_RESOURCE = ResourceBundle.getBundle(ATTRIBUTE_TYPES_PATH);
	
	public String getFromAttributeTypes(String str){
		return ATTRIBUTE_TYPES_RESOURCE.getString(str);
	}
	
	public Set<String> getKeysFromAttributeTypes(){
		return ATTRIBUTE_TYPES_RESOURCE.keySet();
	}
	
	private static final String ATTRIBUTE_NAMES_PATH = "resources.allAttributeNames";
	private static final ResourceBundle ATTRIBUTE_NAMES_RESOURCE = ResourceBundle.getBundle(ATTRIBUTE_NAMES_PATH);

	public String getFromAttributeNames(String str){
		return ATTRIBUTE_NAMES_RESOURCE.getString(str);
	}
	
	public Set<String> getKeysFromAttributeNames(){
		return ATTRIBUTE_NAMES_RESOURCE.keySet();
	}
	
	private static final String DEFAULT_COMPONENT_ATTRIBUTES_PATH = "resources.defaultComponentAttributes";
	private static final ResourceBundle DEFAULT_COMPONENT_ATTRIBUTES_RESOURCE = ResourceBundle.getBundle(DEFAULT_COMPONENT_ATTRIBUTES_PATH);
	
	public String getFromDefaultComponentAttributes(String str){
		return DEFAULT_COMPONENT_ATTRIBUTES_RESOURCE.getString(str);
	}
	
	public Set<String> getKeysFromDefaultComponentAttributes(){
		return DEFAULT_COMPONENT_ATTRIBUTES_RESOURCE.keySet();
	}
	
	private static final String DEFAULT_TILE_ATTRIBUTES_PATH = "resources.defaultTileAttributes";
	private static final ResourceBundle DEFAULT_TILE_ATTRIBUTES_RESOURCE = ResourceBundle.getBundle(DEFAULT_TILE_ATTRIBUTES_PATH);
	
	public String getFromDefaultTileAttributes(String str){
		return DEFAULT_TILE_ATTRIBUTES_RESOURCE.getString(str);
	}
	
	public Set<String> getKeysFromDefaultTileAttributes(){
		return DEFAULT_TILE_ATTRIBUTES_RESOURCE.keySet();
	}
	
	private static final String ENGINE_NAMES_PATH = "resources.engineNames";
	private static final ResourceBundle ENGINE_NAMES_RESOURCE = ResourceBundle.getBundle(ENGINE_NAMES_PATH);
	
	public String getFromEngineNames(String str){
		return ENGINE_NAMES_RESOURCE.getString(str);
	}
	
	public Set<String> getKeysFromEngineNames(){
		return ENGINE_NAMES_RESOURCE.keySet();
	}
	
	private static final String FACEBOOK_PATH = "resources.facebook";
	private static final ResourceBundle FACEBOOK_RESOURCE = ResourceBundle.getBundle(FACEBOOK_PATH);
	
	public String getFromFacebook(String str){
		return FACEBOOK_RESOURCE.getString(str);
	}
	
	public Set<String> getKeysFromFacebook(){
		return FACEBOOK_RESOURCE.keySet();
	}
	
	private static final String GAME_PROCESS_CTLR_PATH = "resources.GameProcessController";
	private static final ResourceBundle GAME_PROCESS_CTLR_RESOURCE = ResourceBundle.getBundle(GAME_PROCESS_CTLR_PATH);
	
	
	public String getFromGameProcessController(String str){
		return GAME_PROCESS_CTLR_RESOURCE.getString(str);
	}
	
	public Set<String> getKeysFromGameProcessController(){
		return GAME_PROCESS_CTLR_RESOURCE.keySet();
	}
	
	private static final String PLAYER_STATUS_ITEMS_PATH = "resources.playerStatusItems";
	private static final ResourceBundle PLAYER_STATUS_ITEMS_RESOURCE = ResourceBundle.getBundle(PLAYER_STATUS_ITEMS_PATH);
	
	public String getFromPlayerStatusItems(String str){
		return PLAYER_STATUS_ITEMS_RESOURCE.getString(str);
	}
	
	public Set<String> getKeysFromPlayerStatusItems(){
		return PLAYER_STATUS_ITEMS_RESOURCE.keySet();
	}
	
	private static final String ERROR_MESSAGES_PATH = "resources.errorMessages";
	private static final ResourceBundle ERROR_MESSAGES_RESOURCE = ResourceBundle.getBundle(ERROR_MESSAGES_PATH);
	
	public String getFromErrorMessages(String str){
		return ERROR_MESSAGES_RESOURCE.getString(str);
	}
	
	public Set<String> getKeysFromErrorMessages(){
		return ERROR_MESSAGES_RESOURCE.keySet();
	}
	
	private static final String FILE_PATHS_PATH = "resources.filePaths";
	private static final ResourceBundle FILE_PATHS_RESOURCE = ResourceBundle.getBundle(FILE_PATHS_PATH);
	
	public String getFromFilePaths(String str){
		return FILE_PATHS_RESOURCE.getString(str);
	}
	
	public Set<String> getKeysFromFilePaths(){
		return FILE_PATHS_RESOURCE.keySet();
	}
	
}
