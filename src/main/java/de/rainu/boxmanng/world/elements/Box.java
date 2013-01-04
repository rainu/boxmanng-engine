package de.rainu.boxmanng.world.elements;

import de.rainu.boxmanng.world.WorldCoord;

public class Box extends WorldElement {

	@Override
	protected Object clone() throws CloneNotSupportedException {
		Box clone = new Box();

		int x = getPosition().getX();
		int y = getPosition().getY();
		clone.setPosition(new WorldCoord(x, y));
		
		return clone;
	}
	
	@Override
	public String getRepresentation() {
		return "O";
	}
}
