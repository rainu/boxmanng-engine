package de.rainu.boxmanng.game;

/**
 * Diese Klasse stellt die entscheidene Schnittstelle zum Client dar.
 * Mit dieser kann man das gesamte Spiel steuern.
 */
public interface SinglePlayerEngine extends Engine{

	/**
	 * Bewegt den Spieler nach Norden.
	 */
	public void moveNorth();
	
	/**
	 * Bewegt den Spieler nach Osten.
	 */
	public void moveEast();
	
	/**
	 * Bewegt den Spieler nach Süden.
	 */
	public void moveSouth();
	
	/**
	 * Bewegt den Spieler nach Westen.
	 */
	public void moveWest();
}
