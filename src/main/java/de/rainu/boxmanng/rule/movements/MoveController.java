package de.rainu.boxmanng.rule.movements;

import de.rainu.boxmanng.world.World;
import de.rainu.boxmanng.world.WorldCoord;
import de.rainu.boxmanng.world.elements.WorldElement;

public interface MoveController {
	public static enum MoveDirection{
		NORTH, EAST, SOUTH, WEST
	}
	
	/**
	 * Bewegt das gegebene Element in die angegebene Richtung.
	 * 
	 * @param elementType Element welches bewegt werden soll.
	 * @param from Wo befindet sich das Element welches bewegt werden soll.
	 * @param to In welche Richtung soll es bewegt werden.
	 */
	public void move(Class<? extends WorldElement> elementType, WorldCoord from, MoveDirection to);
	
	/**
	 * Bewegt das gegebene Element in nach Norden.
	 * 
	 * @param elementType Element welches bewegt werden soll.
	 * @param from Wo befindet sich das Element welches bewegt werden soll.
	 */
	public void moveNorth(Class<? extends WorldElement> elementType, WorldCoord from);
	
	/**
	 * Bewegt das gegebene Element in nach Osten.
	 * 
	 * @param elementType Element welches bewegt werden soll.
	 * @param from Wo befindet sich das Element welches bewegt werden soll.
	 */
	public void moveEast(Class<? extends WorldElement> elementType, WorldCoord from);
	
	/**
	 * Bewegt das gegebene Element in nach Süden.
	 * 
	 * @param elementType Element welches bewegt werden soll.
	 * @param from Wo befindet sich das Element welches bewegt werden soll.
	 */
	public void moveSouth(Class<? extends WorldElement> elementType, WorldCoord from);
	
	/**
	 * Bewegt das gegebene Element in nach Westen.
	 * 
	 * @param elementType Element welches bewegt werden soll.
	 * @param from Wo befindet sich das Element welches bewegt werden soll.
	 */
	public void moveWest(Class<? extends WorldElement> elementType, WorldCoord from);
	
	/**
	 * Liefert die Welt auf der operiert wird.
	 * @return
	 */
	public World getBasedWorld();
}
