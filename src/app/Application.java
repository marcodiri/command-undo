package app;

import component.*;
import command.*;
import history.History;

/**
 * Singleton.
 */
public class Application {

    private ComponentManager compMng;
    private CommandManager commMng;
    private Editor activeEditor;
    private History<Command> commandsHistory;
    public String clipboard;

    public Application() {
        compMng = new ComponentManager(this);
        commMng = new CommandManager(this);
        commandsHistory = new History<>();
        generateUI();
    }

    private void generateUI() {
        Command copyCmd = commMng.get(CommandManager.Type.COPY);
        Command pasteCmd = commMng.get(CommandManager.Type.PASTE);
        Command cutCmd = commMng.get(CommandManager.Type.CUT);
        Command undoCmd = commMng.get(CommandManager.Type.UNDO);
        Command newEditorCmd = commMng.get(CommandManager.Type.NEWEDITOR);

        // create the buttons
        compMng.create(ComponentManager.Type.BUTTON, "CopyButton", copyCmd);
        compMng.create(ComponentManager.Type.BUTTON, "PasteButton", pasteCmd);
        compMng.create(ComponentManager.Type.BUTTON, "CutButton", cutCmd);
        compMng.create(ComponentManager.Type.BUTTON, "UndoButton", undoCmd);
        compMng.create(ComponentManager.Type.BUTTON, "NewEditorButton", newEditorCmd);

        // create the shortcuts
        compMng.create(ComponentManager.Type.SHORTCUT, "Ctrl+C", copyCmd);
        compMng.create(ComponentManager.Type.SHORTCUT, "Ctrl+V", pasteCmd);
        compMng.create(ComponentManager.Type.SHORTCUT, "Ctrl+X", cutCmd);
        compMng.create(ComponentManager.Type.SHORTCUT, "Ctrl+Z", undoCmd);
        compMng.create(ComponentManager.Type.SHORTCUT, "Ctrl+N", newEditorCmd);
    }

    public void click(String compName) {
        compMng.get(compName).click();
    }

    /**
     * Create an 
     * @param name
     * @return
     */
    public Editor createEditor(String name) {
        return (Editor)compMng.create(ComponentManager.Type.EDITOR, name, null);
    }

    public void removeEditor(Editor editor) {
        compMng.remove(editor);
    }

    /**
     * Get a default command or a macro created with {@link #createMacro}.
     * @param name one of {@link CommandManager.Type} or a macro identifier
     * @return the requested {@link Command}
     */
    public Command getCommand(CommandManager.Type name) {
        return commMng.get(name);
    }

    /**
     * 
     * @param name the macro identifier
     * @param shortcut the shortcut to invoke the macro
     * @return a {@code Command} object on which you can call {@code add}
     * method to edit the macro
     */
    public Command createMacro(String name, String shortcut) {
        Command macro = commMng.createMacro(name);
        compMng.create(ComponentManager.Type.SHORTCUT, shortcut, macro);
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