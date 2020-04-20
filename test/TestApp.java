import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import app.AppFacade;
import command.*;
import component.*;
import component.Editor.EditorMemento;
import history.*;

public class TestApp {

    private AppFacade app;
    private CommandManager commMng;
    private Field aw;
    private Window activeWindow;
    private ComponentManager compMng;
    private Map<Integer, Component> components;
    private Map<String, Integer> nameToId;
    private History<Memento> history;

    @Before
    public void setUp() {
        try {
            app = new AppFacade();
            // test private fields with reflection
            aw = AppFacade.class.getDeclaredField("activeWindow");
            aw.setAccessible(true);
            activeWindow = (Window)aw.get(app);

            Field comM = AppFacade.class.getDeclaredField("commMng");
            comM.setAccessible(true);
            commMng = (CommandManager)comM.get(app);

            Field h = Window.class.getDeclaredField("history");
            h.setAccessible(true);
            history = (History<Memento>)h.get(activeWindow);

            Field cm = Window.class.getDeclaredField("compMng");
            cm.setAccessible(true);
            compMng = (ComponentManager)cm.get(activeWindow);

            Field comps = ComponentManager.class.getDeclaredField("components");
            Field nti = ComponentManager.class.getDeclaredField("nameToId");
            comps.setAccessible(true);
            nti.setAccessible(true);
            components = (Map<Integer, Component>)comps.get(compMng);
            nameToId = (Map<String, Integer>)nti.get(compMng);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testCommandPatern() {
        
        // different components have the same command
        Component newEditorButton = components.get(nameToId.get("NewEditorButton"));
        Component newEditorShortcut = components.get(nameToId.get("Ctrl+N"));

        assertTrue(newEditorButton instanceof Button);
        assertTrue(newEditorShortcut instanceof Shortcut);

        Command newEditorButtonCommand = newEditorButton.getCommand();
        Command newEditorShortcutCommand = newEditorShortcut.getCommand();
        
        // both button and shortcut refer to the same command
        // N.B. the same read as the exact same object (commands objects are shared)
        assertTrue(newEditorButtonCommand instanceof NewEditorCommand);
        assertTrue(newEditorButtonCommand == newEditorShortcutCommand);

    }


    @Test
    public void testNewEditorCommand() {

        // create a new editor and write into it
        //with button
        app.click("NewEditorButton");
        app.write("Testo di prova");

        assertTrue(components.get(activeWindow.getActiveEditor().getId()) == activeWindow.getActiveEditor()); // the created editor gets saved by the CommandManager
        assertEquals("Testo di prova", activeWindow.getActiveEditor().getText());

        // with shortcut
        app.click("Ctrl+N");
        app.write("Un altro testo di prova");

        assertTrue(components.get(activeWindow.getActiveEditor().getId()) == activeWindow.getActiveEditor()); // the created editor gets saved by the CommandManager
        assertEquals("Un altro testo di prova", activeWindow.getActiveEditor().getText());

    }

    @Test
	public void testCutCommand() {
        // create a new editor and write into it
        app.click("NewEditorButton");
        app.write("Testo di prova");

        // cut some text from editor1
        // with button
        app.selectText(5, 3, true);
        app.click("CutButton");

        assertTrue(history.size() == 1); // the command gets saved into history
        assertTrue(history.clone().pop() instanceof EditorMemento); // clone history to not change app state
        assertEquals(" di", activeWindow.clipboard);
        assertEquals("Testo prova", activeWindow.getActiveEditor().getText());
        
        // with shortcut
        app.selectText(5, -1, true);
        app.click("Ctrl+X");

        assertTrue(history.size() == 2);
        assertTrue(history.clone().pop() instanceof EditorMemento);
        assertEquals(" prova", activeWindow.clipboard);
        assertEquals("Testo", activeWindow.getActiveEditor().getText());
	}

    @Test
    public void testUndoCommand() {
        testCutCommand();

        // undo the cut command
        // with button
        app.click("UndoButton");

        // the last CUT command gets reverted
        assertTrue(history.size() == 1);
        assertEquals("Testo prova", activeWindow.getActiveEditor().getText());

        // with shortcut
        app.click("Ctrl+Z");

        // the first CUT command gets reverted
        assertTrue(history.size() == 0);
        assertEquals("Testo di prova", activeWindow.getActiveEditor().getText());
    }
    
    @Test
    public void testMacroCommand() {
        app.click("NewEditorButton");
        app.write("Testo di prova");

        // create a macro to copy text and paste it on a new editor
        Command macro = app.createMacro("Cut and Paste on New Editor");
        macro.add(app.getCommand(CommandManager.Type.CUT));
        macro.add(app.getCommand(CommandManager.Type.NEWEDITOR));
        macro.add(app.getCommand(CommandManager.Type.PASTE));

        assertTrue(commMng.get("Cut and Paste on New Editor") instanceof MacroCommand); // a new MacroCommand has been created

        app.createShortcut("Ctrl+Shift+X", "Cut and Paste on New Editor"); // attach macro to custom shortcut
        
        app.selectText(0, 7, true);
        int oldEditorId = activeWindow.getActiveEditor().getId(); // to test if activeEditor has in fact changed
        app.click("Ctrl+Shift+X");

        assertTrue(history.size() == 2); // the 2 undoable commands in the macro
        // both CUT and PASTE commands request a snapshot to Editor
        History<Memento> clone = history.clone();
        assertTrue(clone.pop() instanceof EditorMemento);
        assertTrue(clone.pop() instanceof EditorMemento);
        assertEquals("Testo d", activeWindow.clipboard); // the selected text has been cut from the active editor
        assertTrue(activeWindow.getActiveEditor().getId() > oldEditorId); // a new editor has been created by the macro
        assertEquals("Testo d", activeWindow.getActiveEditor().getText()); // the copied text has been pasted onto the new editor
    }

    @Test
    public void testMacroOnNewWindow() {
        testMacroCommand();

        int oldWindowId = activeWindow.getId(); // to test a new window has been created
        app.createWindow();
        try {
            activeWindow = (Window)aw.get(app);
        } catch (IllegalAccessException e) {}

        assertTrue(activeWindow.getId() > oldWindowId);

        app.click("NewEditorButton");
        app.write("Prova editor in nuova finestra");

        // attach macro to a custom shortcut in the new window without creating it again
        app.createShortcut("Ctrl+Shift+V", "Cut and Paste on New Editor");

        app.selectText(0, 12, true);
        app.click("Ctrl+Shift+V");

        assertTrue(history.size() == 2); // the 2 undoable commands in the macro
        // both CUT and PASTE commands request a snapshot to Editor
        History<Memento> clone = history.clone();
        assertTrue(clone.pop() instanceof EditorMemento);
        assertTrue(clone.pop() instanceof EditorMemento);
        assertEquals("Prova editor", activeWindow.clipboard); // the selected text has been cut from the active editor
        assertEquals("Prova editor", activeWindow.getActiveEditor().getText()); // the copied text has been pasted onto the new editor
    }

}