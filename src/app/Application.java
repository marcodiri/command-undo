package app;

import java.util.*;

import component.Button;
import component.Component;
import component.Editor;
import component.Shortcut;
import command.*;
import history.History;

public class Application {

    private Map<String, Component> controls;
    private Map<String, Editor> editors;
    private Editor activeEditor;
    private History<Command> commandsHistory;
    public String clipboard;

    public Application() {
        controls = new HashMap<String, Component>();
        editors = new HashMap<>();
        commandsHistory = new History<>();
        generateUI();
    }

    private void generateUI() {
        Command copyCmd = new CopyCommand(this);
        Command pasteCmd = new PasteCommand(this);
        Command cutCmd = new CutCommand(this);
        Command undoCmd = new UndoCommand(this);

        // create the buttons
        controls.put("CopyButton", new Button("Copy Button", copyCmd));
        controls.put("PasteButton", new Button("Paste Button", pasteCmd));
        controls.put("CutButton", new Button("Cut Button", cutCmd));
        controls.put("UndoButton", new Button("Undo Button", undoCmd));

        // create the shortcuts
        controls.put("Ctrl+C", new Shortcut("Copy", copyCmd));
        controls.put("Ctrl+V", new Shortcut("Paste", pasteCmd));
        controls.put("Ctrl+X", new Shortcut("Cut", cutCmd));
        controls.put("Ctrl+Z", new Shortcut("Undo", undoCmd));
    }

    /**
     * Create a new {@link Editor} and set it as the {@code activeEditor}
     * @param name the name of the new {@link Editor}
     * @return the new {@link Editor}
     */
    public Editor createEditor(String name) {
        Editor newEditor = new Editor(this, name, null);
        editors.put(name, newEditor);
        System.out.println("Created "+name);
        setActiveEditor(newEditor);
        return newEditor;
    }

    public void saveCommand(Command command) {
        commandsHistory.push(command);
    }

    public void undo() {
        Command toUndo = commandsHistory.pop();
        if(toUndo != null) {
            toUndo.undo();
        }
    }

    /**
     * @return the control going by the {@code name}
     */
    public Component getControl(String name) {
        return controls.get(name);
    }

    /**
     * @return the editor going by the {@code name}
     */
    public Editor getEditor(String name) {
        return editors.get(name);
    }

    /**
     * @return the activeEditor
     */
    public Editor getActiveEditor() {
        return activeEditor;
    }

    /**
     * @param activeEditor the editor to focus on
     */
    public void setActiveEditor(Editor editor) {
        this.activeEditor = editor;
        System.out.println("Active editor: "+editor.getName());
    }

}