package de.rainu.boxmanng.rule.movements;

import de.rainu.boxmanng.exception.RuleException;
import de.rainu.boxmanng.world.WorldCoord;
import de.rainu.boxmanng.world.elements.Mark;
import de.rainu.boxmanng.world.elements.WorldElement;

/**
 * Diese Klasse verhindert, dass man eine Markierung bewegen kann.
 */
public class MarkMoveRule extends AbstractMoveRule {
	private MoveController delegate;
	
	public MarkMoveRule(MoveController controller) {
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
		if(elementType == Mark.class){
			throw new RuleException("Marks can not move!");
		}
	}
}
