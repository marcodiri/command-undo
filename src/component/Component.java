package component;

import app.Application;
import app.UniqueID;

public abstract class Component {

    private final int id;
    private String name;
    protected Application app;

    Component(Application app, String name) {
        id = UniqueID.generateID();
        this.app = app;
        this.name = name;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * behaviour of the Component when clicked
     */
    public abstract void click();

}