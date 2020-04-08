package command;

import app.Application;

public class CutCommand extends Command {

    public CutCommand(Application app) {
        super(app);
    }

    @Override
    public boolean execute() {
        app.clipboard = app.getActiveEditor().getSelection();
        history.push(app.getActiveEditor().saveSnapshot());
        System.out.println("Cutting \""+app.clipboard+"\" from "+app.getActiveEditor().getName()+"\n");
        app.getActiveEditor().replaceSelection("");
        return true;
    }

    @Override
    public void undo() {
        System.out.println("Undoing cutting");
        history.pop().restore();
    }

}