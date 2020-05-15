public final class Window {
    
    ...
    
    // Window is the external caretaker of the Mementos' history
    private History<Memento> history;
    
    public void storeSnapshot(Memento memento) {
        history.push(memento);
    }

    public void undo() {
        Memento toRestore = history.pop();
        if(toRestore != null) {
            toRestore.restore();
        }
    }
}