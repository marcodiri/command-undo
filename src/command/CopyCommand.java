package command;

import app.Application;
import component.Editor;

public class CopyCommand extends Command {

    public CopyCommand(Application app) {
        super(app);
    }

    @Override
    public boolean execute(Editor editor) {
        app.clipboard = editor.getSelection();
        System.out.println("Copied \""+app.clipboard+"\" from "+editor.getName()+"\n");
        return false;
    }

    @Override
    public void undo() {
        // no undo needed
    }

}