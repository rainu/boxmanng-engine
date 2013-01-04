package de.rainu.boxmanng.world;

import java.util.Arrays;

import de.rainu.boxmanng.exception.WorldAccessException;
import de.rainu.boxmanng.exception.WorldBuildException;
import de.rainu.boxmanng.world.elements.WorldElement;

public class WorldLayer {
	private WorldElement[][] layer;
	
	/**
	 * Wird für das cloning verwendet, sonst nirgends!
	 */
	private WorldLayer(){
		
	}
	
	public WorldLayer(int width, int height){
		if(width <= 0) throw new WorldBuildException("The world width must greater than zero!");
		if(height <= 0) throw new WorldBuildException("The world height must greater than zero!");
		
		this.layer = new WorldElement[height][width];
		
		//fill world with empty fields
		for(WorldElement[] col : layer) Arrays.fill(col, null);
	}
	
	public void set(WorldCoord coord, WorldElement element){
		set(coord.getX(), coord.getY(), element);
	}
		
	private void checkCoordinate(int x, int y){
		if(y >= layer.length || y < 0){
			throw new WorldAccessException("The y-coordinate is invalid! Height of world: " + (layer.length - 1) + " and given coordinate: " + y);
		}
		if(x >= layer[0].length || x < 0){
			throw new WorldAccessException("The x-coordinate is invalid! Width of world: " + (layer[0].length - 1) + " and given coordinate: " + x);
		}
	}
	
	public void set(int x, int y, WorldElement element){
		checkCoordinate(x, y);
		
		layer[y][x] = element;
		if(element != null)	element.setPosition(new WorldCoord(x, y));
	}
	
	public WorldElement get(int x, int y){
		checkCoordinate(x, y);
		
		return layer[y][x];
	}
	
	public WorldElement get(WorldCoord coord){
		return get(coord.getX(), coord.getY());
	}
	
	public int getWidth(){
		return layer[0].length;
	}
	
	public int getHeight(){
		return layer.length;
	}
	
	protected WorldElement[][] getWorldLayer(){
		return layer;
	}
		
	@Override
	protected WorldLayer clone() throws CloneNotSupportedException {
		WorldLayer dolly = new WorldLayer(this.getWidth(), this.getHeight());
		
		for(int x=0; x < this.getWidth(); x++){
			for(int y=0; y < this.getHeight(); y++){
				WorldElement original = this.get(new WorldCoord(x, y));
				dolly.set(x, y, original == null ? null : original.getClone());	//getClone ist hier sehr wichtig!
			}
		}
		
		return dolly;
	}
	
	public WorldLayer getClone(){
		try {
			return this.clone();
		} catch (CloneNotSupportedException e) {
			throw new IllegalStateException(e);
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(layer);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof WorldLayer))
			return false;
		WorldLayer other = (WorldLayer) obj;
		if (!Arrays.deepEquals(getWorldLayer(), other.getWorldLayer()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();
		
		for(int y=0; y < layer.length; y++){
			for(int x=0; x < layer[y].length; x++){
				WorldElement e = layer[y][x];
				
				String representation = " ";
				if(e != null){
					representation = e.getRepresentation();
				}
				
				buffer.append(representation);
			}
			buffer.append("\n");
		}
		
		return buffer.toString();
	}
}
