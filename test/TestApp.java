import static org.junit.Assert.*;

import org.junit.Test;

import app.Application;
import command.*;

public class TestApp {

    private Application app;

    public void setUp() {
        app = new Application();
        // create a new editor and write into it
        app.click("NewEditorButton");
        app.getActiveEditor().setText("Testo di prova");
    }

    // helper function
    boolean printActiveEditorText () {
        if (app.getActiveEditor() != null) {
            System.out.println(app.getActiveEditor().getName()+" text: \""+app.getActiveEditor().getText()+"\"\n");
            return true;
        } else {
            System.out.println("no active editor");
            return false;
        }
    }

    @Test
	public void testCutCommand() {
        setUp();
        printActiveEditorText();

        // cut some text from editor1
        // with button
        app.getActiveEditor().setCaretPos(5);
        app.getActiveEditor().setSelectionWidth(3);
        app.click("CutButton");
        printActiveEditorText();

        assertTrue(app.getCommandsHistory().size() == 1); // the command gets saved into history
        assertTrue(app.getCommandsHistory().pop() instanceof CutCommand); // pop() does not change app state cause getCommandHistory() returns a copy
        assertEquals(" di", app.clipboard);
        assertEquals("Testo prova", app.getActiveEditor().getText());
        
        // with shortcut
        app.getActiveEditor().setCaretPos(5);
        app.getActiveEditor().setSelectionWidth(app.getActiveEditor().getText().length()-app.getActiveEditor().getCaretPos());
        app.click("Ctrl+X");
        printActiveEditorText();

        assertTrue(app.getCommandsHistory().size() == 2);
        assertTrue(app.getCommandsHistory().pop() instanceof CutCommand);
        assertEquals(" prova", app.clipboard);
        assertEquals("Testo", app.getActiveEditor().getText());
	}

    @Test
    public void testUndoCommand() {
        testCutCommand();

        // undo the last command
        // with button
        app.click("UndoButton");
        printActiveEditorText();

        assertTrue(app.getCommandsHistory().size() == 1); // the command gets removed from history
        assertEquals("Testo prova", app.getActiveEditor().getText());
        
        // with shortcut
        app.click("Ctrl+Z");
        printActiveEditorText();

        assertTrue(app.getCommandsHistory().size() == 0);
        assertEquals("Testo di prova", app.getActiveEditor().getText());
    }

    @Test
    public void testMacroCommand() {
        setUp();

        // create a macro to copy text and paste it on a new editor
        Command macro = app.createMacro("Ctrl+Shift+X");
        macro.add(new CutCommand(app));
        macro.add(new NewEditorCommand(app));
        macro.add(new PasteCommand(app));
        
        app.getActiveEditor().setCaretPos(0);
        app.getActiveEditor().setSelectionWidth(app.getActiveEditor().getText().length()/2);
        int editorId = app.getActiveEditor().getId();
        app.click("Ctrl+Shift+X");
        printActiveEditorText();
        assertTrue(app.getCommandsHistory().size() == 2); // both the cut and paste commands have been saved
        assertTrue(app.getActiveEditor().getId() != editorId); // a new editor has been created by the macro
        assertEquals("Testo d", app.getActiveEditor().getText()); // the copied text has been pasted onto the new editor
    }

}