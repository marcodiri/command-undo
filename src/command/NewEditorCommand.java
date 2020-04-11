package command;

import app.UniqueID;

import app.Application;

public class NewEditorCommand extends Command {

    public NewEditorCommand(Application app) {
        super(app);
    }

    @Override
    public void execute() {
        app.createComponent("editor", "NewEditor-"+UniqueID.getID(), null);
    }

    @Override
    public void undo() {
        // undo code here
    }

}