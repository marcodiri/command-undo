package app;

public abstract class UniqueID {

    // non istantiable class
    private UniqueID() {};

    private static int id = 0;

    public static int generateID() {
        return id++;
    }

    /**
     * @return the first free id
     */
    public static int getID() {
        return id;
    }
    
}