package de.rainu.boxmanng.rule;

import java.util.Collection;

import de.rainu.boxmanng.exception.NotValidException;
import de.rainu.boxmanng.world.World;
import de.rainu.boxmanng.world.WorldCoord;
import de.rainu.boxmanng.world.elements.Box;
import de.rainu.boxmanng.world.elements.Mark;
import de.rainu.boxmanng.world.elements.Player;

/**
 * Diese Klasse stellt die allgemeinen Standart-Regeln des Spieles auf.
 */
public class ClassicGeneralGameRule implements GeneralGameRule {

	@Override
	public void chekValidity(World world) throws NotValidException {
		/* 
		 * Ein Spiel ist erst dann Valide wenn:
		 *  ~ es mindestens eine Markierung gibt
		 * 	~ es gleichviele Markierungen gibt wie Boxen
		 *	~ es mindestens einen Spieler gibt
		 */
		if(!hasAtLeastOneMark(world)){
			throw new NotValidException("There is no mark in world!");
		}
		if(!hasSameNumbeOfMarkSuchAsBox(world)){
			throw new NotValidException("There are not the same number of mark such as box.");
		}
		if(!hasAtLeastOnePlayer(world)){
			throw new NotValidException("There is no player in world!");
		}
	}
	
	protected boolean hasSameNumbeOfMarkSuchAsBox(World world){
		return 	world.get(Mark.class).size() ==
				world.get(Box.class).size();
	}
	
	protected boolean hasAtLeastOneMark(World world){
		return world.get(Mark.class).size() > 0;
	}
	
	protected boolean hasAtLeastOnePlayer(World world){
		return world.get(Player.class).size() > 0;
	}

	@Override
	public boolean isSolved(World world) {
		/*
		 * Gelöst ist das Spiel erst dann, wenn alle Markierungen
		 * mit einer Box belegt sind.
		 */
		Collection<WorldCoord> markCoords = world.get(Mark.class);
		Collection<WorldCoord> boxCoords = world.get(Box.class);
		
		for(WorldCoord curMarkCoord : markCoords){
			if(!boxCoords.contains(curMarkCoord)){
				return false;
			}
		}
		
		return true;
	}

	@Override
	public boolean isGameOver(World world) {
		//a never ending story
		return false;
	}
}
