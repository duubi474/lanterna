package com.googlecode.lanterna.integration;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.menu.Menu;
import com.googlecode.lanterna.gui2.menu.MenuBar;
import com.googlecode.lanterna.gui2.menu.MenuItem;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class WorkingUpKeyMenuTest extends AbstractWindowTest {

    private static final int SIMULATED_USER_TRHEAD_SLEEP_TIME = 10000;

    private TextBox textBox;

    @Test
    public void testKeyUp() throws InterruptedException {
        Thread thread = new Thread(() -> {
            terminal.addInput(new KeyStroke(KeyType.ArrowDown));
            terminal.addInput(new KeyStroke(KeyType.ArrowDown));
            terminal.addInput(new KeyStroke(KeyType.ArrowUp));
            terminal.addInput(new KeyStroke(KeyType.Backspace));
            terminal.addInput(new KeyStroke('F', false, false));
            terminal.addInput(new KeyStroke(KeyType.ArrowUp));
            try {
                Thread.sleep(SIMULATED_USER_TRHEAD_SLEEP_TIME);
            } catch ( InterruptedException interruptedException ) {
                fail();
            }
            guiThread.stop();
        });
        thread.start();
        guiThread.waitForStop();

        assertEquals("F", textBox.getText());
    }

    @Override
    public BasicWindow buildWindow() {
        MenuBar menuBar = new MenuBar();
        menuBar.add(new Menu("Menu 1").add(new MenuItem("MenuItem 1.1")));

        BasicWindow basicWindow = new BasicWindow();

        textBox = new TextBox("A");

        Panel mainPanel = new Panel(new LinearLayout(Direction.VERTICAL));
        mainPanel.addComponent(new Label("Label just to fill some space"));
        mainPanel.addComponent(textBox);
        mainPanel.addComponent(new Button("Quit", basicWindow::close));

        basicWindow.setComponent(mainPanel);
        basicWindow.setMenuBar(menuBar);
        return basicWindow;
    }
}
