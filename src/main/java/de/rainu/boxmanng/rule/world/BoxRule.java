package de.rainu.boxmanng.rule.world;

import java.util.Collection;

import de.rainu.boxmanng.exception.RuleException;
import de.rainu.boxmanng.world.World;
import de.rainu.boxmanng.world.WorldChangeHandler;
import de.rainu.boxmanng.world.WorldCoord;
import de.rainu.boxmanng.world.elements.Box;
import de.rainu.boxmanng.world.elements.Player;
import de.rainu.boxmanng.world.elements.Wall;
import de.rainu.boxmanng.world.elements.WorldElement;

/**
 * Die Klasse definiert wo eine Box hingesetzt werden darf.
 */
public class BoxRule implements World{
	private World delegate;
	
	public BoxRule(World world){
		this.delegate = world;
	}

	public void add(WorldCoord coord, WorldElement element) {
		if(element != null && element.getClass() == Box.class){
			checkCoord(coord);
		}
		
		delegate.add(coord, element);
	}

	public void move(WorldCoord from, WorldCoord to, Class<? extends WorldElement> elementType) {
		if(elementType != null && elementType == Box.class){
			checkCoord(to);
			if(isPresent(to, Box.class)){
				//wenn schon eine Box vorhanden ist...
				throw new RuleException("There is a another BOX on position " + to + "!");
			}
		}
		
		delegate.move(from, to, elementType);
	}
	
	protected void checkCoord(WorldCoord coord) {
		if(isPresent(coord, Wall.class)){
			throw new RuleException("There is a WALL on position " + coord + "!");
		}
		if(isPresent(coord, Player.class)){
			throw new RuleException("There is a PLAYER on position " + coord + "!");
		}
	}
	
	public void addChangeHandler(WorldChangeHandler handler) {
		delegate.addChangeHandler(handler);
	}
	
	public void clear(WorldCoord coord) {
		delegate.clear(coord);
	}

	public void remove(WorldCoord coord, Class<? extends WorldElement> elementType) {
		delegate.remove(coord, elementType);
	}

	public Collection<WorldElement> get(WorldCoord coord) {
		return delegate.get(coord);
	}
	
	public Collection<WorldCoord> get(Class<? extends WorldElement> elementType) {
		return delegate.get(elementType);
	}
	
	public <T extends WorldElement> Collection<T> getElements(Class<? extends T> elementType) {
		return delegate.getElements(elementType);
	}

	public boolean isPresent(WorldCoord coord, Class<? extends WorldElement> elementType) {
		return delegate.isPresent(coord, elementType);
	}

	public boolean isEmpty(WorldCoord coord) {
		return delegate.isEmpty(coord);
	}
}
