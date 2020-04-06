package app;

import java.util.*;

import component.Button;
import component.Component;
import component.Editor;
import component.Shortcut;
import command.*;
import history.History;

public class Application {

    public Map<String, Component> components;
    public Editor activeEditor;
    private History<Command> commandsHistory;
    public String clipboard;

    public Application() {
        components = new HashMap<>();
        commandsHistory = new History<>();
        generateUI();
    }

    private void generateUI() {
        Command copyCmd = new CopyCommand(this);
        Command pasteCmd = new PasteCommand(this);
        Command cutCmd = new CutCommand(this);
        Command undoCmd = new UndoCommand(this);

        // create the buttons
        components.put("CopyButton", new Button(this, "Copy Button", copyCmd));
        components.put("PasteButton", new Button(this, "Paste Button", pasteCmd));
        components.put("CutButton", new Button(this, "Cut Button", cutCmd));
        components.put("UndoButton", new Button(this, "Undo Button", undoCmd));

        // create the shortcuts
        components.put("Ctrl+C", new Shortcut(this, "Copy", copyCmd));
        components.put("Ctrl+V", new Shortcut(this, "Paste", pasteCmd));
        components.put("Ctrl+X", new Shortcut(this, "Cut", cutCmd));
        components.put("Ctrl+Z", new Shortcut(this, "Undo", undoCmd));
    }

    /**
     * Create a new {@link Editor} and set it as the {@code activeEditor}
     * @param name the name of the new {@link Editor}
     * @return the new {@link Editor}
     */
    public Editor createEditor(String name) {
        Editor newEditor = new Editor(this, name);
        components.put(name, newEditor);
        System.out.println("Created "+name);
        setActiveEditor(newEditor);
        return newEditor;
    }

    public void executeCommand(Command command) {
        if(command.execute(activeEditor)) {
            commandsHistory.push(command);
        }
    }

    public void undo() {
        Command toUndo = commandsHistory.pop();
        if(toUndo != null) {
            toUndo.undo();
        }
    }

    /**
     * @param activeEditor the editor to focus on
     */
    public void setActiveEditor(Editor editor) {
        this.activeEditor = editor;
        System.out.println("Active editor: "+editor.getName());
    }

}