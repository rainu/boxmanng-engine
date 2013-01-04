package de.rainu.boxmanng.world;

import java.util.Collection;

import de.rainu.boxmanng.exception.WorldAccessException;
import de.rainu.boxmanng.world.elements.WorldElement;

/**
 * Diese Klasse stellt ein Schutzmechanismus für eine World dar.
 * Man kann ihr eine World geben, und sie verhindert, dass diese
 * verändert werden kann.
 */
public class WriteProtectedWorld implements World {
	private World delegate;
	
	/**
	 * Baut einen neue Schreibgeschüzte Welt.
	 * 
	 * @param toProtect World, welche "beschützt" werden soll.
	 */
	public WriteProtectedWorld(World toProtect) {
		this.delegate = toProtect;
	}
	@Override
	public void add(WorldCoord coord, WorldElement element) {
		throw new WorldAccessException("This world is write-protected!");
	}
	@Override
	public void clear(WorldCoord coord) {
		throw new WorldAccessException("This world is write-protected!");
	}
	@Override
	public void remove(WorldCoord coord, Class<? extends WorldElement> elementType) {
		throw new WorldAccessException("This world is write-protected!");
	}
	@Override
	public void move(WorldCoord from, WorldCoord to, Class<? extends WorldElement> elementType) {
		throw new WorldAccessException("This world is write-protected!");
	}
	@Override
	public void addChangeHandler(WorldChangeHandler handler) {
		throw new WorldAccessException("This world is write-protected!");
	}
	@Override
	public Collection<WorldElement> get(WorldCoord coord) {
		return delegate.get(coord);
	}
	@Override
	public Collection<WorldCoord> get(Class<? extends WorldElement> elementType) {
		return delegate.get(elementType);
	}
	@Override
	public <T extends WorldElement> Collection<T> getElements(Class<? extends T> elementType) {
		return delegate.getElements(elementType);
	}
	@Override
	public boolean isPresent(WorldCoord coord, Class<? extends WorldElement> elementType) {
		return delegate.isPresent(coord, elementType);
	}
	@Override
	public boolean isEmpty(WorldCoord coord) {
		return delegate.isEmpty(coord);
	}
}
