package com.strangegrotto.montu.view;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.gui2.InteractableRenderer;

public class ChecklistItemInteractableRenderer
        extends AbstractMontuComponentRenderer<ChecklistItemInteractable>
        implements InteractableRenderer<ChecklistItemInteractable> {

    @Override
    public TerminalPosition getCursorLocation(ChecklistItemInteractable component) {
        var gutterIndentationStrLen = this.getPrefixStr(0, component).length();
        var checklistCoreOffset = component.getChecklistCoreOffset();
        return new TerminalPosition(gutterIndentationStrLen + checklistCoreOffset, 0);
    }
}
