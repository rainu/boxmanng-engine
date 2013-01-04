package de.rainu.boxmanng.world.elements;

import de.rainu.boxmanng.world.WorldCoord;

public class Player extends WorldElement {
	final int playerNumber;

	public Player(int number){
		playerNumber = number;
	}
	
	public int getPlayerNumber() {
		return playerNumber;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		Player clone = new Player(this.getPlayerNumber());

		int x = getPosition().getX();
		int y = getPosition().getY();
		clone.setPosition(new WorldCoord(x, y));
		
		return clone;
	}
	
	@Override
	public String getRepresentation() {
		return "P";
	}
}
