package command;

import app.Application;

public class CopyCommand extends Command {

    CopyCommand(Application app) {
        super(app);
    };

    @Override
    public void execute() {
        app.clipboard = app.getActiveEditor().getSelection();
        System.out.println("Copied \""+app.clipboard+"\" from "+app.getActiveEditor().getName()+"\n");
    }

    @Override
    public void undo() {
        // no undo needed
    }

}