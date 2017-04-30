package ModificationFromUser.AttributeOwner;

import ModificationFromUser.ModificationFromUser;
import backEnd.Model;
import backEnd.GameData.State.AccessPermissions;

public class Modification_EditAccessPermissions implements ModificationFromUser{
	private AccessPermissions myAccessPermissions;
	private boolean permits;
	private String mode;
	
	public Modification_EditAccessPermissions(AccessPermissions accessPermissions, boolean permits, String mode) {
		this.myAccessPermissions = accessPermissions;
		this.permits = permits;
		this.mode = mode;
	}

	@Override
	public void invoke(Model myModel) throws Exception {
		System.out.println(this.getClass().getSimpleName() + ": " + mode + " " + permits);
		if (myModel.getMode().getAllUserModes().contains(mode)){
			if (permits){
				myAccessPermissions.addUserAccessPermission(mode);
			}
			else {
				myAccessPermissions.removeUserAccessPermission(mode);
			}
		}
		else if (myModel.getLevelProgressionController().getGameList().contains(mode)){
			if (permits){
				myAccessPermissions.addGameAccessPermission(mode);
			}
			else {
				myAccessPermissions.removeGameAccessPermission(mode);
			}
		}
		else if (myModel.getLevelProgressionController().contains(mode)){
			if (permits){
				myAccessPermissions.addLevelAccessPermission(mode);
			}
			else {
				myAccessPermissions.removeLevelAccessPermission(mode);
			}
		}
		myModel.getDataController().saveUniversalGameData();
		
	}
}