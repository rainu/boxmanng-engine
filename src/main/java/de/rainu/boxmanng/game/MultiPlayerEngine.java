package de.rainu.boxmanng.game;

public interface MultiPlayerEngine extends Engine{
	
	/**
	 * Bewegt den übergebenen Spieler nach Norden.
	 * 
	 * @param player Id des Spielers
	 */
	public void moveNorth(int player);
	
	/**
	 * Bewegt den übergebenen Spieler nach Osten.
	 * 
	 * @param player Id des Spielers
	 */
	public void moveEast(int player);
	
	/**
	 * Bewegt den übergebenen Spieler nach Süden.
	 * 
	 * @param player Id des Spielers
	 */
	public void moveSouth(int player);
	
	/**
	 * Bewegt den übergebenen Spieler nach Westen.
	 * 
	 * @param player Id des Spielers
	 */
	public void moveWest(int player);
}
