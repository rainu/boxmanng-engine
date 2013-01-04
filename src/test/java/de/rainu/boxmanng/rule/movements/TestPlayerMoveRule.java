package de.rainu.boxmanng.rule.movements;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.rainu.boxmanng.exception.WorldAccessException;
import de.rainu.boxmanng.rule.movements.MoveController.MoveDirection;
import de.rainu.boxmanng.world.World;
import de.rainu.boxmanng.world.WorldCoord;
import de.rainu.boxmanng.world.elements.Box;
import de.rainu.boxmanng.world.elements.Mark;
import de.rainu.boxmanng.world.elements.Player;
import de.rainu.boxmanng.world.elements.WorldElement;

@RunWith(MockitoJUnitRunner.class)
public class TestPlayerMoveRule {

	@Mock
	World mockedWorld;
	
	@Mock
	MoveController delegate;
	
	PlayerMoveRule toTest;
	
	@Before
	public void init(){
		doReturn(mockedWorld).when(delegate).getBasedWorld();
		toTest = spy(new PlayerMoveRule(delegate));
	}
	
	@After
	public void clear(){
		reset(mockedWorld);
		reset(delegate);
		reset(toTest);
	}
	
	@Test
	public void isBoxInFrontOf_North(){
		WorldCoord from = new WorldCoord(0, 0);
		WorldCoord to = new WorldCoord(from.getX(), from.getY() - 1);
		
		isBoxInFrontOf(from, to, MoveDirection.NORTH);
	}
	
	@Test
	public void isBoxInFrontOf_East(){
		WorldCoord from = new WorldCoord(0, 0);
		WorldCoord to = new WorldCoord(from.getX() + 1, from.getY());
		
		isBoxInFrontOf(from, to, MoveDirection.EAST);
	}
	
	@Test
	public void isBoxInFrontOf_South(){
		WorldCoord from = new WorldCoord(0, 0);
		WorldCoord to = new WorldCoord(from.getX(), from.getY() + 1);
		
		isBoxInFrontOf(from, to, MoveDirection.SOUTH);
	}
	
	@Test
	public void isBoxInFrontOf_West(){
		WorldCoord from = new WorldCoord(0, 0);
		WorldCoord to = new WorldCoord(from.getX() - 1, from.getY());
		
		isBoxInFrontOf(from, to, MoveDirection.WEST);
	}	

	public void isBoxInFrontOf(final WorldCoord from, final WorldCoord to, final MoveDirection direction){
		//usecase: treffer!
		doReturn(true).when(mockedWorld).isPresent(any(WorldCoord.class), any(Class.class));
		ArgumentCaptor<WorldCoord> coordCap = ArgumentCaptor.forClass(WorldCoord.class);
		
		boolean result = toTest.isBoxInFrontOf(from, direction);
		
		assertTrue(result);
		verify(mockedWorld).isPresent(coordCap.capture(), eq(Box.class));
		assertEquals(to, coordCap.getValue());
		
		reset(mockedWorld);	//zähler zurücksetzen
		
		//usecase: kein treffer!
		doReturn(false).when(mockedWorld).isPresent(any(WorldCoord.class), any(Class.class));
		coordCap = ArgumentCaptor.forClass(WorldCoord.class);
		
		result = toTest.isBoxInFrontOf(from, direction);
		
		assertFalse(result);
		verify(mockedWorld).isPresent(coordCap.capture(), eq(Box.class));
		assertEquals(to, coordCap.getValue());
		
		reset(mockedWorld);	//zähler zurücksetzen
		
		//usecase: auserhalb der welt
		doThrow(new WorldAccessException()).when(mockedWorld).isPresent(any(WorldCoord.class), any(Class.class));
		coordCap = ArgumentCaptor.forClass(WorldCoord.class);
		
		result = toTest.isBoxInFrontOf(from, direction);
		
		assertFalse(result);
		verify(mockedWorld).isPresent(coordCap.capture(), eq(Box.class));
		assertEquals(to, coordCap.getValue());
	}
	
	@Test
	public void moveNorth(){
		WorldCoord coord = new WorldCoord(0, 0);
		
		//usecase: Kein Spieler -> nicht seine Aufgabe!
		Class<? extends WorldElement> elementType = Mark.class;
		toTest.moveNorth(elementType, coord);

		verify(delegate, never()).moveNorth(eq(Box.class), any(WorldCoord.class));
		verify(delegate).moveNorth(eq(elementType), eq(coord));
		
		reset(delegate);	//zähler zurücksetzen
		
		//usecase: Ein Spieler aber keine box vor ihm
		doReturn(false).when(toTest).isBoxInFrontOf(any(WorldCoord.class), any(MoveDirection.class));
		
		elementType = Player.class;
		toTest.moveNorth(elementType, coord);
		
		verify(delegate, never()).moveNorth(eq(Box.class), any(WorldCoord.class));
		verify(delegate).moveNorth(eq(elementType), eq(coord));
		
		reset(delegate);	//zähler zurücksetzen
		
		//usecase: Ein Spieler und eine box vor ihm
		doReturn(true).when(toTest).isBoxInFrontOf(any(WorldCoord.class), any(MoveDirection.class));
		ArgumentCaptor<WorldCoord> toCap = ArgumentCaptor.forClass(WorldCoord.class);
		
		toTest.moveNorth(elementType, coord);
		
		verify(delegate).moveNorth(eq(Box.class), toCap.capture());
		verify(delegate).moveNorth(eq(elementType), eq(coord));
		
		assertEquals(new WorldCoord(coord.getX(), coord.getY() - 1), toCap.getValue());
	}
	
	@Test
	public void moveEast(){
		WorldCoord coord = new WorldCoord(0, 0);
		
		//usecase: Kein Spieler -> nicht seine Aufgabe!
		Class<? extends WorldElement> elementType = Mark.class;
		toTest.moveEast(elementType, coord);

		verify(delegate, never()).moveEast(eq(Box.class), any(WorldCoord.class));
		verify(delegate).moveEast(eq(elementType), eq(coord));
		
		reset(delegate);	//zähler zurücksetzen
		
		//usecase: Ein Spieler aber keine box vor ihm
		doReturn(false).when(toTest).isBoxInFrontOf(any(WorldCoord.class), any(MoveDirection.class));
		
		elementType = Player.class;
		toTest.moveEast(elementType, coord);
		
		verify(delegate, never()).moveEast(eq(Box.class), any(WorldCoord.class));
		verify(delegate).moveEast(eq(elementType), eq(coord));
		
		reset(delegate);	//zähler zurücksetzen
		
		//usecase: Ein Spieler und eine box vor ihm
		doReturn(true).when(toTest).isBoxInFrontOf(any(WorldCoord.class), any(MoveDirection.class));
		ArgumentCaptor<WorldCoord> toCap = ArgumentCaptor.forClass(WorldCoord.class);
		
		toTest.moveEast(elementType, coord);
		
		verify(delegate).moveEast(eq(Box.class), toCap.capture());
		verify(delegate).moveEast(eq(elementType), eq(coord));
		
		assertEquals(new WorldCoord(coord.getX() + 1, coord.getY()), toCap.getValue());
	}
	
	@Test
	public void moveSouth(){
		WorldCoord coord = new WorldCoord(0, 0);
		
		//usecase: Kein Spieler -> nicht seine Aufgabe!
		Class<? extends WorldElement> elementType = Mark.class;
		toTest.moveSouth(elementType, coord);

		verify(delegate, never()).moveSouth(eq(Box.class), any(WorldCoord.class));
		verify(delegate).moveSouth(eq(elementType), eq(coord));
		
		reset(delegate);	//zähler zurücksetzen
		
		//usecase: Ein Spieler aber keine box vor ihm
		doReturn(false).when(toTest).isBoxInFrontOf(any(WorldCoord.class), any(MoveDirection.class));
		
		elementType = Player.class;
		toTest.moveSouth(elementType, coord);
		
		verify(delegate, never()).moveSouth(eq(Box.class), any(WorldCoord.class));
		verify(delegate).moveSouth(eq(elementType), eq(coord));
		
		reset(delegate);	//zähler zurücksetzen
		
		//usecase: Ein Spieler und eine box vor ihm
		doReturn(true).when(toTest).isBoxInFrontOf(any(WorldCoord.class), any(MoveDirection.class));
		ArgumentCaptor<WorldCoord> toCap = ArgumentCaptor.forClass(WorldCoord.class);
		
		toTest.moveSouth(elementType, coord);
		
		verify(delegate).moveSouth(eq(Box.class), toCap.capture());
		verify(delegate).moveSouth(eq(elementType), eq(coord));
		
		assertEquals(new WorldCoord(coord.getX(), coord.getY() + 1), toCap.getValue());
	}
	
	@Test
	public void moveWest(){
		WorldCoord coord = new WorldCoord(0, 0);
		
		//usecase: Kein Spieler -> nicht seine Aufgabe!
		Class<? extends WorldElement> elementType = Mark.class;
		toTest.moveWest(elementType, coord);

		verify(delegate, never()).moveWest(eq(Box.class), any(WorldCoord.class));
		verify(delegate).moveWest(eq(elementType), eq(coord));
		
		reset(delegate);	//zähler zurücksetzen
		
		//usecase: Ein Spieler aber keine box vor ihm
		doReturn(false).when(toTest).isBoxInFrontOf(any(WorldCoord.class), any(MoveDirection.class));
		
		elementType = Player.class;
		toTest.moveWest(elementType, coord);
		
		verify(delegate, never()).moveWest(eq(Box.class), any(WorldCoord.class));
		verify(delegate).moveWest(eq(elementType), eq(coord));
		
		reset(delegate);	//zähler zurücksetzen
		
		//usecase: Ein Spieler und eine box vor ihm
		doReturn(true).when(toTest).isBoxInFrontOf(any(WorldCoord.class), any(MoveDirection.class));
		ArgumentCaptor<WorldCoord> toCap = ArgumentCaptor.forClass(WorldCoord.class);
		
		toTest.moveWest(elementType, coord);
		
		verify(delegate).moveWest(eq(Box.class), toCap.capture());
		verify(delegate).moveWest(eq(elementType), eq(coord));
		
		assertEquals(new WorldCoord(coord.getX() - 1, coord.getY()), toCap.getValue());
	}
}
