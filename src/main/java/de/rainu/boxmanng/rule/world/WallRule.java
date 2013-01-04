package de.rainu.boxmanng.rule.world;

import java.util.Collection;

import de.rainu.boxmanng.exception.RuleException;
import de.rainu.boxmanng.world.World;
import de.rainu.boxmanng.world.WorldChangeHandler;
import de.rainu.boxmanng.world.WorldCoord;
import de.rainu.boxmanng.world.elements.Wall;
import de.rainu.boxmanng.world.elements.WorldElement;

/**
 * Dieser Decorator dient dazu alle Aktionen zu verhindern,
 * die im Zuammenhang mit einer Wand nicht funktionieren sollen.
 * Bspw. ist das Setzen eines Spielers auf eine Wand nicht vorgesehen.
 * 
 */
public class WallRule implements World {
	private World delegate;
	
	public WallRule(World world){
		this.delegate = world;
	}

	public void add(WorldCoord coord, WorldElement element) {
		if(element != null && element.getClass() == Wall.class){
			checkCoord(coord);
		}
		
		delegate.add(coord, element);
	}
	
	public void move(WorldCoord from, WorldCoord to, Class<? extends WorldElement> elementType) {
		if(elementType != null && elementType == Wall.class){
			checkCoord(to);
			if(isPresent(to, Wall.class)){
				//wenn schon eine Wand vorhanden ist...
				throw new RuleException("There is a another WALL on position " + to + "!");
			}
		}

		delegate.move(from, to, elementType);
	}
	
	protected void checkCoord(WorldCoord coord){
		//eine mauer kann ich nur dann setzen, wenn nichts anderes vorhanden ist!
		Collection<WorldElement> elements = get(coord);
		if(elements.size() > 0){
			if(elements.size() == 1){
				for(WorldElement element : elements){
					if(element instanceof Wall){
						return;
					}
				}
			}
			throw new RuleException("There are other element(s) on " + coord + ". A WALL can only set if there is nothing!");
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
