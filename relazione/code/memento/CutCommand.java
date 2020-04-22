public class CutCommand extends Command {
    @Override
    public void execute() {
        // save an EditorMemento and store it in the caretaker
        activeWindow.storeSnapshot(activeWindow.getActiveEditor().getSnapshot());
        // modify Editor
        ...
    }
}