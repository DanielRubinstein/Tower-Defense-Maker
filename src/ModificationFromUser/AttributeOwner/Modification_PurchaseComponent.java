package ModificationFromUser.AttributeOwner;

import ModificationFromUser.ModificationFromUser;
import backEnd.ModelImpl;
import frontEnd.CustomJavafxNodes.ErrorDialog;

public class Modification_PurchaseComponent implements ModificationFromUser{
	private int pointChange;
	
	public Modification_PurchaseComponent(int pointChange){
		this.pointChange = pointChange;
	}

	@Override
	public void invoke(ModelImpl myModel) throws Exception {
		if (myModel.getPlayerStatusReader().getProperty("Money").getValue() + pointChange < 0){
			new ErrorDialog().create("Not Enough Money", "You don't have enough money to purchase this component");
		}
		myModel.getGameData().getStatus().decrementStatusItem("Money", pointChange);
	}

}
