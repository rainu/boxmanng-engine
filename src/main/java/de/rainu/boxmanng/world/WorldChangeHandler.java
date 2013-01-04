package de.rainu.boxmanng.world;

import de.rainu.boxmanng.world.elements.WorldElement;

public interface WorldChangeHandler {
	/**
	 * Wird aufgerufen, wenn ein Element sein Platz verlassen hat.
	 * 
	 * @param coord Position auf dem das element <b>war</b>.
	 * @param elementType Element welches entfernt wurde.
	 */
	public void handleOnElementRemove(WorldCoord coord, Class<? extends WorldElement> elementType);
	
	/**
	 * Wird aufgerufen, wenn ein Element hinzugefügt wurde.
	 * 
	 * @param coord Position auf dem das Element hinzugefügt wurde.
	 * @param element Element welches hinzugefügt wurde.
	 */
	public void handleOnElementAdd(WorldCoord coord, WorldElement element);
}
