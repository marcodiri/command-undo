package command;

import app.Application;
import component.Editor;

public class UndoCommand extends Command {

    public UndoCommand(Application app) {
        super(app);
    }

    @Override
    public boolean execute(Editor editor) {
        app.undo();
        return false;
    }

    @Override
    public void undo() {
        // no undo needed
    }

}