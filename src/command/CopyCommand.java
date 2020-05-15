package command;

public final class CopyCommand extends Command {

    @Override
    public void execute() {
        activeWindow.clipboard = activeWindow.getActiveEditor().getSelection();
        System.out.println("Copied \""+activeWindow.clipboard+"\" from "+activeWindow.getActiveEditor().getName()+"\n");
    }

}