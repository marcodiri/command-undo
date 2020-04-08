package command;

import app.Application;
import component.Editor.EditorMemento;
import history.History;

/**
 * Command design pattern.
 * Every {@code concreteCommand} incapsulates a function that can later
 * be attached to different kinds of {@code Component}s like
 * {@code Button}s and {@code Shortcut}s in this example.
 */
public abstract class Command {

    protected Application app;

    // probably better to put the history in the Editor directly, done like this
    // for the sake of demonstrating the Memento pattern with an external caretaker
    protected History<EditorMemento> history;

    protected Command(Application app) {
        this.app = app;
        history = new History<>();
    }

    /**
     * Execute the command on an editor. If undoable save an {@link EditorMemento} in {@link Command.history}
     * @return {@code true} if the command is undoable, {@code false} otherwise
     */
    public abstract boolean execute();

    public abstract void undo();

}