package app;

import java.util.*;

import component.Button;
import component.Component;
import component.Editor;
import component.Shortcut;
import command.*;
import history.History;

public class Application {

    private Map<Integer, Component> components;
    private Map<String, Integer> nameToId; // just a helper to call the component by name in the client
    private Editor activeEditor;
    private History<Command> commandsHistory;
    public String clipboard;

    public Application() {
        components = new HashMap<>();
        nameToId = new HashMap<>();
        commandsHistory = new History<>();
        generateUI();
    }

    private void generateUI() {
        Command copyCmd = new CopyCommand(this);
        Command pasteCmd = new PasteCommand(this);
        Command cutCmd = new CutCommand(this);
        Command undoCmd = new UndoCommand(this);
        Command newEditorCmd = new NewEditorCommand(this);

        // create the buttons
        createComponent("button", "CopyButton", copyCmd);
        createComponent("button", "PasteButton", pasteCmd);
        createComponent("button", "CutButton", cutCmd);
        createComponent("button", "UndoButton", undoCmd);
        createComponent("button", "NewEditorButton", newEditorCmd);

        // create the shortcuts
        createComponent("shortcut", "Ctrl+C", copyCmd);
        createComponent("shortcut", "Ctrl+V", pasteCmd);
        createComponent("shortcut", "Ctrl+X", cutCmd);
        createComponent("shortcut", "Ctrl+Z", undoCmd);
        createComponent("shortcut", "Ctrl+N", newEditorCmd);
    }

    /**
     * Create a new {@link Component}.<p>
     *  and set it as the {@code activeEditor}
     * @param type one of "button", "shortcut", "macro" or "editor"
     * @param name the name of the new {@link Component}
     * @param command the {@link Command} to bind to the component
     */
    public void createComponent(String type, String name, Command command) {
        Component component;
        switch(type) {
            case "shortcut":
                component = new Shortcut(name, command);
                break;
            case "button":
                component = new Button(name, command);
                break;
            case "editor":
            default:
                component = new Editor(this, name, null);
                System.out.println("Created "+name);
                components.put(component.getId(), component);
                setActiveEditor((Editor)component);
                break;
        }
        components.put(component.getId(), component);
        nameToId.put(name, component.getId());
    }

    public void click(String compName) {
        components.get(nameToId.get(compName)).click();
    }

    public Command createMacro(String shortcut) {
        Command macro = new MacroCommand(this);
        createComponent("shortcut", shortcut, macro);
        return macro;
    }

    /**
     * @return the commandsHistory
     */
    public History<Command> getCommandsHistory() {
        return commandsHistory.clone();
    }

    /**
     * save a command in the history
     * @param command the command to save
     */
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