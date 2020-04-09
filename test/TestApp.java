import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestInstance;

import app.Application;
import command.CutCommand;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestApp {

    private static Application app;

    @BeforeClass
    public static void setUp() {
        app = new Application();
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
    @Order(1)
	public void testCutCommand() {		
        // create a new editor and write into it
        app.createEditor("Editor1").setText("Testo di prova");
        printActiveEditorText();

        // cut some text from editor1
        // with button
        app.getActiveEditor().setCaretPos(5);
        app.getActiveEditor().setSelectionWidth(3);
        app.getControl("CutButton").click();
        printActiveEditorText();

        assertTrue(app.getCommandsHistory().size() == 1); // the command gets saved into history
        assertTrue(app.getCommandsHistory().pop() instanceof CutCommand); // pop() does not change app state cause getCommandHistory() returns a copy
        assertEquals(" di", app.clipboard);
        assertEquals("Testo prova", app.getActiveEditor().getText());
        
        // with shortcut
        app.getActiveEditor().setCaretPos(5);
        app.getActiveEditor().setSelectionWidth(app.getActiveEditor().getText().length()-app.getActiveEditor().getCaretPos());
        app.getControl("Ctrl+X").click();
        printActiveEditorText();

        assertTrue(app.getCommandsHistory().size() == 2);
        assertTrue(app.getCommandsHistory().pop() instanceof CutCommand);
        assertEquals(" prova", app.clipboard);
        assertEquals("Testo", app.getActiveEditor().getText());
	}

    @Test
    @Order(2)
    public void testUndoCommand() {
        if(!printActiveEditorText()) {
            return;
        }

        // undo the last command
        // with button
        app.getControl("UndoButton").click();
        printActiveEditorText();

        assertTrue(app.getCommandsHistory().size() == 1); // the command gets removed from history
        assertEquals("Testo prova", app.getActiveEditor().getText());
        
        // with shortcut
        app.getControl("Ctrl+Z").click();
        printActiveEditorText();

        assertTrue(app.getCommandsHistory().size() == 0);
        assertEquals("Testo di prova", app.getActiveEditor().getText());
    }

}