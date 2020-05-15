package component;

import java.sql.Timestamp;

import history.Memento;

public final class Editor extends Component {

    private Window window;
    private String text;
    private int caretPos, selectionWidth;

    // package private to be instantiated by the factory
    Editor(String name, Window window) {
        super(name);
        this.window = window;
        text = "";
        caretPos = 0;
        selectionWidth = 0;
    }

    @Override
    public Editor clone() {
        Editor newEditor = new Editor(getName(), window);
        newEditor.text = this.text;
        newEditor.caretPos = this.caretPos;
        newEditor.selectionWidth = this.selectionWidth;
        return newEditor;
    }

    @Override
    public void click() {
        window.setActiveEditor(this);
    }

    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
        setCaretPos(-1); // move the caret at the end of the text
        setSelectionWidth(0, true);
        System.out.print(toString());
        System.out.println(" <- Text set on "+getName()+": \""+text+"\"\n");
    }

    public String getSelection() {
        if(caretPos + selectionWidth > caretPos) {
            return text.substring(caretPos, caretPos + selectionWidth);
        } else {
            return text.substring(caretPos + selectionWidth, caretPos);
        }
    }

    public void replaceSelection(String replacement) {
        StringBuilder builder = new StringBuilder(text);
        if(caretPos + selectionWidth > caretPos) {
            builder.replace(caretPos, caretPos + selectionWidth, replacement);
        } else {
            builder.replace(caretPos + selectionWidth, caretPos, replacement);
        }
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
     * @param pos the caret position to be set.
     * Must be greater than or equal to {@code -1} and cannot exceed the text length.
     * {@code -1} corresponds to the end of the text
     */
    public void setCaretPos(int pos) throws RuntimeException {
        if(pos > text.length() || pos < -1) {
            throw new RuntimeException("Cannot set the sursor position out of the text");
        } else if(pos == -1) {
            this.caretPos = this.text.length();
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
     * Must be greater than or equal to {@code -1}.
     * If positive it cannot exceed the {@code text.length() - caretPos},
     * if {@code -1} the text from caret to the end will be selected.
     * @param toRight {@code true} if the selection is from caret to right.
     * {@code false} if the selection is from caret to left.
     */
    public void setSelectionWidth(int width, boolean toRight) throws RuntimeException {
        if(width < -1 || (toRight && width > text.length() - caretPos) || (!toRight && caretPos - width < 0)) {
            throw new RuntimeException("Selection out of the text");
        } else if(width == -1) {
            if(toRight) {
                this.selectionWidth = text.length() - caretPos;
            } else {
                this.selectionWidth = -caretPos;
            }
        } else {
            if(toRight) {
                this.selectionWidth = width;
            } else {
                this.selectionWidth = -width;
            }
        }
    }

    @Override
    public String toString() {
        int length = text.length() > getName().length() ? text.length() : getName().length();
        StringBuilder builder = new StringBuilder();
        builder.append(" ");
        for(int i=0; i<length+2; i++) {
            builder.append("_");
        }
        builder.append(" ");
        builder.append(System.lineSeparator());
        builder.append("| "+getName());
        for(int i=0; i<length-getName().length()+1; i++) {
            builder.append(" ");
        }
        builder.append("|");
        builder.append(System.lineSeparator());
        builder.append("|");
        for(int i=0; i<length+2; i++) {
            builder.append("‾");
        }
        builder.append("|");
        builder.append(System.lineSeparator());
        builder.append("| "+text);
        for(int i=0; i<length-text.length()+1; i++) {
            builder.append(" ");
        }
        builder.append("|");
        builder.append(System.lineSeparator());
        builder.append("|");
        for(int i=0; i<length+2; i++) {
            builder.append("_");
        }
        builder.append("|");
        return builder.toString();
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
    public final class EditorMemento implements Memento {

        private final Timestamp timestamp;
        private final String text;
        private final int caretPos, selectionWidth;

        // private so that only Editor can instantiate it
        private EditorMemento() {
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
            System.out.println(Editor.this.toString());
        }

    }

    /**
     * @return a {@link Memento} with the current state of the {@link Editor} object.
     */
    public EditorMemento getSnapshot() {
        return new EditorMemento();
    }

}