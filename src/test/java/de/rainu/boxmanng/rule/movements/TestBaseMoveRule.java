package de.rainu.boxmanng.rule.movements;

import static org.junit.Assert.assertEquals;
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
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.rainu.boxmanng.world.World;
import de.rainu.boxmanng.world.WorldCoord;
import de.rainu.boxmanng.world.elements.Player;
import de.rainu.boxmanng.world.elements.WorldElement;

@RunWith(MockitoJUnitRunner.class)
public class TestBaseMoveRule {

	@Mock
	World mockedWorld;
	
	BaseMoveRule toTest;
	
	@Before
	public void init(){
		toTest = spy(new BaseMoveRule(mockedWorld));
	}
	
	@After
	public void clear(){
		reset(mockedWorld);
		reset(toTest);
	}
	
	@Test
	public void moveNorth(){
		WorldCoord coord = new WorldCoord(0, 0);
		Class<? extends WorldElement> elementType = Player.class;
		
		ArgumentCaptor<WorldCoord> fromCaptor = ArgumentCaptor.forClass(WorldCoord.class);
		ArgumentCaptor<WorldCoord> toCaptor = ArgumentCaptor.forClass(WorldCoord.class);
		
		doNothing().when(toTest).checkPresentation(any(Class.class), any(WorldCoord.class));
		
		toTest.moveNorth(elementType, coord);
		
		verify(toTest).checkPresentation(eq(elementType), eq(coord));
		verify(mockedWorld).move(fromCaptor.capture(), toCaptor.capture(), eq(elementType));
		
		assertEquals(coord, fromCaptor.getValue());
		assertEquals(new WorldCoord(coord.getX(), coord.getY() - 1), toCaptor.getValue());
	}
	
	@Test
	public void moveEast(){
		WorldCoord coord = new WorldCoord(0, 0);
		Class<? extends WorldElement> elementType = Player.class;
		
		ArgumentCaptor<WorldCoord> fromCaptor = ArgumentCaptor.forClass(WorldCoord.class);
		ArgumentCaptor<WorldCoord> toCaptor = ArgumentCaptor.forClass(WorldCoord.class);
		
		doNothing().when(toTest).checkPresentation(any(Class.class), any(WorldCoord.class));
		
		toTest.moveEast(elementType, coord);
		
		verify(toTest).checkPresentation(eq(elementType), eq(coord));
		verify(mockedWorld).move(fromCaptor.capture(), toCaptor.capture(), eq(elementType));
		
		assertEquals(coord, fromCaptor.getValue());
		assertEquals(new WorldCoord(coord.getX() + 1, coord.getY()), toCaptor.getValue());
	}
	
	@Test
	public void moveSouth(){
		WorldCoord coord = new WorldCoord(0, 0);
		Class<? extends WorldElement> elementType = Player.class;
		
		ArgumentCaptor<WorldCoord> fromCaptor = ArgumentCaptor.forClass(WorldCoord.class);
		ArgumentCaptor<WorldCoord> toCaptor = ArgumentCaptor.forClass(WorldCoord.class);
		
		doNothing().when(toTest).checkPresentation(any(Class.class), any(WorldCoord.class));
		
		toTest.moveSouth(elementType, coord);
		
		verify(toTest).checkPresentation(eq(elementType), eq(coord));
		verify(mockedWorld).move(fromCaptor.capture(), toCaptor.capture(), eq(elementType));
		
		assertEquals(coord, fromCaptor.getValue());
		assertEquals(new WorldCoord(coord.getX(), coord.getY() + 1), toCaptor.getValue());
	}
	
	@Test
	public void moveWest(){
		WorldCoord coord = new WorldCoord(0, 0);
		Class<? extends WorldElement> elementType = Player.class;
		
		ArgumentCaptor<WorldCoord> fromCaptor = ArgumentCaptor.forClass(WorldCoord.class);
		ArgumentCaptor<WorldCoord> toCaptor = ArgumentCaptor.forClass(WorldCoord.class);
		
		doNothing().when(toTest).checkPresentation(any(Class.class), any(WorldCoord.class));
		
		toTest.moveWest(elementType, coord);
		
		verify(toTest).checkPresentation(eq(elementType), eq(coord));
		verify(mockedWorld).move(fromCaptor.capture(), toCaptor.capture(), eq(elementType));
		
		assertEquals(coord, fromCaptor.getValue());
		assertEquals(new WorldCoord(coord.getX() - 1, coord.getY()), toCaptor.getValue());
	}
}
