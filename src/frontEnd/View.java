package frontEnd;

import ModificationFromUser.ModificationFromUser;
import backEnd.Attribute.AttributeOwnerReader;
import backEnd.Bank.BankController;
import javafx.scene.Node;

public interface View extends ViewReader{

	public void save();
	
	public void load();
	
	public void newGame();

	public void viewRules();

	public void editRules();
	
	public void step();
	
	public void sendUserModification(ModificationFromUser mod);
	
	public void addToCanvas(AttributeOwnerReader ao);

	public BankController getBankController();
	
	public void play();
	
	public Node getCanvas();

}
