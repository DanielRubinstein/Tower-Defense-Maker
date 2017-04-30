package ModificationFromUser.AttributeOwner;

import ModificationFromUser.ModificationFromUser;
import backEnd.Model;
import backEnd.ModelImpl;
import frontEnd.CustomJavafxNodes.ErrorDialog;

public class Modification_PurchaseComponent implements ModificationFromUser{
	private int cost;
	
	public Modification_PurchaseComponent(int cost){
		this.cost = cost;
	}

	@Override
	public void invoke(Model myModel) throws Exception {
		if (myModel.getPlayerStatusReader().getProperty("Money").getValue() - cost < 0){
			new ErrorDialog().create("Not Enough Money", "You don't have enough money to purchase this component");
		}
		myModel.getGameData().getStatus().decrementStatusItem("Money", cost);
	}

}
