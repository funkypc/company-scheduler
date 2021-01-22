package model;

/**
 * Abstract class for Item. 
 * @author Charles Plett
 */
public abstract class Item {
    
    /**
     * Getter for returning the Name of the item. 
     * @return Returns the Name as String. 
     */
    public abstract String getName();

    /**
     * Getter for returning the id of the item. 
     * @return Returns the id of the item as int. 
     */
    public abstract int getId();
}
