package com.strangegrotto.montu.controller;

import com.googlecode.lanterna.gui2.Interactable;
import com.googlecode.lanterna.input.KeyStroke;

public interface InputHandler {
    // Returns true if the input was handled, and false if not
    boolean handleInput(KeyStroke stroke);

}
