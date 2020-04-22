public abstract class Component implements Cloneable {
    ...
    private Command command;
    ...
    public Command getCommand() {
        return command;
    }
    
    public void setCommand(Command command) {
        this.command = command;
    }
    
    public abstract void click();
}