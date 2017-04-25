package backEnd.GameData.State;

import java.util.ArrayList;
import java.util.List;

public class AccessPermissionsImpl implements AccessPermissions {

	private ArrayList<String> userModePermissions;
	private ArrayList<String> gameModePermissions;
	private ArrayList<String> levelModePermissions;
	
	public AccessPermissionsImpl(){
		this.userModePermissions = new ArrayList<String>();
		this.userModePermissions.add("AUTHOR");
		this.userModePermissions.add("PLAYER"); // TODO figure this out
		this.gameModePermissions = new ArrayList<String>();
		this.gameModePermissions.add("DEFAULT");
		this.levelModePermissions = new ArrayList<String>();
		this.levelModePermissions.add("DEFAULT");
	}
	
	public AccessPermissionsImpl(List<String> gameModePermissions, List<String> userModePermissions, List<String> levelModePermissions){
		this.userModePermissions = new ArrayList<String>(userModePermissions);
		if (!this.userModePermissions.contains("AUTHOR")) {
			this.userModePermissions.add("AUTHOR");
		}
		this.gameModePermissions = new ArrayList<String>(gameModePermissions);
		if (!this.gameModePermissions.contains("DEFAULT")) {
			this.gameModePermissions.add("DEFAULT");
		}
		this.levelModePermissions = new ArrayList<String>(levelModePermissions);
		if (!this.levelModePermissions.contains("DEFAULT")) {
			this.levelModePermissions.add("DEFAULT");
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
		gameModePermissions.add(levelMode);
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
		gameModePermissions.remove(levelMode);
	}

	@Override
	public boolean permitsAccess(String userMode, String gameMode, String levelMode) {
		if (userModePermissions.contains(userMode) & gameModePermissions.contains(gameMode) & levelModePermissions.contains(levelMode)){
			return true;
		}
		return false;
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