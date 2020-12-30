package com.strangegrotto.montu.view;

import com.google.common.base.Strings;
import com.googlecode.lanterna.gui2.AbstractInteractableComponent;
import com.googlecode.lanterna.gui2.InteractableRenderer;

import java.util.ArrayList;
import java.util.List;

public class ChecklistItemInteractable extends AbstractInteractableComponent<ChecklistItemInteractable> implements MontuComponent {
    private final int indentationLevel;
    private final ListMarker listMarker;
    private boolean isComplete;
    private final List<String> lines;

    public ChecklistItemInteractable(int indentationLevel, ListMarker listMarker, boolean isComplete, List<String> lines) {
        this.indentationLevel = indentationLevel;
        this.listMarker = listMarker;
        this.isComplete = isComplete;
        this.lines = lines;
    }

    @Override
    public GutterMarker getGutterMarker() {
        return this.isFocused() ? GutterMarker.ARROW : GutterMarker.BLANK;
    }

    @Override
    public List<String> getLines() {
        var checkboxCore = this.isComplete ? "x" : " ";
        var firstLinePrefix = listMarker.getMarker() + " [" + checkboxCore + "] ";

        var result = new ArrayList<String>(this.lines.size());
        for (int i = 0; i < this.lines.size(); i++) {
            var prefix = Strings.repeat(" ", firstLinePrefix.length());
            if (i == 0) {
                prefix = firstLinePrefix;
            }

            var rawLine = this.lines.get(i);
            result.add(prefix + rawLine);
        }
        return result;
    }

    public void setCompletionStatus(boolean isComplete) {
        this.isComplete = isComplete;
        this.invalidate();
    }

    @Override
    protected InteractableRenderer<ChecklistItemInteractable> createDefaultRenderer() {
        return new ChecklistItemInteractableRenderer(this.indentationLevel);

    }
}
