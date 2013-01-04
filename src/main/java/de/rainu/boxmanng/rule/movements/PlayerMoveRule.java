
package de.rainu.boxmanng.rule.movements;

import de.rainu.boxmanng.exception.WorldAccessException;
import de.rainu.boxmanng.world.WorldCoord;
import de.rainu.boxmanng.world.elements.Box;
import de.rainu.boxmanng.world.elements.Player;
import de.rainu.boxmanng.world.elements.WorldElement;

/**
 * Diese Klasse sorgt dafür, dass ein Spieler eine Kiste vorwärts schiebt (falls vorhanden).
 */
public class PlayerMoveRule extends BaseMoveRule {
	private MoveController delegate;
	
	public PlayerMoveRule(MoveController controller) {
		super(null); //kann ich machen, da ich nur delegiere
		
		this.delegate = controller;
	}

	@Override
	public void moveNorth(Class<? extends WorldElement> elementType, WorldCoord from) {
		if(elementType != null && elementType == Player.class){
			if(isBoxInFrontOf(from, MoveDirection.NORTH)){
				//die vor mir liegende Box nach bewegen
				delegate.moveNorth(
						Box.class, 
						calculateTargetCoord(from, MoveDirection.NORTH));
			}
		}
		delegate.moveNorth(elementType, from);
	}

	@Override
	public void moveEast(Class<? extends WorldElement> elementType, WorldCoord from) {
		if(elementType != null && elementType == Player.class){
			if(isBoxInFrontOf(from, MoveDirection.EAST)){
				//die vor mir liegende Box nach bewegen
				delegate.moveEast(
						Box.class, 
						calculateTargetCoord(from, MoveDirection.EAST));
			}
		}
		delegate.moveEast(elementType, from);
	}

	@Override
	public void moveSouth(Class<? extends WorldElement> elementType, WorldCoord from) {
		if(elementType != null && elementType == Player.class){
			if(isBoxInFrontOf(from, MoveDirection.SOUTH)){
				//die vor mir liegende Box nach bewegen
				delegate.moveSouth(
						Box.class, 
						calculateTargetCoord(from, MoveDirection.SOUTH));
			}
		}
		delegate.moveSouth(elementType, from);
	}

	@Override
	public void moveWest(Class<? extends WorldElement> elementType, WorldCoord from) {
		if(elementType != null && elementType == Player.class){
			if(isBoxInFrontOf(from, MoveDirection.WEST)){
				//die vor mir liegende Box nach bewegen
				delegate.moveWest(
						Box.class, 
						calculateTargetCoord(from, MoveDirection.WEST));
			}
		}
		delegate.moveWest(elementType, from);
	}
	
	protected boolean isBoxInFrontOf(WorldCoord me, MoveDirection direction){
		try{
			WorldCoord frontOfMe = calculateTargetCoord(me, direction);
			return delegate.getBasedWorld().isPresent(frontOfMe, Box.class);
		}catch(WorldAccessException e){
			//vor mir ist die welt zu ende
			return false;
		}
	}
}
