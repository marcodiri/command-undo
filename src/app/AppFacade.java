package app;

import java.util.ArrayList;
import java.util.List;

import command.Command;
import command.CommandManager;
import component.*;

/**
 * {@code Facade} for the client.
 * It exposes all the useful methods to use the application without
 * the client having to know the underlying architecture.
 */
public class AppFacade {

    private List<Window> windows;
    private Window activeWindow;
    private CommandManager commMng;

    public AppFacade() {
        commMng = new CommandManager();
        windows = new ArrayList<>();
        createWindow(); // create a Window by default on init
    }

    // this should also be a Command but whatever
    public void createWindow() {
        Window newWin = new Window("Window-"+UniqueID.getID(), commMng);
        windows.add(newWin);
        activeWindow = newWin;
        setActiveWindow(newWin.getName());
    }

    public void setActiveWindow(String name) {
        windows.forEach(w->{
            if(w.getName() == name) {
                activeWindow = w;
                Command.setActiveWindow(activeWindow); // Observer-like update() push flavor
                System.out.println("Set active window to: "+w.getName());
                return;
            }
        });
    }

    /**
     * A fake method to simulate the click on a UI component of the {@link #activeWindow}.
     * Invokes the click() handler of the component.
     * @param compName the name of the component to be clicked.
     */
    public void click(String compName) {
        activeWindow.click(compName);
    }

    // helper to write something on the activeEditor of the activeWindow
    public void write(String text) {
        activeWindow.getActiveEditor().setText(text);
    }

    // helper to select some text on the activeEditor
    /**
     * Select some text on the {@code activeEditor}.
     * @param caretPos the position of the caret, {@code -1} corresponds
     * to the end of the text
     * @param selectionWidth the width in characters of selection,
     * {@code -1} to select until the end of the text
     * @param toRight {@code true} if the selection is from caret to right,
     * {@code false} if the selection is from caret to left
     */
    public void selectText(int caretPos, int selectionWidth, boolean toRight) {
        activeWindow.getActiveEditor().setCaretPos(caretPos);
        activeWindow.getActiveEditor().setSelectionWidth(selectionWidth, toRight);
    }

    /**
     * Get a default command or a macro created with {@link #createMacro}.
     * @param name one of {@link CommandManager.Type} or a macro identifier
     * @return the requested {@link Command}
     */
    public Command getCommand(CommandManager.Type name) {
        return commMng.get(name);
    }
    
    // overload to get macros
    public Command getCommand(String name) {
        return commMng.get(name);
    }

    /**
     * Create a custom shortcut and bind a commmand to it.
     * @param name the name to be assigned to the shortcut
     * @param commandName the command triggered by the shortcut,
     * either a {@link CommandManager.Type} or a macro name
     */
    public void createShortcut(String name, String commandName) {
        activeWindow.createShortcut(name, commandName);
    }

    /**
     * Create a macro command that can be attached to a shortcut created
     * with {@link #createShortcut(String, Command)}.
     * @param name the macro identifier
     * @return a {@code Command} object on which you can call {@code add}
     * method to edit the macro
     */
    public Command createMacro(String name) {
        return commMng.createMacro(name);
    }

}