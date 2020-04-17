package component;

import java.util.HashMap;
import java.util.Map;

import command.Command;

public class ComponentManager {

    public enum Type {
        BUTTON,
        SHORTCUT,
        EDITOR
    }

    private Window window;
    private Map<Type, Component> prototypes;
    private Map<Integer, Component> components;
    private Map<String, Integer> nameToId; // just a helper to call the component by name in the client for testing purpose

    ComponentManager(Window window) {
        this.window = window;
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
     * @param command the command to run on the component click
     * (always {@code null} for a {@code Type.EDITOR})
     * @return the created {@link Component}
     */
    public Component create(Type type, String name, Command command) {
        // lazy load the prototypes
        if(prototypes == null) {
            prototypes = new HashMap<>();
            prototypes.put(Type.BUTTON, new Button(null));
            prototypes.put(Type.SHORTCUT, new Shortcut(null));
            prototypes.put(Type.EDITOR, new Editor(null, window));
        }
        // RIP: remove IF with Prototype
        // instead of having a switch(type), just prototype every component and pass a clone
        Component component = null;
        try {
            component = prototypes.get(type).clone();
            component.setName(name);
            component.setCommand(command);
            components.put(component.getId(), component);
            nameToId.put(component.getName(), component.getId());
        } catch(CloneNotSupportedException e) {
            System.out.println(e.getMessage());
        }
        return component;
    }

    /* public void remove(Component component) {
        // remove from both components and nameToId maps
        nameToId.remove(components.remove(component.getId()).getName());
    } */

    public Component get(String name) {
        return components.get(nameToId.get(name));
    }

}