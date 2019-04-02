package com.semgtech.display.ui.panes;

import com.semgtech.api.simulation.anatomy.Fibre;
import com.semgtech.api.utils.signals.Signal;
import com.semgtech.api.utils.vector.Vector3d;

import javax.swing.*;

public class ConsoleTextPane extends JTextArea
{

    private static final String CONSOLE_TEXT_PANE_TOOLTIP = "Console outputs representing various properties relating to Signals.";

    public ConsoleTextPane()
    {
        initConsoleTextPane();
    }

    private void initConsoleTextPane()
    {
        setEditable(false);
        setToolTipText(CONSOLE_TEXT_PANE_TOOLTIP);
    }

    public synchronized void displayObjectInformation(final Object object)
    {

    }
}
