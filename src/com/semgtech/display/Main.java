package com.semgtech.display;

import com.semgtech.display.windows.MainWindow;

import javax.swing.*;

public class Main
{

    public static void main(String... args)
    {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainWindow.getInstance();
            }
        });
    }

}
