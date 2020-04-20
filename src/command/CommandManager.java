package command;

import java.util.HashMap;
import java.util.Map;

/**
 * Responsible of instantiating {@link Command}s and passing them on request.
 */
public class CommandManager {

    public enum Type {
        COPY,
        CUT,
        PASTE,
        NEWEDITOR,
        UNDO,
        NULL // removes
    }

    private Map<String, Command> commands;

    /**
     * Call this method to get a default command or to edit a macro created with {@link #createMacro(String)}
     * @param app the application you are working on
     * @param name one of {@link Type} or a macro identifier
     * @return a {@link Command} object
     */
    public Command get(String name) {
        if(commands == null) {
            commands = new HashMap<>();
            commands.put(Type.COPY.toString(), new CopyCommand());
            commands.put(Type.CUT.toString(), new CutCommand());
            commands.put(Type.PASTE.toString(), new PasteCommand());
            commands.put(Type.NEWEDITOR.toString(), new NewEditorCommand());
            commands.put(Type.UNDO.toString(), new UndoCommand());
            commands.put(Type.NULL.toString(), null);
        }
        // pass the reference, no need to clone commands
        return commands.get(name);
    }

    // overload to accept CommandManager.Type
    public Command get(Type name) {
        return get(name.toString());
    }


    public Command createMacro(String name) {
        commands.put(name, new MacroCommand(name));
        return commands.get(name);
    }

}