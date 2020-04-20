package command;

import component.*;

/**
 * Command design pattern.
 * Every {@code concreteCommand} incapsulates a function that can later
 * be attached to different kinds of {@code Component}s like
 * {@code Button}s and {@code Shortcut}s in this example.<p>
 * It acts as the {@code Component} of the Composite design pattern to
 * generate macro commands. {@code concreteCommand}s are the {@code Leaf}s.
 */
public abstract class Command {

    // Command has to know the receiver, but since our receiver is dynamic
    // either we do this static, or we put setActiveWindow() in the
    // CommandManager and pass the activeWindow reference in the Command constructor
    protected static Window activeWindow;

    /**
     * Execute the command. If undoable saves a {@code Memento} of the
     * modified object in the caretaker.
     */
    public abstract void execute();

    public void add(Command command) throws RuntimeException {
        throw new RuntimeException("Cannot execute add operation on a non-macro command.");
    }

    public void remove(Command command) throws RuntimeException {
        throw new RuntimeException("Cannot execute remove operation on a non-macro command.");
    }

    public static void setActiveWindow(Window win) {
        activeWindow = win;
    }

}