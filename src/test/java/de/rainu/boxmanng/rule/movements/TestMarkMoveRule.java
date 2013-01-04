package de.rainu.boxmanng.rule.movements;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.rainu.boxmanng.exception.RuleException;
import de.rainu.boxmanng.world.WorldCoord;
import de.rainu.boxmanng.world.elements.Box;
import de.rainu.boxmanng.world.elements.Mark;
import de.rainu.boxmanng.world.elements.Player;
import de.rainu.boxmanng.world.elements.Wall;
import de.rainu.boxmanng.world.elements.WorldElement;

@RunWith(MockitoJUnitRunner.class)
public class TestMarkMoveRule {

	@Mock
	MoveController mockedMoveController;
	
	MarkMoveRule toTest;
	
	@Before
	public void init(){
		toTest = spy(new MarkMoveRule(mockedMoveController));
	}
	
	@After
	public void clear(){
		reset(mockedMoveController);
		reset(toTest);
	}
	
	@Test
	public void check(){
		try{
			toTest.check(Mark.class);
			fail("It should be thrown an exception!");
		}catch(RuleException e){}
		
		try{
			toTest.check(Player.class);
			toTest.check(null);
			toTest.check(Wall.class);
			toTest.check(Box.class);
		}catch(RuleException e){
			e.printStackTrace();
			fail("It was thrown an exception!");
		}
	}
	
	@Test
	public void moveNorth(){
		WorldCoord coord = new WorldCoord(0, 0);
		Class<? extends WorldElement> elementType = Player.class;
				
		doNothing().when(toTest).check(any(Class.class));
		
		toTest.moveNorth(elementType, coord);
		
		verify(toTest).check(eq(elementType));
		verify(mockedMoveController).moveNorth(eq(elementType), eq(coord));
	}
	
	@Test
	public void moveEast(){
		WorldCoord coord = new WorldCoord(0, 0);
		Class<? extends WorldElement> elementType = Player.class;
				
		doNothing().when(toTest).check(any(Class.class));
		
		toTest.moveEast(elementType, coord);
		
		verify(toTest).check(eq(elementType));
		verify(mockedMoveController).moveEast(eq(elementType), eq(coord));
	}
	
	@Test
	public void moveSouth(){
		WorldCoord coord = new WorldCoord(0, 0);
		Class<? extends WorldElement> elementType = Player.class;
				
		doNothing().when(toTest).check(any(Class.class));
		
		toTest.moveSouth(elementType, coord);
		
		verify(toTest).check(eq(elementType));
		verify(mockedMoveController).moveSouth(eq(elementType), eq(coord));
	}
	
	@Test
	public void moveWest(){
		WorldCoord coord = new WorldCoord(0, 0);
		Class<? extends WorldElement> elementType = Player.class;
				
		doNothing().when(toTest).check(any(Class.class));
		
		toTest.moveWest(elementType, coord);
		
		verify(toTest).check(eq(elementType));
		verify(mockedMoveController).moveWest(eq(elementType), eq(coord));
	}
}
