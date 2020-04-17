package command;

public class UndoCommand extends Command {

    @Override
    public void execute() {
        System.out.println("\nUndoing last command");
        activeWindow.undo();
    }

}