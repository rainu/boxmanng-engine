package de.rainu.boxmanng.world;

/**
 * Diese Klasse repräsentiert eine Koordinate einer Welt.
 */
public class WorldCoord {
	private int x;
	private int y;
	
	WorldCoord(){
		
	}
	
	public WorldCoord(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	void setY(int y) {
		this.y = y;
	}
	
	public WorldCoord getClone(){
		try {
			return this.clone();
		} catch (CloneNotSupportedException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	protected WorldCoord clone() throws CloneNotSupportedException {
		return new WorldCoord(new Integer(x), new Integer(y));
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
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
		WorldCoord other = (WorldCoord) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "[x=" + x + ", y=" + y + "]";
	}
}
