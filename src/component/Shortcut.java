package component;

public class Shortcut extends Component {

    Shortcut(String name) {
        super(name);
    }

    @Override
    public void click() {
        getCommand().execute();
    }

    @Override
    public Shortcut clone() {
        Shortcut newShortcut = new Shortcut(getName());
        newShortcut.setCommand(getCommand());
        return newShortcut;
    }

}