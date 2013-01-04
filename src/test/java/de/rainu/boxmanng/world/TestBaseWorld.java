package de.rainu.boxmanng.world;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.rainu.boxmanng.exception.WorldBuildException;
import de.rainu.boxmanng.world.elements.Mark;
import de.rainu.boxmanng.world.elements.Player;
import de.rainu.boxmanng.world.elements.Wall;
import de.rainu.boxmanng.world.elements.WorldElement;

@RunWith(MockitoJUnitRunner.class)
public class TestBaseWorld {

	@Mock
	WorldChangeHandler mockedHandler;
	
	private BaseWorld toTest;
	
	@Before
	public void init(){
		toTest = spy(new BaseWorld(2, 2));
		toTest.addChangeHandler(mockedHandler);
	}
	
	@After
	public void clean(){
		reset(toTest);
		reset(mockedHandler);
	}
	
	@Test
	public void testCreation(){
		try{
			new BaseWorld(0, 2);
			fail("It should be thrown an exception!");
		}catch(WorldBuildException e){}
		
		try{
			new BaseWorld(2, 0);
			fail("It should be thrown an exception!");
		}catch(WorldBuildException e){}
	}
	
	@Test
	public void testAddAndGet(){
		WorldCoord coord = new WorldCoord(0, 0);
		WorldElement[] elements = new WorldElement[]{
			new Wall(), new Mark(), new Player(0)
		};
		
		for(WorldElement element : elements) toTest.add(coord, element);
		for(WorldElement element : elements) assertEquals(coord, element.getPosition());
		
		Collection<WorldElement> result = toTest.get(coord);
		
		for(WorldElement element : elements) assertTrue(result.contains(element));
		assertFalse(result.contains(null));
		
		for(WorldElement element : elements) verify(mockedHandler).handleOnElementAdd(eq(coord), eq(element));
	}
	
	@Test
	public void testIsPresent(){
		WorldCoord coord = new WorldCoord(0, 0);
		
		toTest.add(coord, new Wall());
		toTest.add(coord, new Mark());
		
		assertTrue(toTest.isPresent(coord, Wall.class));
		assertFalse(toTest.isPresent(coord, Player.class));
		assertFalse(toTest.isPresent(new WorldCoord(1, 1), Mark.class));
	}
	
	@Test
	public void testIsEmpty(){
		WorldCoord coord = new WorldCoord(0, 0);
		
		assertTrue(toTest.isEmpty(coord));
	}
	
	@Test
	public void testRemove(){
		WorldCoord coord = new WorldCoord(0, 0);
		WorldElement element = new Wall();
		
		toTest.add(coord, element);
		
		assertTrue(toTest.isPresent(coord, Wall.class));

		toTest.remove(coord, Wall.class);
		assertFalse(toTest.isPresent(coord, Wall.class));
		assertTrue(toTest.isEmpty(coord));
		
		verify(mockedHandler).handleOnElementAdd(eq(coord), eq(element));
		verify(mockedHandler).handleOnElementRemove(eq(coord), eq(Wall.class));
	}
	
	@Test
	public void testClear(){
		WorldCoord coord = new WorldCoord(0, 0);
		
		WorldElement[] elements = new WorldElement[]{
			new Wall(), new Mark(), new Player(0)
		};
			
		for(WorldElement element : elements) toTest.add(coord, element);
		for(WorldElement element : elements) assertTrue(toTest.isPresent(coord, element.getClass()));

		toTest.clear(coord);
		assertTrue(toTest.isEmpty(coord));
		
		for(WorldElement element : elements) verify(mockedHandler).handleOnElementAdd(eq(coord), eq(element));
		for(WorldElement element : elements) verify(mockedHandler).handleOnElementRemove(eq(coord), eq(element.getClass()));
	}
	
	@Test
	public void testMove(){
		WorldCoord coord = new WorldCoord(0, 0);
		WorldCoord newCoord = new WorldCoord(1, 1);
		
		WorldElement[] elements = new WorldElement[]{
			new Wall(), new Mark(), new Player(0)
		};
		
		for(WorldElement element : elements) toTest.add(coord, element);
		
		toTest.move(coord, newCoord, Mark.class);

		assertTrue(toTest.isPresent(coord, Wall.class));
		assertFalse(toTest.isPresent(coord, Mark.class));
		assertTrue(toTest.isPresent(coord, Player.class));
		assertTrue(toTest.isPresent(newCoord, Mark.class));
		
		for(WorldElement element : elements) verify(mockedHandler).handleOnElementAdd(eq(coord), eq(element));
		
		verify(mockedHandler).handleOnElementRemove(eq(coord), eq(Mark.class));
		verify(mockedHandler).handleOnElementAdd(eq(newCoord), eq(elements[1]));
	}
	
	@Test
	public void getEmpties(){
		WorldCoord[] coords = new WorldCoord[]{
			new WorldCoord(0, 0),
			new WorldCoord(1, 0),
			new WorldCoord(0, 1)
		};
		
		for(WorldCoord coord : coords){
			toTest.add(coord, new Wall());
		}
		
		Collection<WorldCoord> results = toTest.getEmpties();
		assertTrue(results.size() == 1);
		assertTrue(results.contains(new WorldCoord(1, 1)));
	}
	
	@Test
	public void getElements(){
		WorldCoord[] coords = new WorldCoord[]{
			new WorldCoord(0, 0),
			new WorldCoord(0, 1)
		};
		
		for(WorldCoord coord : coords){
			toTest.add(coord, new Wall());
		}
		
		Collection<WorldCoord> results = toTest.get(Wall.class);
		assertTrue(results.size() == coords.length);
		assertTrue(results.contains(new WorldCoord(0, 0)));
		assertTrue(results.contains(new WorldCoord(0, 1)));
	}
	
	@Test
	public void get(){
		doReturn(null).when(toTest).get(any(WorldLayer.class), any(Class.class));
		doReturn(null).when(toTest).getEmpties();
		
		//es gibt noch keine schicht mit den spielern -> leere liste
		Collection<WorldCoord> results = toTest.get(Player.class);
		assertTrue(results.isEmpty());
		
		toTest.add(new WorldCoord(0, 0), new Player(0));
		
		results = toTest.get((Class<? extends WorldElement>)null);
		verify(toTest).getEmpties();
		
		results = toTest.get(Player.class);
		verify(toTest).get(any(WorldLayer.class), eq(Player.class));
	}
	
	@Test
	public void getElementsByType(){
		WorldElement[] elements = new WorldElement[]{
			new Wall(), new Wall(), new Wall()
		};
		toTest.add(new WorldCoord(0, 0), elements[0]);
		toTest.add(new WorldCoord(0, 1), elements[1]);
		toTest.add(new WorldCoord(1, 0), elements[2]);
		toTest.add(new WorldCoord(1, 1), new Mark());
		
		Collection<Wall> result = toTest.getElements(Wall.class);
		
		assertTrue(result.size() == elements.length);
		for(WorldElement curElement : elements){
			assertTrue(result.contains(curElement));
		}
	}
}
