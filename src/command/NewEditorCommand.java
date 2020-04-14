package command;

import java.util.Stack;

import app.Application;
import app.UniqueID;
import component.Editor;

public class NewEditorCommand extends Command {

    private Stack<Editor> created;

    NewEditorCommand(Application app) {
        super(app);
        created = new Stack<>();
    };

    @Override
    public void execute() {
        app.saveCommand(this);
        String name = "NewEditor-"+UniqueID.getID();
        System.out.println("Creating "+name);
        Editor newEditor = app.createEditor(name);
        created.push(newEditor); // save the created editor
        System.out.println(newEditor.toString());
    }

    @Override
    public void undo() {
        Editor toRemove = created.pop();
        System.out.println("\nRemoving "+toRemove.getName());
        app.removeEditor(toRemove);
    }

}