package command;

public final class PasteCommand extends Command {

    @Override
    public void execute() {
        if (activeWindow.clipboard != null) {
            activeWindow.storeSnapshot(activeWindow.getActiveEditor().getSnapshot()); // save an EditorMemento and store it in the caretaker
            System.out.println("\nPasting \""+activeWindow.clipboard+"\" on "+activeWindow.getActiveEditor().getName());
            activeWindow.getActiveEditor().replaceSelection(activeWindow.clipboard);
        }
    }

}