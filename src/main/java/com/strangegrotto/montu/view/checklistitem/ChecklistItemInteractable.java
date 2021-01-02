package com.strangegrotto.montu.view.checklistitem;

import com.google.common.base.Strings;
import com.googlecode.lanterna.gui2.AbstractInteractableComponent;
import com.googlecode.lanterna.gui2.InteractableRenderer;
import com.googlecode.lanterna.input.KeyStroke;
import com.strangegrotto.montu.view.MontuComponent;

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
    public int getIndentationLevel() {
        return this.indentationLevel;
    }

    @Override
    public List<String> getLines() {
        var checkboxCore = this.isComplete ? "x" : " ";
        var stringInFrontOfCheckboxCore = getStringInFrontOfCheckboxCore();
        var firstLinePrefix = stringInFrontOfCheckboxCore + checkboxCore + "] ";

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

    // On the first line, the number of characters into the string that the checklist core is
    int getChecklistCoreOffset() {
        return getStringInFrontOfCheckboxCore().length();
    }

    @Override
    protected InteractableRenderer<ChecklistItemInteractable> createDefaultRenderer() {
        return new ChecklistItemInteractableRenderer();
    }

    private String getStringInFrontOfCheckboxCore() {
        return listMarker.getMarker() + " [";
    }
}
