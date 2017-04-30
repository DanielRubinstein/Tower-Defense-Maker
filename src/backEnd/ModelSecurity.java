package backEnd;

import backEnd.Attribute.AttributeOwner;
import backEnd.Attribute.AttributeOwnerReader;
import backEnd.GameEngine.Engine.Spawning.SpawnData;
import backEnd.GameEngine.Engine.Spawning.SpawnDataReader;

public interface ModelSecurity {

	AttributeOwner getAttributeOwner(AttributeOwnerReader attributeOwnerReader);

	SpawnData getSpawnData(SpawnDataReader mySpawnDataReader);

}