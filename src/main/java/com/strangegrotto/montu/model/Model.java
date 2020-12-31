package com.strangegrotto.montu.model;

public class Model {
    private final ChildCompletable entireChecklist;
    private int focusedCompletableIndex;    // Index into the entire checklist's children

    public Model(ChildCompletable entireChecklist) {
        this.entireChecklist = entireChecklist;
    }

    public void moveFocusUpOne() {
        var newIndex = Math.max(0, this.focusedCompletableIndex - 1);
        this.focusedCompletableIndex = newIndex;
    }

    // TODO
}
