package component;

import command.Command;

public class Button extends Component {

    Button(String name, Command command) {
        super(name, command);
    }

    @Override
    public Button clone() {
        return new Button(getName(), getCommand());
    }

}