package com.strangegrotto.montu.controller;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.strangegrotto.montu.model.Model;

public class Controller implements InputHandler {
    private static final char SCROLL_DOWN_ONE_ITEM_CHAR = 'j';
    private static final char SCROLL_UP_ONE_ITEM_CHAR = 'k';
    private static final char SCROLL_DOWN_ONE_PAGE_CHAR = 'J';
    private static final char SCROLL_UP_ONE_PAGE_CHAR = 'K';
    private static final char TOGGLE_COMPLETION_CHAR = 'x';
    private static final char CENTER_SCREEN_CHAR = 'z';

    private final Model model;

    public Controller(Model model) {
        this.model = model;
    }

    public boolean handleInput(KeyStroke stroke) {
        if (stroke.isAltDown() || stroke.isCtrlDown()) {
            return true;
        }

        if (stroke.getKeyType() != KeyType.Character) {
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
            case TOGGLE_COMPLETION_CHAR:
                this.model.toggleItemCompletion();
                return true;
            case CENTER_SCREEN_CHAR:
                this.model.centerOnCursor();
                return true;
            default:
                return false;
        }
    }
}
