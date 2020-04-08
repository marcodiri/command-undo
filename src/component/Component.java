package component;

import app.UniqueID;
import command.Command;

public abstract class Component {

    private final int id;
    private String name;
    private Command command;

    Component(String name, Command command) {
        id = UniqueID.generateID();
        this.name = name;
        this.command = command;
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
    public void click() {
        command.execute();
    }

}