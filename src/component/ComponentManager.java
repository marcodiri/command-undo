package component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.Application;
import command.Command;

public class ComponentManager {

    public enum Type {
        BUTTON,
        SHORTCUT,
        EDITOR
    }

    private Application app;
    private Map<Type, Component> prototypes;
    private Map<Integer, Component> components;
    private Map<String, Integer> nameToId; // just a helper to call the component by name in the client
    private List<Editor> editors;

    public ComponentManager(Application app) {
        this.app = app;
        components = new HashMap<>();
        nameToId = new HashMap<>();
        editors = new ArrayList<>();
    };

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
            prototypes.put(Type.BUTTON, new Button(null, null));
            prototypes.put(Type.SHORTCUT, new Shortcut(null, null));
            prototypes.put(Type.EDITOR, new Editor(app, null, null));
        }
        // RIP: remove IF with Prototype
        // instead of having a switch(type), just prototype every component and pass a clone
        Component component = prototypes.get(type).clone();
        component.setName(name);
        if(type != Type.EDITOR) {
            component.setCommand(command);
        } else {
            editors.add((Editor)component);
            app.setActiveEditor((Editor)component);
        }
        components.put(component.getId(), component);
        nameToId.put(component.getName(), component.getId());
        return component;
    }

    public void remove(Component component) {
        Component toRemove = components.get(component.getId());
        // need to know if an editor is being removed
        if(toRemove instanceof Editor) {
            // remove from editors list
            editors.remove(component);

            // if activeEditor got removed make another editor active
            if(toRemove == app.getActiveEditor()) {
                // restore last editor as the activeEditor if any
                if(editors.size()-1 < 0) {
                    app.setActiveEditor(null);
                } else {
                    app.setActiveEditor(editors.get(editors.size()-1));
                }
            }
        }

        // remove from both components and nameToId maps
        nameToId.remove(components.remove(component.getId()).getName());
    }

    public Component get(String name) {
        return components.get(nameToId.get(name));
    }

}