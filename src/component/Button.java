package component;

import app.Application;
import command.Command;

public class Button extends Component {

    private Command command;

    public Button(Application app, String name, Command command) {
        super(app, name);
        this.command = command;
    }

    public void click() {
        app.executeCommand(command);
    }

}