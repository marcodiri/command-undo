package component;

import app.UniqueID;
import command.*;
import history.*;

/**
 * The {@code Receiver} of the Command pattern.<p>
 * The {@code Caretaker} of the Memento pattern.
 */
public class Window {

    private final int id;
    private String name;
    private ComponentManager compMng;
    public String clipboard;
    private Editor activeEditor;

    // Window is the external caretaker of the Mementos' history
    private History<Memento> history;

    public Window(String name, CommandManager commMng) {
        id = UniqueID.generateID();
        this.name = name;
        compMng = new ComponentManager(this, commMng);
        history = new History<>();
        generateUI();
    }

    private void generateUI() {
        // create the buttons
        compMng.create(ComponentManager.Type.BUTTON, "CopyButton", CommandManager.Type.COPY);
        compMng.create(ComponentManager.Type.BUTTON, "PasteButton", CommandManager.Type.PASTE);
        compMng.create(ComponentManager.Type.BUTTON, "CutButton", CommandManager.Type.CUT);
        compMng.create(ComponentManager.Type.BUTTON, "UndoButton", CommandManager.Type.UNDO);
        compMng.create(ComponentManager.Type.BUTTON, "NewEditorButton", CommandManager.Type.NEWEDITOR);

        // create the shortcuts
        compMng.create(ComponentManager.Type.SHORTCUT, "Ctrl+C", CommandManager.Type.COPY);
        compMng.create(ComponentManager.Type.SHORTCUT, "Ctrl+V", CommandManager.Type.PASTE);
        compMng.create(ComponentManager.Type.SHORTCUT, "Ctrl+X", CommandManager.Type.CUT);
        compMng.create(ComponentManager.Type.SHORTCUT, "Ctrl+Z", CommandManager.Type.UNDO);
        compMng.create(ComponentManager.Type.SHORTCUT, "Ctrl+N", CommandManager.Type.NEWEDITOR);
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * A fake method to simulate the click on a GUI component of this window.
     * Invokes the click() handler of the component.
     * @param compName the name of the component to be clicked.
     */
    public void click(String compName) {
        compMng.get(compName).click();
    }

    /**
     * Create a new {@link Editor} and set it as the activeEditor
     * @param name the new {@code Editor} name
     * @return the new {@code Editor}
     */
    public void createEditor(String name) {
        Editor newEditor = (Editor)compMng.create(ComponentManager.Type.EDITOR, name, CommandManager.Type.NULL);
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
     * @param commandName the command triggered by the shortcut,
     * either a {@link CommandManager.Type} or a macro name
     */
    public void createShortcut(String name, String commandName) {
        compMng.create(ComponentManager.Type.SHORTCUT, name, commandName);
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

}