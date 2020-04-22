@Test
public void testCutCommand() {

    // create a new editor and write into it
    app.click("NewEditorButton");
    app.write("Testo di prova");

    // cut some text from editor1
    // with button
    app.selectText(5, 3, true);
    app.click("CutButton");

    assertTrue(history.size() == 1); // the command gets requested a snapshot
    assertTrue(history.clone().pop() instanceof EditorMemento); // clone history to not change app state
    assertEquals(" di", activeWindow.clipboard);
    assertEquals("Testo prova", activeWindow.getActiveEditor().getText());
    
    // with shortcut
    app.selectText(5, -1, true);
    app.click("Ctrl+X");

    assertTrue(history.size() == 2);
    assertTrue(history.clone().pop() instanceof EditorMemento);
    assertEquals(" prova", activeWindow.clipboard);
    assertEquals("Testo", activeWindow.getActiveEditor().getText());
        
}