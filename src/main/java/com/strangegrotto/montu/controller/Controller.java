package com.strangegrotto.montu.controller;

import com.googlecode.lanterna.gui2.Interactable;
import com.googlecode.lanterna.input.KeyStroke;
import com.strangegrotto.montu.model.Model;

public class Controller implements InputHandler {
    private static final char SCROLL_DOWN_ONE_ITEM_CHAR = 'j';
    private static final char SCROLL_UP_ONE_ITEM_CHAR = 'k';
    private static final char SCROLL_DOWN_ONE_PAGE_CHAR = 'J';
    private static final char SCROLL_UP_ONE_PAGE_CHAR = 'K';

    private final Model model;

    public Controller(Model model) {
        this.model = model;
    }

    public boolean handleInput(KeyStroke stroke) {
        if (stroke.isAltDown() || stroke.isCtrlDown()) {
            return true;
        }

        switch (stroke.getCharacter()) {
            case SCROLL_DOWN_ONE_ITEM_CHAR:
                this.model.moveCursorDownOneItem();
                return true;
            case SCROLL_UP_ONE_ITEM_CHAR:
                this.model.moveCursorUpOneItem();
                return true;
            case SCROLL_DOWN_ONE_PAGE_CHAR:
                this.model.moveCursorDownOnePage();
                return true;
            case SCROLL_UP_ONE_PAGE_CHAR:
                this.model.moveCursorUpOnePage();
                return true;
            default:
                return false;
        }
    }
}
