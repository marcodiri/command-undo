package command;

import app.Application;
import component.Editor;

public class CutCommand extends Command {

    public CutCommand(Application app) {
        super(app);
    }

    @Override
    public boolean execute(Editor editor) {
        app.clipboard = editor.getSelection();
        history.push(editor.saveSnapshot());
        System.out.println("Cutting \""+app.clipboard+"\" from "+editor.getName()+"\n");
        editor.replaceSelection("");
        return true;
    }

    @Override
    public void undo() {
        System.out.println("Undoing cutting");
        history.pop().restore();
    }

}