package com.strangegrotto.montu.model;

import java.util.Set;

public class RootCompletable implements Completable {
    private final Set<Completable> children;

    public RootCompletable() {
        this.children = Set.of();
    }

    @Override
    public void addChild(Completable child) {
        this.children.add(child);
    }

    @Override
    public void setCompletionStatus(boolean isComplete) {

    }
}
