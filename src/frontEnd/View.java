package frontEnd;

import ModificationFromUser.ModificationFromUser;
import backEnd.Model;
import backEnd.Bank.BankController;
import javafx.scene.Node;

public interface View extends ViewReader{

	public void viewRules();

	public void editRules();
	
	public void sendUserModification(ModificationFromUser mod);

	public BankController getBankController();
	
	public Node getScreenGrid();
}
