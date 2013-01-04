package de.rainu.boxmanng.rule.movements;

import de.rainu.boxmanng.world.World;
import de.rainu.boxmanng.world.WorldCoord;
import de.rainu.boxmanng.world.elements.WorldElement;

/**
 * Laut dieser Klasse kann sich jedes Element um eins bewegen!
 */
public class BaseMoveRule extends AbstractMoveRule {
	public BaseMoveRule(World world) {
		super(world);
	}

	@Override
	public void moveNorth(Class<? extends WorldElement> elementType, WorldCoord from) {
		checkPresentation(elementType, from);
		
		WorldCoord to = calculateTargetCoord(from, MoveDirection.NORTH);
		world.move(from, to, elementType);
	}

	@Override
	public void moveEast(Class<? extends WorldElement> elementType, WorldCoord from) {
		checkPresentation(elementType, from);
		
		WorldCoord to = calculateTargetCoord(from, MoveDirection.EAST);
		world.move(from, to, elementType);
	}

	@Override
	public void moveSouth(Class<? extends WorldElement> elementType, WorldCoord from) {
		checkPresentation(elementType, from);
		
		WorldCoord to = calculateTargetCoord(from, MoveDirection.SOUTH);
		world.move(from, to, elementType);
	}

	@Override
	public void moveWest(Class<? extends WorldElement> elementType, WorldCoord from) {
		checkPresentation(elementType, from);
		
		WorldCoord to = calculateTargetCoord(from, MoveDirection.WEST);
		world.move(from, to, elementType);
	}

	protected WorldCoord calculateTargetCoord(WorldCoord source, MoveDirection direction){
		switch(direction){
		case NORTH:	return new WorldCoord(source.getX(), source.getY() - 1);
		case EAST:	return new WorldCoord(source.getX() + 1, source.getY());
		case SOUTH:	return new WorldCoord(source.getX(), source.getY() + 1);
		case WEST:	return new WorldCoord(source.getX() - 1, source.getY());
		}
		
		return null;
	}
}
