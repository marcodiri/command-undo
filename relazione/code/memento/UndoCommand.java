public class UndoCommand extends Command {
    @Override
    public void execute() {
        activeWindow.undo();
    }
}