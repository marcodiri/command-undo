package command;

import java.util.ArrayList;
import java.util.List;

import app.Application;

/**
 * Acts as the {@code Composite} of the Composite pattern
 */
public class MacroCommand extends Command {

    private List<Command> macro;

    public MacroCommand(Application app) {
        super(app);
        macro = new ArrayList<>();
    }

    @Override
    public void add(Command command) {
        macro.add(command);
    }

    @Override
    public void remove(Command command) {
        macro.remove(command);
    }

    @Override
    public void execute() {
        System.out.println("Executing macro");
        macro.forEach(c->{
            c.execute();
        });
    }

    @Override
    public void undo() {
        // the undo called is the one in the actual commands (leaves)
    }

}