package frontEnd;

import ModificationFromUser.ModificationFromUser;
import backEnd.Attribute.AttributeOwnerReader;
import backEnd.Bank.BankController;

public interface View extends ViewReader{
	
	public BankController getBankController();

	public void save();
	
	public void load();
	
	public void newGame();

	public void viewRules();

	public void editRules();
	
	public void step();
	
	public void sendUserModification(ModificationFromUser mod);
	
	public void addToCanvas(AttributeOwnerReader ao);

}
