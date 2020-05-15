public final class Button extends Component {
    ...
    @Override
    public void click() {
        getCommand().execute();
    }
    ...
}