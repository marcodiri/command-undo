package command;

import app.Application;

public class PasteCommand extends Command {

    PasteCommand(Application app) {
        super(app);
    };

    @Override
    public void execute() {
        if (app.clipboard != null) {
            app.saveCommand(this); // notify the app that this command has been executed
            history.push(app.getActiveEditor().saveSnapshot()); // get an EditorMemento
            System.out.println("\nPasting \""+app.clipboard+"\" on "+app.getActiveEditor().getName());
            app.getActiveEditor().replaceSelection(app.clipboard);
        }
    }

    @Override
    public void undo() {
        System.out.println("\nUndoing pasting");
        history.pop().restore();
    }

}