@Test
public void testCommandPatern() {
    
    // different components have the same command
    Component newEditorButton = components.get(nameToId.get("NewEditorButton"));
    Component newEditorShortcut = components.get(nameToId.get("Ctrl+N"));

    assertTrue(newEditorButton instanceof Button);
    assertTrue(newEditorShortcut instanceof Shortcut);

    Command newEditorButtonCommand = newEditorButton.getCommand();
    Command newEditorShortcutCommand = newEditorShortcut.getCommand();
    
    // both button and shortcut refer to the same command
    // N.B. "the same" read as the exact same object (commands objects are shared)
    assertTrue(newEditorButtonCommand instanceof NewEditorCommand);
    assertTrue(newEditorButtonCommand == newEditorShortcutCommand);

}