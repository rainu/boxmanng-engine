package de.rainu.boxmanng.rule.movements;

import de.rainu.boxmanng.exception.RuleException;
import de.rainu.boxmanng.world.WorldCoord;
import de.rainu.boxmanng.world.elements.Wall;
import de.rainu.boxmanng.world.elements.WorldElement;

/**
 * Diese Klasse verhindert, dass man eine Wand bewegen kann.
 */
public class WallMoveRule extends AbstractMoveRule {
	private MoveController delegate;
	
	public WallMoveRule(MoveController controller) {
		super(null); //kann ich machen, da ich nur delegiere
		
		this.delegate = controller;
	}

	@Override
	public void moveNorth(Class<? extends WorldElement> elementType, WorldCoord from) {
		check(elementType);
		
		delegate.moveNorth(elementType, from);
	}

	@Override
	public void moveEast(Class<? extends WorldElement> elementType, WorldCoord from) {
		check(elementType);
		
		delegate.moveEast(elementType, from);
	}

	@Override
	public void moveSouth(Class<? extends WorldElement> elementType, WorldCoord from) {
		check(elementType);
		
		delegate.moveSouth(elementType, from);
	}

	@Override
	public void moveWest(Class<? extends WorldElement> elementType, WorldCoord from) {
		check(elementType);
		
		delegate.moveWest(elementType, from);
	}
	
	protected void check(Class<? extends WorldElement> elementType){
		if(elementType != null && elementType == Wall.class){
			throw new RuleException("Walls can not move!");
		}
	}
}
