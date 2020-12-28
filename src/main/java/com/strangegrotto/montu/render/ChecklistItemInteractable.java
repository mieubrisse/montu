package com.strangegrotto.montu.render;

import com.googlecode.lanterna.gui2.AbstractInteractableComponent;
import com.googlecode.lanterna.gui2.InteractableRenderer;
import com.strangegrotto.montu.ChecklistItemInteractableRenderer;

public class ChecklistItemInteractable extends AbstractInteractableComponent {
    // If 0, this is a parent-level item; if 1, there is one checklist item above, etc.
    private final int nestLevel;

    // TODO Replace this with a proper class
    // "*", "-", "1.", "2.", etc.
    private final String ordenalString;

    // TODO move these to the datamodel
    private final boolean isChecked;
    private final String text;

    public ChecklistItemInteractable(int nestLevel, String ordenalString, boolean isChecked, String text) {
        this.nestLevel = nestLevel;
        this.ordenalString = ordenalString;
        this.isChecked = isChecked;
        this.text = text;
    }

    @Override
    protected InteractableRenderer createDefaultRenderer() {
        return new ChecklistItemInteractableRenderer();
    }

    public int getNestLevel() {
        return nestLevel;
    }

    public String getOrdenalString() {
        return ordenalString;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public String getText() {
        return text;
    }
}
