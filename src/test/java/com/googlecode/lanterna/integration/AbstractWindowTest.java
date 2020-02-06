package com.googlecode.lanterna.integration;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFrame;
import org.junit.After;
import org.junit.Before;

public abstract class AbstractWindowTest {

    protected BasicWindow basicWindow;
    protected SwingTerminalFrame terminal;
    protected Screen screen;
    protected MultiWindowTextGUI textGUI;
    protected AsynchronousTextGUIThread guiThread;

    @Before
    public void before() throws Exception {
        terminal = (SwingTerminalFrame)new DefaultTerminalFactory().createTerminal();
        screen = new TerminalScreen(terminal);
        screen.startScreen();
        textGUI = new MultiWindowTextGUI(new SeparateTextGUIThread.Factory(), screen);

        textGUI.setBlockingIO(false);
        textGUI.setEOFWhenNoWindows(true);
        textGUI.isEOFWhenNoWindows();

        basicWindow = buildWindow();

        textGUI.addWindow(basicWindow);

        guiThread = (AsynchronousTextGUIThread) textGUI.getGUIThread();
        guiThread.start();
    }

    @After
    public void after() throws Exception {
        screen.stopScreen();
        screen.close();
        terminal.close();
    }

    public abstract BasicWindow buildWindow();
}
