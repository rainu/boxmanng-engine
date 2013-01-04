package de.rainu.boxmanng.game;

public interface Engine {

	/**
	 * Fügt einen Handler zu der Engine hinzu. Die Engine benachrichtigt bei einem Event
	 * alle ihr übergebenen Handler.
	 * 
	 * @param handler
	 */
	public void addEventHandler(GameEventHandler handler);
	
	/**
	 * Startet das Spiel neu.
	 */
	public void reset();
	
	/**
	 * Rollt eine Aktion zurück.
	 */
	public void back();
}
