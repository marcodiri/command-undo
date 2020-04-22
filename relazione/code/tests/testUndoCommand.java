@Test
public void testUndoCommand() {
    
    testCutCommand();

    // undo the cut command
    // with button
    app.click("UndoButton");

    // the last CUT command gets reverted
    assertTrue(history.size() == 1);
    assertEquals("Testo prova", activeWindow.getActiveEditor().getText());

    // with shortcut
    app.click("Ctrl+Z");

    // the first CUT command gets reverted
    assertTrue(history.size() == 0);
    assertEquals("Testo di prova", activeWindow.getActiveEditor().getText());
    
}