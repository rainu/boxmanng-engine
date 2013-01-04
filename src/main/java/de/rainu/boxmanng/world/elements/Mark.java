package de.rainu.boxmanng.world.elements;

import de.rainu.boxmanng.world.WorldCoord;

public class Mark extends WorldElement {

	@Override
	protected Object clone() throws CloneNotSupportedException {
		Mark clone = new Mark();

		int x = getPosition().getX();
		int y = getPosition().getY();
		clone.setPosition(new WorldCoord(x, y));
		
		return clone;
	}
	
	@Override
	public String getRepresentation() {
		return "~";
	}
}
