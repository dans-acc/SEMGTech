package com.semgtech.display.ui.panes;

import javax.swing.*;
import javax.swing.text.Style;

public class ViewerConsoleTextPane extends JTextArea
{

    public ViewerConsoleTextPane()
    {
        setEditable(false);
    }

    public synchronized void displayMessage(final String message)
    {
        append(message);
    }

}
