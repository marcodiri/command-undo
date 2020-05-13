public class Editor extends Component {
    private String text;
    private int caretPos, selectionWidth;
    
    ...
    
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

        public Timestamp getTimestamp() {
            // return a copy not the reference!
            return new Timestamp(timestamp.getTime());
        }

        public void restore() {
            Editor.this.text = this.text;
            Editor.this.caretPos = this.caretPos;
            Editor.this.selectionWidth = this.selectionWidth;
        }

    }

    public EditorMemento getSnapshot() {
        return new EditorMemento();
    }
}