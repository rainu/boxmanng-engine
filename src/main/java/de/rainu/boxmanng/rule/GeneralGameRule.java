package de.rainu.boxmanng.rule;

import de.rainu.boxmanng.exception.NotValidException;
import de.rainu.boxmanng.world.World;

public interface GeneralGameRule {

	/**
	 * Prüft ob die Welt gültig ist. Sollte sie nicht gültig sein, muss eine entsprechende
	 * {@link #NotValidException} geworfen werden.
	 * 
	 * @param world Welt die geprüft werden soll.
	 */
	public void chekValidity(World world) throws NotValidException;
	
	/**
	 * Prüft, ob die Welt als "gelöst" gilt.
	 * 
	 * @param world Welt die geprüft werden soll.
	 * @return <i>True</i> wenn die Welt gelöst wurde. Andernfals <i>false</i>.
	 */
	public boolean isSolved(World world);
	
	/**
	 * Prüft ob das Spiel weitergespielt werden kann.
	 * 
	 * @param world
	 * @return <i>True</i> falls weitergespielt werden kann. Andernfals <i>false</i>.
	 */
	public boolean isGameOver(World world);
}
