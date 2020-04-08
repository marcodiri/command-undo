import static org.junit.Assert.*;

import org.junit.Test;

import app.Application;

public class TestApp {

    private Application app;

    @Test
	public void testParagraph() {
		app = new Application();
        app.createEditor("Editor1").setText("Testo di prova");
		
	}

}