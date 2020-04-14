package component;

import command.Command;

public class Shortcut extends Component {

    Shortcut(String name, Command command) {
        super(name, command);
    }

    @Override
    public Shortcut clone() {
        return new Shortcut(getName(), getCommand());
    }

}