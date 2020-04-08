package command;

import app.Application;

public class UndoCommand extends Command {

    public UndoCommand(Application app) {
        super(app);
    }

    @Override
    public boolean execute() {
        app.undo();
        return false;
    }

    @Override
    public void undo() {
        // no undo needed
    }

}