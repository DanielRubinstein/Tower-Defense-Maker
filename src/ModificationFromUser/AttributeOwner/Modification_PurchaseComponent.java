package ModificationFromUser.AttributeOwner;

import ModificationFromUser.ModificationFromUser;
import backEnd.Model;

public class Modification_PurchaseComponent implements ModificationFromUser{
	private int pointChange;
	
	public Modification_PurchaseComponent(int pointChange){
		this.pointChange = pointChange;
	}

	@Override
	public void invoke(Model myModel) throws Exception {
		myModel.getGameData().getStatus().decrementStatusItem("Money", pointChange);
	}

}
