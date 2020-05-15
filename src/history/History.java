package history;

import java.util.EmptyStackException;
import java.util.Stack;

public class History<T> implements Cloneable {
    
    private Stack<T> history;

    public History() {
        history = new Stack<>();
    }

    private History(Stack<T> toCopy) {
        history = (Stack<T>) toCopy.clone();
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

    @Override
    public History<T> clone() {
        return new History<T>(history);
    }

    public int size() {
        return history.size();
    }

}