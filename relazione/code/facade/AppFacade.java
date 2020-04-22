public class AppFacade {
    private Window activeWindow;
    private CommandManager commMng;
    ...
    public void write(String text) {
        activeWindow.getActiveEditor().setText(text);
    }
    ...
    public Command createMacro(String name) {
        return commMng.createMacro(name);
    }
}