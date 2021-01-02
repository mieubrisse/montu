package com.strangegrotto.montu.view.checklistitem;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.gui2.InteractableRenderer;
import com.strangegrotto.montu.view.AbstractMontuComponentRenderer;

public class ChecklistItemInteractableRenderer
        extends AbstractMontuComponentRenderer<ChecklistItemInteractable>
        implements InteractableRenderer<ChecklistItemInteractable> {

    @Override
    public TerminalPosition getCursorLocation(ChecklistItemInteractable component) {
        if (!component.isVisible()) {
            return null;
        }

        var indentationStrLen = this.getIndentationStr(component).length();
        var checklistCoreOffset = component.getChecklistCoreOffset();
        return new TerminalPosition(indentationStrLen + checklistCoreOffset, 0);
    }
}
