package de.rainu.boxmanng.rule;

import de.rainu.boxmanng.rule.movements.MoveController;
import de.rainu.boxmanng.world.World;

public interface RuleMaster {
	
	/**
	 * Liefert die zu verwendetende Bewegungsregel.
	 * 
	 * @return
	 */
	public MoveController getMoveRule();
	
	/**
	 * Liefert die zu verwendetende Welt und damit die zu verwendende Regeln.
	 * 
	 * @return
	 */
	public World getWorldRule();
	
	/**
	 * Liefert die zu verwendetenden allgemeinen Regeln.
	 * 
	 * @return
	 */
	public GeneralGameRule getGeneralGameRule();
}
