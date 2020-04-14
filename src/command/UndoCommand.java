package command;

import app.Application;

public class UndoCommand extends Command {

    UndoCommand(Application app) {
        super(app);
    };

    @Override
    public void execute() {
        app.undo();
    }

    @Override
    public void undo() {
        // no undo needed
    }

}