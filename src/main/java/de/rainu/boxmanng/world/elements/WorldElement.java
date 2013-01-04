package de.rainu.boxmanng.world.elements;

import de.rainu.boxmanng.world.WorldCoord;

public abstract class WorldElement implements Cloneable {
	private WorldCoord position;
	
	public abstract String getRepresentation();

	@Override
	protected abstract Object clone() throws CloneNotSupportedException;
	
	public WorldElement getClone(){
		try {
			return (WorldElement) clone();
		} catch (CloneNotSupportedException e) {
			throw new IllegalStateException(e);
		}
	}
	
	public WorldCoord getPosition() {
		return position;
	}
	public void setPosition(WorldCoord position) {
		this.position = position;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((position == null) ? 0 : position.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WorldElement other = (WorldElement) obj;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		return true;
	}
}
