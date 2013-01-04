package de.rainu.boxmanng.world;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import de.rainu.boxmanng.exception.WorldAccessException;
import de.rainu.boxmanng.world.elements.Mark;
import de.rainu.boxmanng.world.elements.Player;
import de.rainu.boxmanng.world.elements.Wall;
import de.rainu.boxmanng.world.elements.WorldElement;

public class TestWorldLayer {

	@Test
	public void testAccess(){
		WorldLayer worldLayer = new WorldLayer(3, 5);
		WorldCoord coord = new WorldCoord(2, 4);
		
		WorldElement element = new Wall();
		worldLayer.set(coord.getX(), coord.getY(), element);
		assertSame(element, worldLayer.get(2, 4));
		assertEquals(coord, element.getPosition());
		
		element = new Mark();
		worldLayer.set(coord, element);
		assertSame(element, worldLayer.get(coord));
		assertEquals(coord, element.getPosition());
		
		assertTrue(5 == worldLayer.getHeight());
		assertTrue(3 == worldLayer.getWidth());
	}

	@Test
	public void testIllegalAccess(){
		WorldLayer worldLayer = new WorldLayer(5, 5);
		
		try{
			worldLayer.set(10, 4, new Wall());
			fail("It should be thrown an exception!");
		}catch(WorldAccessException e){}
		
		try{
			worldLayer.get(10, 4);
			fail("It should be thrown an exception!");
		}catch(WorldAccessException e){}
		
		try{
			worldLayer.set(4, 10, new Wall());
			fail("It should be thrown an exception!");
		}catch(WorldAccessException e){}
		
		try{
			worldLayer.get(4, 10);
			fail("It should be thrown an exception!");
		}catch(WorldAccessException e){}
		
		try{
			worldLayer.set(4, -10, new Wall());
			fail("It should be thrown an exception!");
		}catch(WorldAccessException e){}
		
		try{
			worldLayer.get(4, -10);
			fail("It should be thrown an exception!");
		}catch(WorldAccessException e){}
		
		try{
			worldLayer.set(-4, 4, new Wall());
			fail("It should be thrown an exception!");
		}catch(WorldAccessException e){}
		
		try{
			worldLayer.get(-4, 4);
			fail("It should be thrown an exception!");
		}catch(WorldAccessException e){}
	}
	
	@Test
	public void testClone(){
		WorldLayer worldLayer = new WorldLayer(2, 2);
		WorldElement element = new Wall();
		worldLayer.set(0, 0, element);
		
		WorldLayer dollyLayer = worldLayer.getClone();
		
		assertNotSame(worldLayer, dollyLayer);
		assertEquals(worldLayer, dollyLayer);
		
		assertTrue(dollyLayer.get(0, 0) instanceof Wall);
		assertNotSame(element, dollyLayer.get(0, 0));
		
		dollyLayer.set(0, 0, new Player(0));
		
		assertTrue(worldLayer.get(0, 0) instanceof Wall);
		assertTrue(dollyLayer.get(0, 0) instanceof Player);
	}
}
