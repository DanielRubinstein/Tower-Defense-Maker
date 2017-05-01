package backEnd.GameData.State;

import java.util.ArrayList;
import java.util.List;

import resources.constants.StringResourceBundle;

public class AccessPermissionsImpl implements AccessPermissions {

	private ArrayList<String> userModePermissions;
	private ArrayList<String> gameModePermissions;
	private ArrayList<String> levelModePermissions;
	private StringResourceBundle strResources;
	
	public AccessPermissionsImpl(){
		this(new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>());
	}
	
	public AccessPermissionsImpl(List<String> gameModePermissions, List<String> userModePermissions, List<String> levelModePermissions){
		strResources = new StringResourceBundle();
		this.userModePermissions = new ArrayList<String>(userModePermissions);
		if (!this.userModePermissions.contains(strResources.getFromStringConstants("AUTHOR"))) {
			this.userModePermissions.add(strResources.getFromStringConstants("AUTHOR"));
		}
		this.gameModePermissions = new ArrayList<String>(gameModePermissions);
		if (!this.gameModePermissions.contains(strResources.getFromStringConstants("DEFAULT"))) {
			this.gameModePermissions.add(strResources.getFromStringConstants("DEFAULT"));
		}
		this.levelModePermissions = new ArrayList<String>(levelModePermissions);
		if (!this.levelModePermissions.contains(strResources.getFromStringConstants("DEFAULT"))) {
			this.levelModePermissions.add(strResources.getFromStringConstants("DEFAULT"));
		}
	}

	@Override
	public void addUserAccessPermission(String gameMode) {
		userModePermissions.add(gameMode);
	}

	@Override
	public void addGameAccessPermission(String gameMode) {
		gameModePermissions.add(gameMode);
	}
	
	@Override
	public void addLevelAccessPermission(String levelMode) {
		levelModePermissions.add(levelMode);
	}
	
	@Override
	public void removeUserAccessPermission(String gameMode) {
		userModePermissions.remove(gameMode);
	}

	@Override
	public void removeGameAccessPermission(String gameMode) {
		gameModePermissions.remove(gameMode);
	}
	
	@Override
	public void removeLevelAccessPermission(String levelMode) {
		levelModePermissions.remove(levelMode);
	}

	@Override
	public boolean permitsAccess(String userMode, String gameMode, String levelMode) {		
		if (userMode.equals(strResources.getFromStringConstants("AUTHOR"))){
			return true;
		}
		if(userMode.equals(strResources.getFromStringConstants("PLAYER"))){
			//return true; // FIXME needs LevelProgressionController
		}
		return (userModePermissions.contains(userMode) & gameModePermissions.contains(gameMode) & levelModePermissions.contains(levelMode));
	}

	@Override
	public List<String> getGameModeList() {
		return gameModePermissions;
	}

	@Override
	public List<String> getUserModeList() {
		return userModePermissions;
	}
	
	@Override
	public List<String> getLevelModeList() {
		return levelModePermissions;
	}

	@Override
	public boolean permitsAccess(String mode) {
		return (userModePermissions.contains(mode) || gameModePermissions.contains(mode) || levelModePermissions.contains(mode));
	}
}