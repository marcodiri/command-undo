package command;

import app.Application;
import component.Editor;

public class PasteCommand extends Command {

    public PasteCommand(Application app) {
        super(app);
    }

    @Override
    public boolean execute(Editor editor) {
        if (app.clipboard != null) {
            history.push(editor.saveSnapshot());
            System.out.println("\nPasting \""+app.clipboard+"\" on "+editor.getName());
            editor.replaceSelection(app.clipboard);
        }
        return true;
    }

    @Override
    public void undo() {
        System.out.println("Undoing pasting");
        history.pop().restore();
    }

}