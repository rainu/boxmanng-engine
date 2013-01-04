package de.rainu.boxmanng.rule.movements;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.rainu.boxmanng.exception.WorldAccessException;
import de.rainu.boxmanng.rule.movements.MoveController.MoveDirection;
import de.rainu.boxmanng.world.World;
import de.rainu.boxmanng.world.WorldCoord;
import de.rainu.boxmanng.world.WriteProtectedWorld;
import de.rainu.boxmanng.world.elements.Player;
import de.rainu.boxmanng.world.elements.WorldElement;

@RunWith(MockitoJUnitRunner.class)
public class TestAbstractMoveRule {

	@Mock
	World mockedWorld;
	
	AbstractMoveRule toTest;
	
	@Before
	public void init(){
		toTest = spy(new AbstractMoveRule(mockedWorld) {
			@Override
			public void moveWest(Class<? extends WorldElement> elementType, WorldCoord from) {
			}
			@Override
			public void moveSouth(Class<? extends WorldElement> elementType, WorldCoord from) {
			}
			@Override
			public void moveNorth(Class<? extends WorldElement> elementType, WorldCoord from) {
			}
			@Override
			public void moveEast(Class<? extends WorldElement> elementType, WorldCoord from) {
			}
		});
	}
	
	@After
	public void clear(){
		reset(mockedWorld);
		reset(toTest);
	}
	
	@Test
	public void getBasedWorld(){
		World result = toTest.getBasedWorld();
		
		assertNotSame(result, mockedWorld);
		assertTrue(result instanceof WriteProtectedWorld);
	}
	
	@Test
	public void checkPresentation(){
		doReturn(true).when(mockedWorld).isPresent(any(WorldCoord.class), any(Class.class));
		
		try{
			toTest.checkPresentation(Player.class, null);
		}catch(WorldAccessException e){
			e.printStackTrace();
			fail("It was thrown an exception!");
		}
		
		doReturn(false).when(mockedWorld).isPresent(any(WorldCoord.class), any(Class.class));
		
		try{
			toTest.checkPresentation(Player.class, null);
			fail("It should be thrown an exception!");
		}catch(WorldAccessException e){}
	}
	
	@Test
	public void testMove(){
		WorldCoord from = new WorldCoord(0, 0);
		doNothing().when(toTest).checkPresentation(any(Class.class), any(WorldCoord.class));
		
		toTest.move(Player.class, from, MoveDirection.NORTH);
		
		verify(toTest, times(1)).moveNorth(eq(Player.class), eq(from));
		verify(toTest, never()).moveEast(eq(Player.class), eq(from));
		verify(toTest, never()).moveSouth(eq(Player.class), eq(from));
		verify(toTest, never()).moveWest(eq(Player.class), eq(from));
		
		toTest.move(Player.class, from, MoveDirection.EAST);
		
		verify(toTest, times(1)).moveNorth(eq(Player.class), eq(from));
		verify(toTest, times(1)).moveEast(eq(Player.class), eq(from));
		verify(toTest, never()).moveSouth(eq(Player.class), eq(from));
		verify(toTest, never()).moveWest(eq(Player.class), eq(from));
		
		toTest.move(Player.class, from, MoveDirection.SOUTH);
		
		verify(toTest, times(1)).moveNorth(eq(Player.class), eq(from));
		verify(toTest, times(1)).moveEast(eq(Player.class), eq(from));
		verify(toTest, times(1)).moveSouth(eq(Player.class), eq(from));
		verify(toTest, never()).moveWest(eq(Player.class), eq(from));
		
		toTest.move(Player.class, from, MoveDirection.WEST);
		
		verify(toTest, times(1)).moveNorth(eq(Player.class), eq(from));
		verify(toTest, times(1)).moveEast(eq(Player.class), eq(from));
		verify(toTest, times(1)).moveSouth(eq(Player.class), eq(from));
		verify(toTest, times(1)).moveWest(eq(Player.class), eq(from));
	}
}
