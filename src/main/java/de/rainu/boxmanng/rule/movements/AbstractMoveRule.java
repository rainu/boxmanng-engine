package de.rainu.boxmanng.rule.movements;

import de.rainu.boxmanng.exception.WorldAccessException;
import de.rainu.boxmanng.world.World;
import de.rainu.boxmanng.world.WorldCoord;
import de.rainu.boxmanng.world.WriteProtectedWorld;
import de.rainu.boxmanng.world.elements.WorldElement;

public abstract class AbstractMoveRule implements MoveController{
	protected World world;
	
	public AbstractMoveRule(World world){
		this.world = world;
	}
	
	@Override
	public void move(Class<? extends WorldElement> elementType, WorldCoord from, MoveDirection to) {
		checkPresentation(elementType, from);
		
		switch(to){
			case NORTH: moveNorth(elementType, from);
				break;
			case EAST: 
				moveEast(elementType, from);
				break;
			case SOUTH: 
				moveSouth(elementType, from);
				break;
			case WEST: 
				moveWest(elementType, from);
				break;
		}
	}
	
	@Override
	public World getBasedWorld() {
		return new WriteProtectedWorld(world);
	}
	
	/**
	 * Wirft eine Exception, wenn das element an der gegebenen Position nicht gefunden wurde.
	 * 
	 * @param elementType Element welches auf der Position vorhanden sein sollte.
	 * @param coord Position
	 */
	protected void checkPresentation(Class<? extends WorldElement> elementType, WorldCoord coord){
		if(!world.isPresent(coord, elementType)){
			throw new WorldAccessException("No such element (" + elementType + ") found on " + coord + ".");
		}
	}
}
