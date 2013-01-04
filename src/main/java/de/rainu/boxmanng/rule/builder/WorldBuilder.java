package de.rainu.boxmanng.rule.builder;

import java.lang.reflect.Constructor;

import de.rainu.boxmanng.exception.WorldBuildException;
import de.rainu.boxmanng.world.BaseWorld;
import de.rainu.boxmanng.world.World;

public class WorldBuilder {
	private World base;
	
	public WorldBuilder(int width, int height){
		this.base = new BaseWorld(width, height);
	}
	
	/**
	 * Fügt eine neue Regel hinzu. Eine Regel ist ein Decorator (siehe Decorator-Pattern).
	 * Eine Regel-Klasse benötigt einen Konstruktor, welcher eine {@link #World} erwartet.
	 * 
	 * @param worldRuleClass Regel, die hinzugefügt werden soll.
	 * @throws WorldBuildException Wird geworfen, wenn aus irgendeinem Grund die Regel nicht hinzugefügt werden konnte.
	 */
	public void addRule(Class<? extends World> worldRuleClass){
		World extWorld = createInstance(worldRuleClass);
		
		//sobald eine neue instanz erstellt werden konnte,
		//stelt diese unsere neue basis-welt dar.
		base = extWorld;
	}
	
	/**
	 * Liefert die fertig gebaute Welt mit allen Regeln, die vorher mittels {@link #addRule(Class)} hinzugefügt
	 * wurden.
	 * 
	 * @return
	 */
	public World build(){
		return base;
	}
	
	protected World createInstance(Class<? extends World> worldRuleClass){
		try {
			/*
			 * Die Regeln sind Decoratoren (siehe Decorator-Pattern). Das heißt
			 * man benötigt ein Kern-Element, welches man mit den Decoratoren
			 * erweitert. Wir haben als Basis eine einfache Welt, die nichts
			 * an erweiterter Logik beinhaltet. Diese können wir bspw. mit einer
			 * "Regel" unschließen, die dafür sorgt, dass keine Box auf eine Mauer
			 * gesetzt werden kann.
			 */
			Constructor<? extends World> c = worldRuleClass.getConstructor(World.class);
			return c.newInstance(base);
		} catch (Exception e) {
			throw new WorldBuildException("Can not wrap rule!", e);
		}
	}
}
