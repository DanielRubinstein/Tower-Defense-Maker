package ModificationFromUser.savingAndLoading;

import java.io.File;

import ModificationFromUser.ModificationFromUser;
import backEnd.Model;

public class Modification_RestartLevel implements ModificationFromUser
{

	@Override
	public void invoke(Model myModel) throws Exception
	{
		myModel.getGameLoader().accept(new File("data/games/" + myModel.getMode().getGameMode() + "/templates/" + myModel.getMode().getLevelMode()));
	}

}
