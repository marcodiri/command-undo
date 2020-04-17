package command;

public class CutCommand extends Command {

    @Override
    public void execute() {
        activeWindow.storeSnapshot(activeWindow.getActiveEditor().getSnapshot()); // save an EditorMemento and store it in the caretaker
        activeWindow.clipboard = activeWindow.getActiveEditor().getSelection();
        System.out.println("Cutting \""+activeWindow.clipboard+"\" from "+activeWindow.getActiveEditor().getName());
        activeWindow.getActiveEditor().replaceSelection("");
    }

}