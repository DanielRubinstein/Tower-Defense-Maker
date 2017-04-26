package ModificationFromUser.AttributeOwner;

import ModificationFromUser.ModificationFromUser;
import backEnd.ModelImpl;

public class Modification_PurchaseComponent implements ModificationFromUser{
	private int pointChange;
	
	public Modification_PurchaseComponent(int pointChange){
		this.pointChange = pointChange;
	}

	@Override
	public void invoke(ModelImpl myModel) throws Exception {
		myModel.getGameData().getStatus().decrementStatusItem("Money", pointChange);
	}

}
