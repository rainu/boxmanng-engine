package de.rainu.boxmanng.world;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.rainu.boxmanng.exception.WorldAccessException;
import de.rainu.boxmanng.world.elements.Box;
import de.rainu.boxmanng.world.elements.Player;
import de.rainu.boxmanng.world.elements.WorldElement;

@RunWith(MockitoJUnitRunner.class)
public class TestWriteprotectedWorld {

	@Mock
	private World mockToProtect;
	
	private World toTest;

	@Before
	public void init(){
		toTest = new WriteProtectedWorld(mockToProtect);
	}
	
	@After
	public void clean(){
		reset(mockToProtect);
	}
	
	@Test
	public void add(){
		try{
			toTest.add(new WorldCoord(0, 0), new Box());
			fail("It should be thrown an exception!");
		}catch(WorldAccessException e){
			verify(mockToProtect, never()).add(any(WorldCoord.class), any(WorldElement.class));
		}
	}
	
	@Test
	public void addChangeHandler(){
		try{
			toTest.addChangeHandler(mock(WorldChangeHandler.class));
			fail("It should be thrown an exception!");
		}catch(WorldAccessException e){
			verify(mockToProtect, never()).addChangeHandler(any(WorldChangeHandler.class));
		}
	}

	@Test
	public void clear(){
		try{
			toTest.clear(new WorldCoord(0, 0));
			fail("It should be thrown an exception!");
		}catch(WorldAccessException e){
			verify(mockToProtect, never()).clear(any(WorldCoord.class));
		}
	}
	
	@Test
	public void remove(){
		try{
			toTest.remove(new WorldCoord(0, 0), Box.class);
			fail("It should be thrown an exception!");
		}catch(WorldAccessException e){
			verify(mockToProtect, never()).remove(any(WorldCoord.class), any(Class.class));
		}
	}
	
	@Test
	public void move(){
		try{
			toTest.move(new WorldCoord(0, 0), new WorldCoord(1, 1), Box.class);
			fail("It should be thrown an exception!");
		}catch(WorldAccessException e){
			verify(mockToProtect, never()).move(any(WorldCoord.class), any(WorldCoord.class), any(Class.class));
		}
	}
	
	@Test
	public void get(){
		try{
			WorldCoord coord = new WorldCoord(0, 0);
			
			toTest.get(coord);
			verify(mockToProtect).get(eq(coord));
		}catch(WorldAccessException e){
			e.printStackTrace();
			fail();
		}
		
		try{
			Class<? extends WorldElement> elementType = Box.class;
			
			toTest.get(elementType);
			verify(mockToProtect).get(eq(elementType));
		}catch(WorldAccessException e){
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void isPresent(){
		try{
			WorldCoord coord = new WorldCoord(0, 0);
			Class<? extends WorldElement> elementType = Player.class;
			
			toTest.isPresent(coord, elementType);
			verify(mockToProtect).isPresent(eq(coord), eq(elementType));
		}catch(WorldAccessException e){
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void isEmpty(){
		try{
			WorldCoord coord = new WorldCoord(0, 0);
			
			toTest.isEmpty(coord);
			verify(mockToProtect).isEmpty(eq(coord));
		}catch(WorldAccessException e){
			e.printStackTrace();
			fail();
		}
	}
}
