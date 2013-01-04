package de.rainu.boxmanng.rule;

import de.rainu.boxmanng.rule.builder.MovementBuilder;
import de.rainu.boxmanng.rule.builder.WorldBuilder;
import de.rainu.boxmanng.rule.movements.MarkMoveRule;
import de.rainu.boxmanng.rule.movements.MoveController;
import de.rainu.boxmanng.rule.movements.PlayerMoveRule;
import de.rainu.boxmanng.rule.movements.WallMoveRule;
import de.rainu.boxmanng.rule.world.BoxRule;
import de.rainu.boxmanng.rule.world.MarkRule;
import de.rainu.boxmanng.rule.world.PlayerRule;
import de.rainu.boxmanng.rule.world.WallRule;
import de.rainu.boxmanng.world.World;

/**
 * Diese Klasse liefert alle Regeln die zum classischen Spiel gehören.
 */
public class ClassicRuleMaster implements RuleMaster {
	private final int width;
	private final int height;
	private MoveController usedController;
	private World usedWorld;
	private GeneralGameRule usedGameRule;
	
	/**
	 * Erstellt einen RuleMaster für das classische Spiel.
	 * 
	 * @param width Breite der Welt
	 * @param height Höhe der Welt
	 */
	public ClassicRuleMaster(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public MoveController getMoveRule() {
		if(usedController == null){
			MovementBuilder builder = new MovementBuilder(getWorldRule());
			//die Reihenfolge ist bedingt wichtig
			builder.addRule(WallMoveRule.class);
			builder.addRule(MarkMoveRule.class);
			builder.addRule(PlayerMoveRule.class);
			
			usedController = builder.build();
		}
		return usedController;
	}

	@Override
	public World getWorldRule() {
		if(usedWorld == null){
			WorldBuilder builder = new WorldBuilder(width, height);
			builder.addRule(WallRule.class);
			builder.addRule(MarkRule.class);
			builder.addRule(PlayerRule.class);
			builder.addRule(BoxRule.class);
			
			usedWorld = builder.build();
		}
		return usedWorld;
	}

	@Override
	public GeneralGameRule getGeneralGameRule() {
		if(usedGameRule == null){
			usedGameRule = new ClassicGeneralGameRule();
		}
		return usedGameRule;
	}

}
