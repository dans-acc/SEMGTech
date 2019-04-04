package com.semgtech.display;

import com.semgtech.display.windows.MainWindow;

import javax.swing.*;

public class Main
{

    public static void main(String... args)
    {
        // Set the look and feel of the ui to that of the systems
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Run the ui within a different thread to avoid concurrency related issues
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainWindow.getInstance();
            }
        });
    }

}
