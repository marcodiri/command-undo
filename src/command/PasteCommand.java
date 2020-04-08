package command;

import app.Application;

public class PasteCommand extends Command {

    public PasteCommand(Application app) {
        super(app);
    }

    @Override
    public boolean execute() {
        if (app.clipboard != null) {
            history.push(app.getActiveEditor().saveSnapshot());
            System.out.println("\nPasting \""+app.clipboard+"\" on "+app.getActiveEditor().getName());
            app.getActiveEditor().replaceSelection(app.clipboard);
        }
        return true;
    }

    @Override
    public void undo() {
        System.out.println("Undoing pasting");
        history.pop().restore();
    }

}