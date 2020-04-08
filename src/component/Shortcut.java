package component;

import command.Command;

public class Shortcut extends Component {

    private Command command;

    public Shortcut(String name, Command command) {
        super(name, command);
    }

}