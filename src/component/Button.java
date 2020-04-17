package component;

public class Button extends Component {

    Button(String name) {
        super(name);
    }

    @Override
    public void click() {
        getCommand().execute();
    }

    @Override
    public Button clone() {
        Button newButton = new Button(getName());
        newButton.setCommand(getCommand()); // FIXME: if we don't clone the command the ComponentMemento will be mutable, do we care?
        return newButton;
    }

}