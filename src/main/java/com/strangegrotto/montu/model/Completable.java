package com.strangegrotto.montu.model;

public interface Completable {
    void addChild(Completable child);

    void setCompletionStatus(boolean isComplete);
}
