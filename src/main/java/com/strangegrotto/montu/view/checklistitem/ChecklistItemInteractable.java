package com.strangegrotto.montu.view.checklistitem;

import com.google.common.base.Strings;
import com.googlecode.lanterna.gui2.AbstractInteractableComponent;
import com.googlecode.lanterna.gui2.InteractableRenderer;
import com.strangegrotto.montu.view.DisplayLinesFilter;
import com.strangegrotto.montu.view.MontuComponent;
import com.strangegrotto.montu.view.ShowAllLinesFilter;

import java.util.ArrayList;
import java.util.List;

public class ChecklistItemInteractable extends AbstractInteractableComponent<ChecklistItemInteractable> implements MontuComponent {
    private final int indentationLevel;
    private final ListMarker listMarker;
    private final List<String> lines;

    private boolean isChecked;
    private DisplayLinesFilter linesFilter;

    public ChecklistItemInteractable(int indentationLevel, ListMarker listMarker, boolean isChecked, List<String> lines) {
        this.indentationLevel = indentationLevel;
        this.listMarker = listMarker;
        this.isChecked = isChecked;
        this.lines = lines;
        this.linesFilter = new ShowAllLinesFilter();
    }

    @Override
    public int getIndentationLevel() {
        return this.indentationLevel;
    }

    @Override
    public List<String> getLines() {
        var checkboxCore = this.isChecked ? "x" : " ";
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

    @Override
    public DisplayLinesFilter getLinesFilter() {
        return this.linesFilter;
    }

    @Override
    public void setLinesFilter(DisplayLinesFilter filter) {
        this.linesFilter = filter;
    }

    public void setState(boolean isComplete) {
        this.isChecked = isComplete;
        this.invalidate();
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
