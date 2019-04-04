package com.semgtech.display.ui.panels;

import javax.swing.*;

public abstract class SideView<T> extends JPanel
{

    private T object;

    public SideView(final T object)
    {
        this.object = object;
    }

    public T getObject()
    {
        return object;
    }
}
