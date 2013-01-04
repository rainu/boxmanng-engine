package de.rainu.boxmanng.world;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.rainu.boxmanng.exception.WorldBuildException;
import de.rainu.boxmanng.world.elements.WorldElement;

/**
 * Dieses Klasse representiert eine Basis-Welt. Sie hat keinerlei
 * speziale Logik. 
 */
public class BaseWorld implements World {
	private List<WorldChangeHandler> allListeners;
	private Map<Class<? extends WorldElement>, WorldLayer> layer = new HashMap<Class<? extends WorldElement>, WorldLayer>();
	private final int width;
	private final int height;
	
	public BaseWorld(int width, int height){
		if(width <= 0) throw new WorldBuildException("The world width must greater than zero!");
		if(height <= 0) throw new WorldBuildException("The world height must greater than zero!");
		
		this.width = width;
		this.height = height;
		this.allListeners = new ArrayList<WorldChangeHandler>();
	}
	
	@Override
	public void addChangeHandler(WorldChangeHandler handler) {
		if(!allListeners.contains(handler)){
			//nur neue handler
			allListeners.add(handler);
		}
	}
	
	public void add(WorldCoord coord, WorldElement element){
		if(element == null) return;	//skip space
		
		createNewLayerIfIsNecessary(element.getClass());
		WorldLayer corLayer = getCorrespondingLayer(element.getClass());
		
		corLayer.set(coord, element);
		fireAddEvent(coord, element);
	}
	
	private void createNewLayerIfIsNecessary(Class<? extends WorldElement> elementType){
		if(!layer.containsKey(elementType)){
			layer.put(elementType, new WorldLayer(width, height));
		}
	}
	
	private WorldLayer getCorrespondingLayer(Class<? extends WorldElement> elementType){
		return layer.get(elementType);
	}

	public void clear(WorldCoord coord){
		for(Class<? extends WorldElement> curLayer : layer.keySet()){
			remove(coord, curLayer);
		}
	}
	
	public void remove(WorldCoord coord, Class<? extends WorldElement> elementType){
		WorldLayer corLayer = getCorrespondingLayer(elementType);
		if(corLayer == null) return;
		
		corLayer.set(coord, null);

		fireRemoveEvent(coord, elementType);
	}
	
	public Collection<WorldElement> get(WorldCoord coord){
		List<WorldElement> result = new ArrayList<WorldElement>();
		
		for(WorldLayer curLayer : layer.values()){
			WorldElement element = curLayer.get(coord);
			if(element != null){
				result.add(element);
			}
		}
		
		return result;
	}
	
	@Override
	public Collection<WorldCoord> get(Class<? extends WorldElement> elementType) {
		if(elementType == null){
			return getEmpties();
		}else{
			WorldLayer layer = getCorrespondingLayer(elementType);
			if(layer == null) return Collections.EMPTY_LIST;
			
			return get(layer, elementType);
		}
	}
	
	@Override
	public <T extends WorldElement> Collection<T> getElements(Class<? extends T> elementType) {
		if(elementType == null){
			return null;
		}
		
		WorldLayer layer = getCorrespondingLayer(elementType);
		if(layer == null) return Collections.EMPTY_LIST;
		
		Collection<T> elements = new ArrayList<T>();
		for(int x=0; x < width; x++){
			for(int y=0; y < height; y++){
				WorldElement element = layer.get(x, y);
				if(element != null){
					elements.add((T)element);
				}
			}
		}
		
		return elements;
	}
	
	protected Collection<WorldCoord> get(WorldLayer layer, Class<? extends WorldElement> elementType){
		Collection<WorldCoord> positions = new ArrayList<WorldCoord>();
		for(int x=0; x < width; x++){
			for(int y=0; y < height; y++){
				WorldElement curElement = layer.get(x, y);
				if(curElement != null && elementType == curElement.getClass()){
					positions.add(new WorldCoord(x, y));
				}
			}
		}
		
		return positions;
	}

	protected Collection<WorldCoord> getEmpties(){
		Collection<WorldCoord> positions = new ArrayList<WorldCoord>();
		for(int x=0; x < width; x++){
			for(int y=0; y < height; y++){
				WorldCoord coord = new WorldCoord(x, y);
				
				if(isEmpty(coord)){
					positions.add(coord);
				}
			}
		}
		
		return positions;
	}
	
	public void move(WorldCoord from, WorldCoord to, Class<? extends WorldElement> elementType){
		if(!isPresent(from, elementType)) return;
		
		WorldElement tmp = getCorrespondingLayer(elementType).get(from);
		remove(from, elementType);
		add(to, tmp);
	}
	
	public boolean isPresent(WorldCoord coord, Class<? extends WorldElement> elementType){
		WorldLayer corLayer = getCorrespondingLayer(elementType);
		if(corLayer == null) return false;

		if(elementType == null) return corLayer.get(coord) == null;
		if(corLayer.get(coord) == null) return false;
		else return corLayer.get(coord).getClass() == elementType;
	}
	
	public boolean isEmpty(WorldCoord coord) {
		return get(coord).isEmpty();
	}
	
	protected void fireRemoveEvent(WorldCoord coord, Class<? extends WorldElement> elementType){
		if(allListeners != null) for(WorldChangeHandler handler : allListeners){
			handler.handleOnElementRemove(coord, elementType);
		}
	}
	
	protected void fireAddEvent(WorldCoord coord, WorldElement element){
		if(allListeners != null) for(WorldChangeHandler handler : allListeners){
			handler.handleOnElementAdd(coord, element);
		}
	}
}
