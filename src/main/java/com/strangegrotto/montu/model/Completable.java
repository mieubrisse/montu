package com.strangegrotto.montu.model;

import com.strangegrotto.montu.view.ChecklistItemInteractable;

import java.util.Set;

public class Completable {
    private final Completable parent;
    private final ChecklistItemInteractable visualization;
    private boolean isComplete;
    private Set<Completable> children;

    public Completable(Completable parent, ChecklistItemInteractable visualization, boolean isComplete) {
        this.parent = parent;
        this.visualization = visualization;
        this.isComplete = isComplete;
        this.children = Set.of();
    }

    public void addChild(Completable child) {
        this.children.add(child);
    }

    public void setCompletionStatus(boolean isComplete) {
        if (isComplete == this.isComplete) {
            return;
        }

        for (Completable child : this.children) {
            child.setCompletionStatus(isComplete);
        }
        this.visualization.setCompletionStatus(isComplete);
        this.isComplete = isComplete;
    }
}
