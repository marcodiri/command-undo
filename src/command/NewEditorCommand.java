package command;

import app.UniqueID;

public final class NewEditorCommand extends Command {

    @Override
    public void execute() {
        String name = "NewEditor-"+UniqueID.getID();
        System.out.println("Creating "+name);
        activeWindow.createEditor(name);
    }

}