package command;

import app.Application;

public class CutCommand extends Command {

    CutCommand(Application app) {
        super(app);
    };

    @Override
    public void execute() {
        app.saveCommand(this);
        history.push(app.getActiveEditor().saveSnapshot());
        app.clipboard = app.getActiveEditor().getSelection();
        System.out.println("Cutting \""+app.clipboard+"\" from "+app.getActiveEditor().getName());
        app.getActiveEditor().replaceSelection("");
    }

    @Override
    public void undo() {
        System.out.println("\nUndoing cutting");
        history.pop().restore();
    }

}