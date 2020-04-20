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
        newButton.setCommand(getCommand()); // FIXME: should clone the command too, otherwise a component with a macro could be changed by the clone with clone.getCommand().add(...)
        return newButton;
    }

}