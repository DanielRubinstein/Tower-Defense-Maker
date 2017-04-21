package backEnd.GameData.State;

import java.util.ArrayList;
import java.util.List;

public class AccessPermissionsImpl implements AccessPermissions {

	private ArrayList<String> gameModePermissions;
	private ArrayList<String> userModePermissions;
	
	public AccessPermissionsImpl(){
		this.gameModePermissions = new ArrayList<String>();
		this.gameModePermissions.add("DEFAULT");
		this.userModePermissions = new ArrayList<String>();
	}
	
	public AccessPermissionsImpl(List<String> gameModePermissions, List<String> userModePermissions){
		this.gameModePermissions = new ArrayList<String>(gameModePermissions);
		if (!this.gameModePermissions.contains("DEFAULT")) {
			this.gameModePermissions.add("DEFAULT");
		}
		this.userModePermissions = new ArrayList<String>(userModePermissions);
	}

	@Override
	public void addUserAccessPermission(String gameMode) {
		userModePermissions.add(gameMode);
	}

	@Override
	public void addGameAccessPermission(String userMode) {
		gameModePermissions.add(userMode);
	}

	@Override
	public boolean permitsAccess(String mode) {
		if (gameModePermissions.contains(mode) || userModePermissions.contains(mode)){
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
	
}