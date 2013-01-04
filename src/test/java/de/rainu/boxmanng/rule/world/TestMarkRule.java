package de.rainu.boxmanng.rule.world;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
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
import de.rainu.boxmanng.world.World;
import de.rainu.boxmanng.world.WorldCoord;
import de.rainu.boxmanng.world.elements.Mark;
import de.rainu.boxmanng.world.elements.Wall;

@RunWith(MockitoJUnitRunner.class)
public class TestMarkRule {

	@Mock
	World mockedWorld;
	
	MarkRule toTest;
	
	@Before
	public void init(){
		toTest = spy(new MarkRule(mockedWorld));
	}
	
	@After
	public void clear(){
		reset(mockedWorld);
		reset(toTest);
	}
	
	@Test
	public void checkCoord(){
		WorldCoord coord = new WorldCoord(0, 0);
		
		doReturn(true).when(mockedWorld).isPresent(any(WorldCoord.class), eq(Wall.class));
		try{
			toTest.checkCoord(coord);
			fail("It should be thrown an exception.");
		}catch(RuleException e){}
		
		doReturn(false).when(mockedWorld).isPresent(any(WorldCoord.class), any(Class.class));
		try{
			toTest.checkCoord(coord);
		}catch(RuleException e){
			e.printStackTrace();
			fail("It should not be thrown an exception.");
		}
	}
	
	@Test
	public void add(){
		WorldCoord coord = new WorldCoord(0, 0);
		doNothing().when(toTest).checkCoord(any(WorldCoord.class));
		
		toTest.add(coord, new Wall());
		verify(toTest, never()).checkCoord(same(coord));
		
		toTest.add(coord, new Mark());
		verify(toTest).checkCoord(same(coord));
	}
	
	@Test
	public void move(){
		WorldCoord coord = new WorldCoord(0, 0);
		WorldCoord to = new WorldCoord(1, 1);
		doNothing().when(toTest).checkCoord(any(WorldCoord.class));
		
		toTest.move(coord, to, Wall.class);
		verify(toTest, never()).checkCoord(same(coord));
		
		toTest.move(coord, to, Mark.class);
		verify(toTest).checkCoord(same(to));

		doReturn(true).when(mockedWorld).isPresent(any(WorldCoord.class), eq(Mark.class));
		try{
			toTest.move(coord, to, Mark.class);
			fail("It should be thrown an exception.");
		}catch(RuleException e){}
	}
}
