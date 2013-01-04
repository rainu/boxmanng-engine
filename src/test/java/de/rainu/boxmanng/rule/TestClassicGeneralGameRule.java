package de.rainu.boxmanng.rule;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.rainu.boxmanng.exception.NotValidException;
import de.rainu.boxmanng.world.BaseWorld;
import de.rainu.boxmanng.world.World;
import de.rainu.boxmanng.world.WorldCoord;
import de.rainu.boxmanng.world.elements.Box;
import de.rainu.boxmanng.world.elements.Mark;
import de.rainu.boxmanng.world.elements.Player;

public class TestClassicGeneralGameRule {

	ClassicGeneralGameRule toTest;
	
	World testWorld;
	
	@Before
	public void init(){
		toTest = spy(new ClassicGeneralGameRule());
		testWorld = new BaseWorld(2, 2);
	}
	
	@After
	public void clean(){
		reset(toTest);
	}
	
	@Test
	public void hasAtLeastOnePlayer(){
		assertFalse(toTest.hasAtLeastOnePlayer(testWorld));
		
		testWorld.add(new WorldCoord(0, 0), new Player(0));
		assertTrue(toTest.hasAtLeastOnePlayer(testWorld));
	}
	
	@Test
	public void hasAtLeastOneMark(){
		assertFalse(toTest.hasAtLeastOneMark(testWorld));
		
		testWorld.add(new WorldCoord(0, 0), new Mark());
		assertTrue(toTest.hasAtLeastOneMark(testWorld));
	}
	
	@Test
	public void hasSameNumbeOfMarkSuchAsBox(){
		assertTrue(toTest.hasSameNumbeOfMarkSuchAsBox(testWorld));
		
		testWorld.add(new WorldCoord(0, 0), new Mark());
		assertFalse(toTest.hasSameNumbeOfMarkSuchAsBox(testWorld));
		
		testWorld.add(new WorldCoord(0, 1), new Box());
		assertTrue(toTest.hasSameNumbeOfMarkSuchAsBox(testWorld));
	}
	
	@Test
	public void chekValidity(){
		try{
			doReturn(true).when(toTest).hasAtLeastOneMark(any(World.class));
			doReturn(true).when(toTest).hasSameNumbeOfMarkSuchAsBox(any(World.class));
			doReturn(true).when(toTest).hasAtLeastOnePlayer(any(World.class));
			toTest.chekValidity(testWorld);
			verify(toTest).hasAtLeastOneMark(eq(testWorld));
		}catch(NotValidException e){
			e.printStackTrace();
			fail();
		}
		reset(toTest);
		try{
			doReturn(false).when(toTest).hasAtLeastOneMark(any(World.class));
			doReturn(true).when(toTest).hasSameNumbeOfMarkSuchAsBox(any(World.class));
			doReturn(true).when(toTest).hasAtLeastOnePlayer(any(World.class));
			toTest.chekValidity(testWorld);
			fail("It should be thrown an exception!");
		}catch(NotValidException e){
			verify(toTest).hasAtLeastOneMark(eq(testWorld));
		}
		reset(toTest);
		try{
			doReturn(true).when(toTest).hasSameNumbeOfMarkSuchAsBox(any(World.class));
			doReturn(true).when(toTest).hasAtLeastOneMark(any(World.class));
			doReturn(true).when(toTest).hasAtLeastOnePlayer(any(World.class));
			toTest.chekValidity(testWorld);
			verify(toTest).hasSameNumbeOfMarkSuchAsBox(eq(testWorld));
		}catch(NotValidException e){
			e.printStackTrace();
			fail();
		}
		reset(toTest);
		try{
			doReturn(false).when(toTest).hasSameNumbeOfMarkSuchAsBox(any(World.class));
			doReturn(true).when(toTest).hasAtLeastOneMark(any(World.class));
			doReturn(true).when(toTest).hasAtLeastOnePlayer(any(World.class));
			toTest.chekValidity(testWorld);
			fail("It should be thrown an exception!");
		}catch(NotValidException e){
			verify(toTest).hasSameNumbeOfMarkSuchAsBox(eq(testWorld));
		}
		reset(toTest);		
		try{
			doReturn(true).when(toTest).hasAtLeastOnePlayer(any(World.class));
			doReturn(true).when(toTest).hasSameNumbeOfMarkSuchAsBox(any(World.class));
			doReturn(true).when(toTest).hasAtLeastOneMark(any(World.class));
			toTest.chekValidity(testWorld);
			verify(toTest).hasAtLeastOnePlayer(eq(testWorld));
		}catch(NotValidException e){
			e.printStackTrace();
			fail();
		}
		reset(toTest);
		try{
			doReturn(false).when(toTest).hasAtLeastOnePlayer(any(World.class));
			doReturn(true).when(toTest).hasSameNumbeOfMarkSuchAsBox(any(World.class));
			doReturn(true).when(toTest).hasAtLeastOneMark(any(World.class));
			toTest.chekValidity(testWorld);
			fail("It should be thrown an exception!");
		}catch(NotValidException e){
			verify(toTest).hasAtLeastOnePlayer(eq(testWorld));
		}
	}
	
	@Test
	public void isSolved(){
		testWorld.add(new WorldCoord(0, 0), new Mark());
		testWorld.add(new WorldCoord(0, 1), new Box());
		
		assertFalse(toTest.isSolved(testWorld));
		
		testWorld.move(new WorldCoord(0, 1), new WorldCoord(0, 0), Box.class);
		
		assertTrue(toTest.isSolved(testWorld));
	}
	
	@Test
	public void isGameOver(){
		assertFalse(toTest.isGameOver(testWorld));
	}
}
