package component;

import java.sql.Timestamp;

import app.Application;
import command.Command;

public class Editor extends Component {

    private Application app;
    private String text;
    private int caretPos, selectionWidth;

    public Editor(Application app, String name, Command command) {
        super(name, command);
        this.app = app;
        text = "";
        caretPos = 0;
        selectionWidth = 0;
    }

    @Override
    public void click() {
        app.setActiveEditor(this);
    }

    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
        setCaretPos(this.text.length()); // move the caret at the end of the text
        setSelectionWidth(0);
    }

    public String getSelection() {
        return text.substring(caretPos, caretPos + selectionWidth);
    }

    public void replaceSelection(String replacement) {
        StringBuilder builder = new StringBuilder(text);
        builder.replace(caretPos, caretPos + selectionWidth, replacement);
        int newCaretPos = caretPos + replacement.length(); // move caret at the end of the pasted text
        setText(builder.toString());
        setCaretPos(newCaretPos);;
    }

    /**
     * @return the caretPos
     */
    public int getCaretPos() {
        return caretPos;
    }

    /**
     * Set the editor caret position.
     * @param pos the caret position to be set. Must be non-negative and cannot exceed the text length.
     */
    public void setCaretPos(int pos) throws RuntimeException {
        if(pos > text.length() || pos < 0) {
            throw new RuntimeException("Cannot set the sursor position out of the text");
        } else {
            this.caretPos = pos;
        }
    }

    /**
     * @return the selectionWidth
     */
    public int getSelectionWidth() {
        return selectionWidth;
    }

    /**
     * Set the editor selection width.
     * @param width the width of the selection to be set.
     * If positive it cannot exceed the {@code text.length() - caretPos},
     * if negative {@code caretPos - selectionWidth} cannot be less than {@code 0}.
     */
    public void setSelectionWidth(int width) throws RuntimeException {
        if(width > text.length() - caretPos || caretPos + width < 0) {
            throw new RuntimeException("Selection out of the text");
        } else {
            this.selectionWidth = width;
        }
    }
    
    /**
     * Implements the Memento design pattern.<p>
     * It can save and restore a backup of the Editor because it sees the Editor private fields,
     * but no one outside with access to the Memento can write nor read the Memento's (and so the Editor's) fields.
     * It can expose to the public some non-sensible information on the backup like the Timestamp.
     * So it's safe for the Editor to pass it outside.<p>
     * This class is immutable.
     * Should be instantiated by the Object itself.
     */
    public final class EditorMemento {
        private final Timestamp timestamp;
        private final String text;
        private final int caretPos, selectionWidth;

        public EditorMemento() {
            timestamp = new Timestamp(System.currentTimeMillis());
            this.text = Editor.this.text;
            this.caretPos = Editor.this.caretPos;
            this.selectionWidth = Editor.this.selectionWidth;
        }

        /**
         * @return the timestamp
         */
        public Timestamp getTimestamp() {
            // return a copy not the reference!
            return new Timestamp(timestamp.getTime());
        }

        /**
         * restore the state of the editor
         */
        public void restore() {
            Editor.this.text = this.text;
            Editor.this.caretPos = this.caretPos;
            Editor.this.selectionWidth = this.selectionWidth;
            System.out.println("Restored "+Editor.this.getName()+" with text: \""+this.text+"\"");
        }

    }

    /**
     * @return a {@code Memento} with the current state of the {@link Drawable} object.
     */
    public EditorMemento saveSnapshot() {
        return new EditorMemento();
    }


}