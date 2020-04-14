import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import app.Application;
import command.*;

public class TestApp {

    private Application app;

    @Before
    public void setUp() {
        app = new Application();
    }

    // TODO: add in the test: get Memento list and see if it has been saved
    @Test
    public void testNewEditorCommand() {
        // create a new editor and write into it
        //with button
        app.click("NewEditorButton");
        app.getActiveEditor().setText("Testo di prova");

        assertTrue(app.getCommandsHistory().size() == 1); // the command gets saved into history
        assertTrue(app.getCommandsHistory().pop() instanceof NewEditorCommand);

        // with shortcut
        app.click("Ctrl+N");
        app.getActiveEditor().setText("Un altro testo di prova");
        assertTrue(app.getCommandsHistory().size() == 2);
        assertTrue(app.getCommandsHistory().pop() instanceof NewEditorCommand);
    }

    @Test
	public void testCutCommand() {
        app.click("NewEditorButton");
        app.getActiveEditor().setText("Testo di prova");

        // cut some text from editor1
        // with button
        app.getActiveEditor().setCaretPos(5);
        app.getActiveEditor().setSelectionWidth(3);
        app.click("CutButton");

        assertTrue(app.getCommandsHistory().size() == 2); // the command gets saved into history
        assertTrue(app.getCommandsHistory().pop() instanceof CutCommand); // pop() does not change app state cause getCommandHistory() returns a copy
        assertEquals(" di", app.clipboard);
        assertEquals("Testo prova", app.getActiveEditor().getText());
        
        // with shortcut
        app.getActiveEditor().setCaretPos(5);
        app.getActiveEditor().setSelectionWidth(app.getActiveEditor().getText().length()-app.getActiveEditor().getCaretPos());
        app.click("Ctrl+X");

        assertTrue(app.getCommandsHistory().size() == 3);
        assertTrue(app.getCommandsHistory().pop() instanceof CutCommand);
        assertEquals(" prova", app.clipboard);
        assertEquals("Testo", app.getActiveEditor().getText());
	}

    @Test
    public void testMacroCommand() {
        app.click("NewEditorButton");
        app.getActiveEditor().setText("Testo di prova");

        // create a macro to copy text and paste it on a new editor
        Command macro = app.createMacro("Cut and Paste on New Editor", "Ctrl+Shift+X");
        macro.add(app.getCommand(CommandManager.Type.CUT));
        macro.add(app.getCommand(CommandManager.Type.NEWEDITOR));
        macro.add(app.getCommand(CommandManager.Type.PASTE));
        
        app.getActiveEditor().setCaretPos(0);
        app.getActiveEditor().setSelectionWidth(app.getActiveEditor().getText().length()/2);
        int editorId = app.getActiveEditor().getId(); // just to test if editor has in fact changed
        app.click("Ctrl+Shift+X");

        assertTrue(app.getCommandsHistory().size() == 5); // initial NewEditorCommand + the 3 commands in the macro + the macro itself
        assertEquals("Testo d", app.clipboard); // the selected text has been cut from the active editor
        assertTrue(app.getActiveEditor().getId() != editorId); // a new editor has been created by the macro
        assertEquals("Testo d", app.getActiveEditor().getText()); // the copied text has been pasted onto the new editor
    }

    @Test
    public void testUndoCommand() {
        testMacroCommand();

        // undo the macro command
        app.click("UndoButton");

        assertTrue(app.getCommandsHistory().size() == 1); // the commands get removed from history
        assertEquals("Testo di prova", app.getActiveEditor().getText());
    }

}