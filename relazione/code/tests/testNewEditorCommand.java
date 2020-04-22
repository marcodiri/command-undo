@Test
public void testNewEditorCommand() {

    // create a new editor and write into it
    //with button
    app.click("NewEditorButton");
    app.write("Testo di prova");

    assertTrue(components.get(activeWindow.getActiveEditor().getId()) == activeWindow.getActiveEditor()); // the created editor gets saved by the CommandManager
    assertEquals("Testo di prova", activeWindow.getActiveEditor().getText());

    // with shortcut
    app.click("Ctrl+N");
    app.write("Un altro testo di prova");

    assertTrue(components.get(activeWindow.getActiveEditor().getId()) == activeWindow.getActiveEditor()); // the created editor gets saved by the CommandManager
    assertEquals("Un altro testo di prova", activeWindow.getActiveEditor().getText());

}