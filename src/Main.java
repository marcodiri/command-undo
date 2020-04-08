import app.Application;

public class Main {

    // helper function
    static void printActiveEditorText (Application app) {
        System.out.println(app.getActiveEditor().getName()+" text: \""+app.getActiveEditor().getText()+"\"\n");
    } 

    public static void main(String[] args) {
        // Simulate a text editor application with multiple editor windows.
        // Uses the Command design pattern to attach a same handler on Buttons and Shortcuts.
        // Uses the Memento design pattern to undo an undoable Command.

        Application app = new Application();

        // create a new editor and write into it
        app.createEditor("Editor1").setText("Testo di prova");
        printActiveEditorText(app);

        // cut some text from editor1
        app.getActiveEditor().setCaretPos(5);
        app.getActiveEditor().setSelectionWidth(app.getActiveEditor().getText().length()-app.getActiveEditor().getCaretPos());
        app.getControl("Ctrl+X").click();
        printActiveEditorText(app);
        // restore the cut text
        app.getControl("Ctrl+Z").click();
        printActiveEditorText(app);
        // create editor2 and paste the cut text on it
        app.createEditor("Editor2").setText("Un altro testo");
        printActiveEditorText(app);
        app.getControl("Ctrl+V").click();
        printActiveEditorText(app);

        // paste the same text on the first editor at the end of the existing text
        app.getEditor("Editor1").click(); // clicking an editor makes it the activeEditor
        app.getActiveEditor().setCaretPos(app.getActiveEditor().getText().length());
        app.getControl("PasteButton").click();
        printActiveEditorText(app);
    }

}