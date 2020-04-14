package command;

import java.util.ArrayList;
import java.util.List;

import app.Application;

/**
 * Acts as the {@code Composite} of the Composite pattern
 */
public class MacroCommand extends Command {

    private String name;
    private List<Command> macro;

    MacroCommand(Application app, String name) {
        super(app);
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
        app.saveCommand(this);
    }

    @Override
    public void undo() {
        System.out.println("\nUndoing macro \""+name+"\"");
        // cannot call undo of the actual commands because they'd
        // not be removed from the Application stack
        // so call as many undos as there are commands in the macros
        for(int i=0; i<macro.size(); i++) {
            app.undo();
        }
    }

}