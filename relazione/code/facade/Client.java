public class Client {
    public static void main(String[] args) {
        AppFacade app = new AppFacade();
        ...
        app.write("Testo di prova");
        ...
        Command macro = app.createMacro("Cut and Paste on New Editor");
        macro.add(app.getCommand(CommandManager.Type.CUT));
        macro.add(app.getCommand(CommandManager.Type.NEWEDITOR));
        macro.add(app.getCommand(CommandManager.Type.PASTE));
    }
}