package frontEnd;

import ModificationFromUser.ModificationFromUser;

public interface View extends ViewReader{

	public void save();
	
	public void load();
	
	public void newGame();

	public void viewRules();

	public void editRules();
	
	public void step();
	
	public void sendUserModification(ModificationFromUser mod);

}
