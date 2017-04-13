package backEnd.GameData.State;

import java.util.ArrayList;
import java.util.List;

import backEnd.Mode.GameModeType;
import backEnd.Mode.UserModeType;

public class AccessPermissionsImpl implements AccessPermissions {

	private ArrayList<GameModeType> gameModePermissions;
	private ArrayList<UserModeType> userModePermissions;
	
	public AccessPermissionsImpl(){
		this.gameModePermissions = new ArrayList<GameModeType>();
		this.gameModePermissions.add(GameModeType.DEFAULT);
		this.userModePermissions = new ArrayList<UserModeType>();
	}
	
	public AccessPermissionsImpl(List<GameModeType> gameModePermissions, List<UserModeType> userModePermissions){
		this.gameModePermissions = new ArrayList<GameModeType>(gameModePermissions);
		if (!this.gameModePermissions.contains(GameModeType.DEFAULT)) {
			this.gameModePermissions.add(GameModeType.DEFAULT);
		}
		this.userModePermissions = new ArrayList<UserModeType>(userModePermissions);
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