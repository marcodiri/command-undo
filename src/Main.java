import app.Application;

public class Main {

    // helper function
    static void printActiveEditorText (Application app) {
        System.out.println(app.activeEditor.getName()+" text: \""+app.activeEditor.getText()+"\"\n");
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
        app.activeEditor.setCaretPos(5);
        app.activeEditor.setSelectionWidth(app.activeEditor.getText().length()-app.activeEditor.getCaretPos());
        app.components.get("Ctrl+X").click();
        printActiveEditorText(app);
        // restore the cut text
        app.components.get("Ctrl+Z").click();
        printActiveEditorText(app);
        // create editor2 and paste the cut text on it
        app.createEditor("Editor2").setText("Un altro testo");
        printActiveEditorText(app);
        app.components.get("Ctrl+V").click();
        printActiveEditorText(app);

        // paste the same text on the first editor at the end of the existing text
        app.components.get("Editor1").click(); // clicking an editor makes it the activeEditor
        app.activeEditor.setCaretPos(app.activeEditor.getText().length());
        app.components.get("PasteButton").click();
        printActiveEditorText(app);
    }

}