package com.strangegrotto.montu.view;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.gui2.InteractableRenderer;

public class ChecklistItemInteractableRenderer
        extends AbstractMontuComponentRenderer<ChecklistItemInteractable>
        implements InteractableRenderer<ChecklistItemInteractable> {

    public ChecklistItemInteractableRenderer(int indentationLevel) {
        super(indentationLevel);
    }

    @Override
    public TerminalPosition getCursorLocation(ChecklistItemInteractable component) {
        // TODO show the cursor location over the checkbox???
        return null;
    }
}
