package history;

import java.util.EmptyStackException;
import java.util.Stack;

public class History<T> {
    
    private Stack<T> history;

    public History() {
        history = new Stack<>();
    }

    public void push(T item) {
        history.push(item);
    }

    public T pop() {
        try {
            return history.pop();
        } catch(EmptyStackException e) {
            return null;
        }
    }

}