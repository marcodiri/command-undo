@Test
public void testMacroCommand() {
    
    app.click("NewEditorButton");
    app.write("Testo di prova");

    // create a macro to copy text and paste it on a new editor
    Command macro = app.createMacro("Cut and Paste on New Editor");
    macro.add(app.getCommand(CommandManager.Type.CUT));
    macro.add(app.getCommand(CommandManager.Type.NEWEDITOR));
    macro.add(app.getCommand(CommandManager.Type.PASTE));

    assertTrue(commMng.get("Cut and Paste on New Editor") instanceof MacroCommand); // a new MacroCommand has been created

    app.createShortcut("Ctrl+Shift+X", "Cut and Paste on New Editor"); // attach macro to custom shortcut
    
    app.selectText(0, 7, true);
    int oldEditorId = activeWindow.getActiveEditor().getId(); // to test if activeEditor has in fact changed
    app.click("Ctrl+Shift+X");

    assertTrue(history.size() == 2); // the 2 undoable commands in the macro
    // both CUT and PASTE commands request a snapshot to Editor
    History<Memento> clone = history.clone();
    assertTrue(clone.pop() instanceof EditorMemento);
    assertTrue(clone.pop() instanceof EditorMemento);
    assertEquals("Testo d", activeWindow.clipboard); // the selected text has been cut from the active editor
    assertTrue(activeWindow.getActiveEditor().getId() > oldEditorId); // a new editor has been created by the macro
    assertEquals("Testo d", activeWindow.getActiveEditor().getText()); // the copied text has been pasted onto the new editor

}