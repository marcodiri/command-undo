package component;

import command.*;
import history.History;

/**
 * Singleton.
 */
public class Window extends Component {

    private ComponentManager compMng;
    private CommandManager commMng;
    public String clipboard;
    private Editor activeEditor;

    // Window is the external caretaker of the Mementos' history
    protected History<Memento> history;

    public Window(String name, CommandManager commMng) {
        super(name);
        compMng = new ComponentManager(this);
        this.commMng = commMng;
        history = new History<>();
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

    @Override
    public Window clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Window not clonable");
    }

    @Override
    public void click() {
        return;
    }

    public void click(String compName) {
        compMng.get(compName).click();
    }

    /**
     * Create a new {@link Editor} and set it as the activeEditor
     * @param name the new {@code Editor} name
     * @return the new {@code Editor}
     */
    public void createEditor(String name) {
        Editor newEditor = (Editor)compMng.create(ComponentManager.Type.EDITOR, name, null);
        setActiveEditor(newEditor);
        System.out.println(newEditor.toString());
    }

    // IMPORTANT: if we allow components to be removed unconditionally,
    // Window, which is the Memento caretaker, has the responsibility
    // to delete the Mementos referring to deleted objects.
    // Also if removing the activeEditor, the reference should go back to
    // another Editor or the removed object won't be actually garbage collected.
    /* public void removeEditor(Editor editor) {
        compMng.remove(editor);
    } */

    /**
     * Create a custom shortcut and bind a commmand to it.
     * @param name the name to be assigned to the shortcut
     * @param command the command triggered by the shortcut
     */
    public void createShortcut(String name, Command command) {
        compMng.create(ComponentManager.Type.SHORTCUT, name, command);
    }

    /**
     * Create a macro command that can be attached to a shortcut created
     * with {@link #createShortcut(String, Command)}.
     * @param name the macro identifier
     * @return a {@code Command} object on which you can call {@code add}
     * method to edit the macro
     */
    public Command createMacro(String name) {
        return commMng.createMacro(name);
    }

    /**
     * save a Memento in the history
     * @param memento the memento to save
     */
    public void storeSnapshot(Memento memento) {
        history.push(memento);
    }

    public void undo() {
        Memento toRestore = history.pop();
        if(toRestore != null) {
            toRestore.restore();
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

    /**
     * Just for testing sake.
     * @return the history
     */
    public History<Memento> getHistory() {
        return history.clone();
    }

}