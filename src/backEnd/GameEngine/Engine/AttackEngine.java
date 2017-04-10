package backEnd.GameEngine.Engine;

import java.util.List;

import backEnd.GameData.State.ComponentGraph;
import backEnd.GameData.State.State;
import backEnd.GameEngine.Component;

public class AttackEngine implements Engine{

	@Override
	public void gameLoop(State currentState) {
		// TODO Auto-generated method stub
		List<Component> componentGraph = currentState.getComponentGraph().getComponentList();
		for(Component componentAttacker : componentGraph){
			if(componentAttacker.getAttribute("TYPE").getValue().equals("TOWERTYPE")){ //Fix with resource
				for(Component componentTarget : componentGraph){
					if(componentAttacker.getAttribute("TYPE").getValue().equals("TOWERTYPE")){ //Fix with resource
						componentAttacker.getBehavior("Attack").execute(null);
						componentTarget.getBehavior("Gitfked").execute(null); //Attacks everything and is wrong
					}
				}
			}
		}
	}

}
