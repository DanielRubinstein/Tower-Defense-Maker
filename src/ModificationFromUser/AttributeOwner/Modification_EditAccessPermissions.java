package ModificationFromUser.AttributeOwner;

import ModificationFromUser.ModificationFromUser;
import backEnd.Model;
import backEnd.GameData.State.AccessPermissions;
import backEnd.GameData.State.AccessPermissionsReader;

public class Modification_EditAccessPermissions implements ModificationFromUser{
	private AccessPermissions myAccessPermissions;
	private boolean permits;
	private String mode;
	
	public Modification_EditAccessPermissions(AccessPermissionsReader myAccessPermissions2, boolean permits, String mode) {
		// TODO fix casting issue
		this.myAccessPermissions = (AccessPermissions) myAccessPermissions2;
		this.permits = permits;
		this.mode = mode;
	}

	@Override
	public void invoke(Model myModel) throws Exception {
		//System.out.println(this.getClass().getSimpleName() + ": " + mode + " " + permits);
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