package command;

import java.util.ArrayList;
import java.util.List;

/**
 * Acts as the {@code Composite} of the Composite pattern
 */
public class MacroCommand extends Command {

    private String name;
    private List<Command> macro;

    MacroCommand(String name) {
        this.name = name;
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
        System.out.println("\nExecuting macro \""+name+"\"");
        macro.forEach(c->{
            c.execute();
        });
    }

}