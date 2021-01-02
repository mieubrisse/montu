package com.strangegrotto.montu.view;

import com.googlecode.lanterna.gui2.AbstractWindow;
import com.googlecode.lanterna.gui2.Interactable;
import com.googlecode.lanterna.input.KeyStroke;
import com.strangegrotto.montu.controller.InputHandler;

public class InputHandlingDelegatorWindow extends AbstractWindow {
    private final InputHandler delegate;

    public InputHandlingDelegatorWindow(InputHandler delegate) {
        this.delegate = delegate;
    }

    @Override
    public boolean handleInput(KeyStroke key) {
        return this.delegate.handleInput(key);
    }
}
