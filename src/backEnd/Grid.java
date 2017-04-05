package backEnd;


/**
 * @author christianmartindale
 */


import javafx.scene.Group;
import java.util.*;
import backEnd.GameEngine.Component;
public class Grid {

    private Map<Location, Component> cellMap;

    public Grid(){
        this.cellMap = new HashMap<Location, Component>();
    }

    public Grid(Map<Location, Component> cellMap){
        this.setCellMap(cellMap);
    }

    public Grid(int row, int col, List<Component> cellList, TileShape cellShape){

        this();

        Iterator<Component> componentIterator = cellList.iterator();

        for(int xRow = 0; xRow < row; xRow ++){
            for(int yCol = 0; yCol < col; yCol ++){
                if(componentIterator.hasNext()){
                    this.cellMap.put(new Location(xRow, yCol), componentIterator.next());
                }
            }
        }

        /*
        this.setCellShape(cellShape);
         */
    }

    public Map<Location, Component> getCellMap(){
        return this.cellMap;
    }

    public void setCellMap(Map<Location, Component> cellMap){
        this.cellMap = cellMap;
    }

    /*
    public void setCellShape(TileShape cellShape){

        for(Component xCell : this.cellMap.values()){
            xCell.setCellShape(cellShape);
        }

    }
	*/
    
    public Component getCell(Location cellLocation){

        try{
            return this.cellMap.get(cellLocation);
        }
        catch(Exception E){
            return null;
        }

    }

    public void setCell(Location cellLocation, Component newComponent){
        this.cellMap.put(cellLocation, newComponent);
    }

    public boolean contains(Location cellLocation){
        return cellMap.containsKey(cellLocation);
    }

    public List<Component> getCellsFromLocations(List<Location> locationList){

        List<Component> cellList = new ArrayList<Component>();

        for(Location cellLocation : locationList){

            if(this.contains(cellLocation)){
                cellList.add(this.cellMap.get(cellLocation));
            }

        }

        cellList.removeAll(Collections.singleton(null)); // ensure list does not contain any extraneous null nodes

        return cellList;

    }

    /**
     * Retrieves the neighboring cells at a given location
     * @param cellLocation location of cell being checked
     * @return list of cells surrounding the location being checked
     */
    public List<Component> getNeighborCellList(Location cellLocation) {
        return new ArrayList<>(this.getNeighborCellMap(cellLocation).values());
    }

    public Map<Location, Component> getNeighborCellMap(Location cellLocation){

        Map<Location, Component> neighborCells = new HashMap<Location, Component>();

        if(this.contains(cellLocation)){

//            for(Location xLocation : this.getCell(cellLocation).getCellShape().getNeighborLocations(cellLocation)){
//
//                if(this.contains(xLocation)){
//                    neighborCells.put(xLocation, this.getCell(xLocation));
//                }
//
//            }

        }

        return neighborCells;

    }

    /*
    public Map<Location, Component> getCellsOfType(State cellType){

        Map<Location, Component> cellMapOfType = new HashMap<Location, Component>();

        for(Location xLocation : this.cellMap.keySet()){
            if(cellMap.get(xLocation).isCurrentEqual(cellType)){
                cellMapOfType.put(xLocation, this.cellMap.get(xLocation));
            }
        }

        return cellMapOfType;

    }
	*/
    public Set<Location> getGridLocations(){
        return this.cellMap.keySet();
    }

    public void renderCells() {

//        for(Location xLocation : this.getGridLocations()){
//            Component xCell = this.getComponent(xLocation);
//            xCell.setLocation(xLocation);
//        }

    }

    /*
    public void setComponentScale(double xFactor) {

        for(Component xCell : this.cellMap.values()){
            xCell.setScale(xFactor);
        }

        this.renderCells();

    }
    */
}