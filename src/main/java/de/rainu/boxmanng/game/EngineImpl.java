package de.rainu.boxmanng.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.rainu.boxmanng.exception.GameException;
import de.rainu.boxmanng.exception.WorldAccessException;
import de.rainu.boxmanng.rule.GeneralGameRule;
import de.rainu.boxmanng.rule.RuleMaster;
import de.rainu.boxmanng.rule.movements.MoveController;
import de.rainu.boxmanng.rule.movements.MoveController.MoveDirection;
import de.rainu.boxmanng.world.World;
import de.rainu.boxmanng.world.WorldChangeHandler;
import de.rainu.boxmanng.world.WorldCoord;
import de.rainu.boxmanng.world.elements.Player;
import de.rainu.boxmanng.world.elements.WorldElement;

public class EngineImpl implements SinglePlayerEngine, MultiPlayerEngine{
	private World world;
	private MoveController controller;
	private GeneralGameRule gameRule;
	private List<GameEventHandler> allHandler;
	private boolean isPerformMoveAction;
	private boolean isForeignAccess = false;
	
	private EngineImpl(RuleMaster ruleMaster) {
		this.world = ruleMaster.getWorldRule();
		this.controller = ruleMaster.getMoveRule();
		this.gameRule = ruleMaster.getGeneralGameRule();
		
		this.allHandler = new ArrayList<GameEventHandler>();
		this.world.addChangeHandler(worldHandler);
	}
	
	public static class Builder {
		private RuleMaster ruleMaster;
		
		public Builder(RuleMaster ruleMaster){
			this.ruleMaster = ruleMaster;
		}
		
		public SinglePlayerEngine buildSP(){
			World world = ruleMaster.getWorldRule();
			int playerCount = world.get(Player.class).size();
			
			if(playerCount != 1){
				throw new GameException("A SinglePlayer-Game must have exactly ONE Player.");
			}
			
			return new EngineImpl(ruleMaster);
		}
		
		public MultiPlayerEngine buildMP(){
			World world = ruleMaster.getWorldRule();
			int playerCount = world.get(Player.class).size();
			
			if(playerCount == 0){
				throw new GameException("A MultiPlayer-Game must have less than 0 player(s).");
			}
			
			return new EngineImpl(ruleMaster);
		}
	}
	
	@Override
	public void addEventHandler(GameEventHandler handler) {
		if(!allHandler.contains(handler)){
			//nur neue
			allHandler.add(handler);
		}
	}

	@Override
	public synchronized void moveNorth(int player) {
		move(player, MoveDirection.NORTH);
	}

	@Override
	public synchronized void moveEast(int player) {
		move(player, MoveDirection.EAST);
	}

	@Override
	public synchronized void moveSouth(int player) {
		move(player, MoveDirection.SOUTH);
	}

	@Override
	public synchronized void moveWest(int player) {
		move(player, MoveDirection.WEST);
	}
	
	@Override
	public synchronized void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public synchronized void back() {
		// TODO Auto-generated method stub
	}

	@Override
	public void moveNorth() {
		moveNorth(0);
	}

	@Override
	public void moveEast() {
		moveEast(0);
	}

	@Override
	public void moveSouth() {
		moveSouth(0);
	}

	@Override
	public void moveWest() {
		moveWest(0);
	}
	
	protected void move(int player, MoveDirection direction){
		if(isGameOver()) return;
		checkForeignAccess();
		
		Player oPlayer = getPlayerByNumber(player);
		if(oPlayer == null) throw new WorldAccessException("Can not find player #" + player);
		
		isPerformMoveAction = true;
			controller.move(Player.class, oPlayer.getPosition(), direction);
		isPerformMoveAction = false;
		
		checkSolved();
	}
	
	protected boolean isGameOver(){
		if(gameRule.isGameOver(world)){
			//fire events
			if(allHandler != null) for(GameEventHandler handler : allHandler){
				handler.handleOnGameOver();
			}
			
			return true;
		}
		
		if(gameRule.isSolved(world)){
			return true;
		}
		
		return false;
	}
	
	protected void checkSolved(){
		if(gameRule.isSolved(world)){
			//fire events
			if(allHandler != null) for(GameEventHandler handler : allHandler){
				handler.handleOnSolved();
			}
		}
	}
	
	private void checkForeignAccess(){
		if(isForeignAccess){
			throw new WorldAccessException("The world has been manipulated by a stranger unit!");
		}
	}
	
	protected Player getPlayerByNumber(int playerNumber){
		Collection<Player> players = world.getElements(Player.class);
		
		if(players != null) for(Player curPlayer : players){
			if(curPlayer.getPlayerNumber() == playerNumber){
				return curPlayer;
			}
		}
		
		return null;
	}

	private WorldChangeHandler worldHandler = new WorldChangeHandler() {
		@Override
		public void handleOnElementRemove(WorldCoord coord, Class<? extends WorldElement> elementType) {
			if(!isPerformMoveAction){
				//fremdzugriff!
				handleForeignAccess();
			}

			//weiterreichen
			if(allHandler != null) for(GameEventHandler handler : allHandler){
				handler.handleOnElementRemove(coord, elementType);
			}
		}

		@Override
		public void handleOnElementAdd(WorldCoord coord, WorldElement element) {
			if(!isPerformMoveAction){
				//fremdzugriff!
				handleForeignAccess();
			}
			
			//weiterreichen
			if(allHandler != null) for(GameEventHandler handler : allHandler){
				handler.handleOnElementAdd(coord, element);
			}
		}
		
		private void handleForeignAccess(){
			/*
			 * Eine Welt kann nicht ohne weiteres kopiert werden.
			 * Wir müssen aber sicherstellen, dass die Welt nur
			 * von dieser Klasse aus manipuliert werden kann.
			 * Aus diesem Grund haben wir ein Flag welches anzeigt,
			 * ob diese Klasse eine Aktion ausführt. Da Move-Aktionen
			 * atomar sind, sollten wir somit mitbekommen, fals jemand
			 * anders von außen die Welt manipuliert (wir haben einen
			 * Listener auf der Welt angemeldet)
			 */
			isForeignAccess = true;
		}
	};
}
