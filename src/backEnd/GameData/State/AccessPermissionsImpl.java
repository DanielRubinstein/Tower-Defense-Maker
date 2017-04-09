package backEnd.GameData.State;

import java.util.List;

import backEnd.Mode.GameModeType;
import backEnd.Mode.UserModeType;

public class AccessPermissionsImpl implements AccessPermissions {

	private List<GameModeType> gameModePermissions;
	private List<UserModeType> userModePermissions;
	
	public AccessPermissionsImpl(List<GameModeType> gameModePermissions, List<UserModeType> userModePermissions){
		//if (!gameModePermissions.contains(GameModeType.DEFAULT)) gameModePermissions.add(GameModeType.DEFAULT);
		this.gameModePermissions = gameModePermissions;
		this.userModePermissions = userModePermissions;
	}
	
	@Override
	public void addAccessPermission(GameModeType gameMode) {
		gameModePermissions.add(gameMode);
	}

	@Override
	public void addAccessPermission(UserModeType userMode) {
		userModePermissions.add(userMode);
	}

	@Override
	public boolean permitsAccess(GameModeType gameMode) {
		return gameModePermissions.contains(gameMode);
	}

	@Override
	public boolean permitsAccess(UserModeType userMode) {
		return userModePermissions.contains(userMode);
	}

}
