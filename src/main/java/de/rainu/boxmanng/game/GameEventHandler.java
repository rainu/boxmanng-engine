package de.rainu.boxmanng.game;

import de.rainu.boxmanng.world.WorldCoord;
import de.rainu.boxmanng.world.elements.WorldElement;

public interface GameEventHandler {

	/**
	 * Wird aufgerufen, wenn das Spiel gewonnen wurde.
	 */
	public void handleOnSolved();
	
	/**
	 * Wird aufgerufen, wenn das Spiel als verloren gilt.
	 */
	public void handleOnGameOver();
	
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
