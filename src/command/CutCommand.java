package command;

import app.Application;

public class CutCommand extends Command {

    public CutCommand(Application app) {
        super(app);
    }

    @Override
    public void execute() {
        app.saveCommand(this);
        history.push(app.getActiveEditor().saveSnapshot());
        app.clipboard = app.getActiveEditor().getSelection();
        System.out.println("Cutting \""+app.clipboard+"\" from "+app.getActiveEditor().getName()+"\n");
        app.getActiveEditor().replaceSelection("");
    }

    @Override
    public void undo() {
        System.out.println("Undoing cutting");
        history.pop().restore();
    }

}