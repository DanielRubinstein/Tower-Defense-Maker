package ModificationFromUser.AttributeOwner;

import ModificationFromUser.ModificationFromUser;
import backEnd.ModelImpl;
import backEnd.GameData.State.Component;

public class Modification_UpgradeComponent implements ModificationFromUser {
	private Component toUpgrade;
	
	public Modification_UpgradeComponent(Component obj) {
		this.toUpgrade = obj;
	}

	@Override
	public void invoke(ModelImpl myModel) throws Exception {
		System.out.println("SOMETHING SHOULD BE HAPPENING");
	}

}
