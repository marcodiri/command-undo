package history;

import java.sql.Timestamp;

/**
 * Interface for all the Mementos.<p>
 * Every entity which wants its state to be saved should declare
 * a specific {@code nestedMemento} class that implements this interface.
 */
public interface Memento {

    public Timestamp getTimestamp();
    public void restore();

}