package command;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {

    public enum Type {
        COPY,
        CUT,
        PASTE,
        NEWEDITOR,
        UNDO
    }

    private Map<String, Command> commands;

    /**
     * Call this method to get a default command or to edit a macro created with {@link #createMacro}
     * @param app the application you are working on
     * @param name one of {@link Type} or a macro identifier
     * @return a {@link Command} object
     */
    public Command get(Type name) {
        if(commands == null) {
            commands = new HashMap<>();
            commands.put(Type.COPY.toString(), new CopyCommand());
            commands.put(Type.CUT.toString(), new CutCommand());
            commands.put(Type.PASTE.toString(), new PasteCommand());
            commands.put(Type.NEWEDITOR.toString(), new NewEditorCommand());
            commands.put(Type.UNDO.toString(), new UndoCommand());
        }
        // pass the reference, no need to clone commands
        return commands.get(name.toString());
    }

    // overload to get created macros
    /**
     * @param name the macro name specified in {@link #createMacro(String)}
     * @return the macro {@code Command}
     */
    public Command get(String name) {
        return commands.get(name);
    }

    public Command createMacro(String name) {
        commands.put(name, new MacroCommand(name));
        return commands.get(name);
    }

}