package component;

import app.UniqueID;
import command.Command;

public abstract class Component {

    private final int id;
    private String name;
    private Command command;

    Component(String name) {
        id = UniqueID.generateID();
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
     * @return the command
     */
    public Command getCommand() {
        return command;
    }

    /**
     * @param command the command to set
     */
    public void setCommand(Command command) {
        this.command = command;
    }

    /**
     * behaviour of the Component when clicked
     */
    public abstract void click();

    /**
     * ID will still be different.
     */
    @Override
    protected abstract Component clone() throws CloneNotSupportedException;

}