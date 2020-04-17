package component;

import java.sql.Timestamp;

/**
 * Interface for all the Mementos.<p>
 * Every component who wants its state to be saved should declare
 * a specific nested Memento class that implements this interface.
 */
public interface Memento {

    public Timestamp getTimestamp();
    public void restore();

}