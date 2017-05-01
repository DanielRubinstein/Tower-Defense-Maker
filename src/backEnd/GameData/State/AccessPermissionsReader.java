package backEnd.GameData.State;

import java.util.List;

public interface AccessPermissionsReader {

	List<String> getGameModeList();

	List<String> getUserModeList();

	List<String> getLevelModeList();
	
	boolean permitsAccess(String mode);

}