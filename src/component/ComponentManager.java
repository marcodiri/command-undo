package component;

import java.util.HashMap;
import java.util.Map;

import command.CommandManager;

/**
 * Responsible of instantiating {@link Component}s on behalf of {@link Window}
 * and manage their lifecycle.
 * It dialogs with {@link CommandManager} to fetch the commands to attach to components.
 */
public class ComponentManager {

    public enum Type {
        BUTTON,
        SHORTCUT,
        EDITOR
    }

    private Window window; /** FIXME: this looks awkward, only needed by EDITOR to set itself as the activeEditor in the click handler.
    I would like to make a command SetActiveEditor to attach to the Editor click(), but Command pattern does not
    accept parameters in the execute() so Editor has no way to pass itself and make the Command responsible of
    calling activeWindow.setActiveEditor(editor). There are also no ways for the Command to get this information
    via other routes.
     */
    private CommandManager commMng;
    private Map<Type, Component> prototypes;
    private Map<Integer, Component> components;
    private Map<String, Integer> nameToId; // just a helper to call the component by name in the client for testing purpose

    ComponentManager(Window window, CommandManager commMng) {
        this.window = window;
        this.commMng = commMng;
        components = new HashMap<>();
        nameToId = new HashMap<>();
    }

    /**
     * Creates a new {@link Component}.<p>
     * When creating a {@code Type.EDITOR}, the new editor will be set
     * as the active editor by {@link Application#setActiveEditor(Editor)}
     * @param app the application you are working on
     * @param type on of {@link Type}
     * @param name the name to assign to the component
     * @param commandName the command to run on the component click,
     * either a {@link CommandManager.Type} or a macro name
     * (always {@code null} for a {@code Type.EDITOR})
     * @return the created {@link Component}
     */
    public Component create(Type type, String name, String commandName) {
        // lazy load the prototypes
        if(prototypes == null) {
            prototypes = new HashMap<>();
            prototypes.put(Type.BUTTON, new Button(null));
            prototypes.put(Type.SHORTCUT, new Shortcut(null));
            prototypes.put(Type.EDITOR, new Editor(null, window));
        }
        // RIP: Remove IF with Prototype
        // instead of having a switch(type), just prototype every component and pass a clone
        Component component = null;
        try {
            component = prototypes.get(type).clone();
            component.setName(name);
            component.setCommand(commMng.get(commandName));
            components.put(component.getId(), component);
            nameToId.put(component.getName(), component.getId());
        } catch(CloneNotSupportedException e) {
            System.out.println(e.getMessage());
        }
        return component;
    }

    // overload to accept a CommandManager.Type
    public Component create(Type type, String name, CommandManager.Type commandName) {
        return create(type, name, commandName.toString());
    }

    /* public void remove(Component component) {
        // remove from both components and nameToId maps
        nameToId.remove(components.remove(component.getId()).getName());
    } */

    public Component get(String name) {
        return components.get(nameToId.get(name));
    }

}