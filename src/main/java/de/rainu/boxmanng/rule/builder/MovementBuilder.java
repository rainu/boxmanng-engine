package de.rainu.boxmanng.rule.builder;

import java.lang.reflect.Constructor;

import de.rainu.boxmanng.exception.WorldBuildException;
import de.rainu.boxmanng.rule.movements.BaseMoveRule;
import de.rainu.boxmanng.rule.movements.MoveController;
import de.rainu.boxmanng.world.World;

/**
 * Diese Klasse ist dafür zuständig, einen MoveController mit gewünschten
 * Regeln zu bauen.
 */
public class MovementBuilder {
	private MoveController base;

	/**
	 * Hier wird als Kern-Komponente eine {@link #BaseMoveRule} definiert.
	 * 
	 * @param world Die Welt mit der operiert werden soll.
	 */
	public MovementBuilder(World world){
		this(new BaseMoveRule(world));
	}
	
	/**
	 * Hier wird eine Kernkomponente vorrausgesetzt. Eine Kern-Komponente
	 * zeichnet sich dadurch aus, dass diese wirklich mit der zugrunde liegenden
	 * Welt operiert {@link World#move(de.rainu.boxmanng.world.WorldCoord, de.rainu.boxmanng.world.WorldCoord, byte)}
	 * 
	 * @param base Die Kernkomponente
	 */
	public MovementBuilder(MoveController base){
		this.base = base;
	}
	
	/**
	 * Fügt eine neue Regel hinzu. Eine Regel ist ein Decorator (siehe Decorator-Pattern).
	 * Eine Regel-Klasse benötigt einen Konstruktor, welcher ein {@link #MoveController} erwartet.
	 * 
	 * @param moveRuleClass Regel, die hinzugefügt werden soll.
	 * @throws WorldBuildException Wird geworfen, wenn aus irgendeinem Grund die Regel nicht hinzugefügt werden konnte.
	 */
	public void addRule(Class<? extends MoveController> moveRuleClass){
		MoveController extController = createInstance(moveRuleClass);
		
		//sobald eine neue instanz erstellt werden konnte,
		//stelt diese unseren neuen basis-controller dar.
		base = extController;
	}
	
	/**
	 * Liefert den fertig gebauten MoveController mit allen Regeln, die vorher mittels {@link #addRule(Class)} hinzugefügt
	 * wurden.
	 * 
	 * @return
	 */
	public MoveController build(){
		return base;
	}
	
	protected MoveController createInstance(Class<? extends MoveController> moveRuleClass){
		try {
			/*
			 * Die Regeln sind Decoratoren (siehe Decorator-Pattern). Das heißt
			 * man benötigt ein Kern-Element, welches man mit den Decoratoren
			 * erweitert. Wir haben als Basis ein einfachen MoveController, die nichts
			 * an erweiterter Logik beinhaltet. Diese können wir bspw. mit einer
			 * "Regel" unschließen, die dafür sorgt, dass ein Spieler eine Box verschiebt.
			 */
			Constructor<? extends MoveController> c = moveRuleClass.getConstructor(MoveController.class);
			return c.newInstance(base);
		} catch (Exception e) {
			throw new WorldBuildException("Can not wrap rule!", e);
		}
	}
}
