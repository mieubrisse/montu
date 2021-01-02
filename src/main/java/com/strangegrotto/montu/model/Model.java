package com.strangegrotto.montu.model;

import com.google.common.base.Preconditions;
import com.googlecode.lanterna.gui2.Interactable;
import com.strangegrotto.montu.view.View;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class Model {
    // The number of items to jump when jumping a page
    private static final int PAGE_SKIP_NUM_ITEMS = 10;

    private final View view;
    private final List<Boolean> isItemCheckedArr;
    private Optional<Integer> cursorIndexOpt;

    public Model(View view, List<Boolean> isItemCheckedArr) {
        Preconditions.checkState(
                view.getNumChecklistItems() == isItemCheckedArr.size(),
                "Number of checklist interactables in view must == number of boolean slots in model"
        );
        this.view = view;
        this.isItemCheckedArr = isItemCheckedArr;
        this.cursorIndexOpt = Optional.empty();
    }

    public void moveCursorDownOneItem() {
        this.moveCursor(1);
    }

    public void moveCursorUpOneItem() {
        this.moveCursor(-1);
    }

    public void moveCursorDownOnePage() {
        this.moveCursor(PAGE_SKIP_NUM_ITEMS);
    }

    public void moveCursorUpOnePage() {
        this.moveCursor(-PAGE_SKIP_NUM_ITEMS);
    }

    private void moveCursor(int delta) {
        // If no elements, we can never move the cursor
        if (this.isItemCheckedArr.size() == 0) {
             return;
        }

        var maxIndex = this.isItemCheckedArr.size() - 1;

        // If we have elements but haven't selected one yet, only start doing so on a positive movement
        int newIndex;
        if (!this.cursorIndexOpt.isPresent()) {
            if (delta <= 0) {
                return;
            }
            // Use -1 as the "current index", so that a single move-down motion will end up on the first item
            newIndex = Math.min(-1 + delta, maxIndex);
        } else {
            var currentIdx = this.cursorIndexOpt.get();
            if (delta <= 0) {
                newIndex = Math.max(0, currentIdx + delta);
            } else {
                newIndex = Math.min(maxIndex, currentIdx + delta);
            }
        }
        this.cursorIndexOpt = Optional.of(newIndex);
        this.view.focusChecklistItem(newIndex);
    }
}
