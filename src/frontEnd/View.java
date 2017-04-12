package frontEnd;

import ModificationFromUser.ModificationFromUser;

public interface View extends ViewReader{

	public Object save();
	
	public Object load();
	
	public Object newGame();

	public Object viewRules();

	public Object editRules();
	
	public void sendUserModification(ModificationFromUser mod);

}
