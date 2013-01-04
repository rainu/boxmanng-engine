package de.rainu.boxmanng.world;

import java.util.Collection;

import de.rainu.boxmanng.world.elements.WorldElement;

public interface World {

	/**
	 * Fügt einen neuen Handler hinzu. Diese werden benachrichtigt, fals sich in der
	 * Welt etwas geändert hat.
	 * 
	 * @param handler
	 */
	public void addChangeHandler(WorldChangeHandler handler);
	
	/**
	 * Setzt ein Element in die Welt. An einer Position können mehrere
	 * Elemente vorhanden sein. Jedoch nur ein Element-Typ pro Position.
	 * 
	 * @param coord Position auf das das Element gesetzt werden soll.
	 * @param element Element welches gesetzt werden soll.
	 */
	public void add(WorldCoord coord, WorldElement element);

	/**
	 * Entfernt alle Elemente auf einer Position.
	 * 
	 * @param coord Position die geleert werden soll.
	 */
	public void clear(WorldCoord coord);
	
	/**
	 * Entfernt das gegebene Element auf der gegebenen Position.
	 * 
	 * @param coord Position des Elementes
	 * @param elementType Element, welches entfernt werden soll
	 */
	public void remove(WorldCoord coord, Class<? extends WorldElement> elementType);
	
	/**
	 * Liefert alle Elemente, die auf einer Position vorhanden sind.
	 * 
	 * @param coord Position
	 * @return alle Elemente auf dieser Position.
	 */
	public Collection<WorldElement> get(WorldCoord coord);
	
	/**
	 * Liefert alle Koordinaten eines Elementtypes.
	 * 
	 * @param element Nach welchem Element soll gesucht werden.
	 * @return alle Positionen eines Element-Types
	 */
	public Collection<WorldCoord> get(Class<? extends WorldElement> elementType);
	
	/**
	 * Liefert alle Elemente eines Elementtypes.
	 * 
	 * @param element Nach welchem Element soll gesucht werden.
	 * @return alle Element-Objekte eines Element-Types
	 */
	public <T extends WorldElement> Collection<T> getElements(Class<? extends T> elementType);
	
	/**
	 * Verschiebt ein Element auf die gewünschte Position. Fals das Element
	 * nicht auf dieser Position vorhanden ist, wird die Aktion unterbrochen.
	 * 
	 * @param from von welcher Position
	 * @param to zu welcher Position
	 * @param elementType welches Element
	 */
	public void move(WorldCoord from, WorldCoord to, Class<? extends WorldElement> elementType);
	
	/**
	 * Prüft ob das angegebene Element auf der Position vorhanden ist.
	 * 
	 * @param coord Position die überprüft werden soll.
	 * @param elementType Element welches geprüft werden soll.
	 * @return <b>True</b> wenn das Element vorhanden ist, andernfals <b>false</b>.
	 */
	public boolean isPresent(WorldCoord coord, Class<? extends WorldElement> elementType);
	
	/**
	 * Prüft ob auf der angegebenen Position ein Element vorhanden ist.
	 * 
	 * @param coord Position die geprüft werden soll.
	 * @return <b>True</b> wenn kein Element vorhanden ist, andernfals <b>false</b>.
	 */
	public boolean isEmpty(WorldCoord coord);
}