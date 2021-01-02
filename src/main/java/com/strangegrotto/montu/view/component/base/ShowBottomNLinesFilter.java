package com.strangegrotto.montu.view.component.base;

import java.util.List;

public class ShowBottomNLinesFilter implements DisplayLinesFilter {
    private final int numLines;

    public ShowBottomNLinesFilter(int numLines) {
        this.numLines = numLines;
    }

    @Override
    public List<String> filterDisplayLines(List<String> input) {
        int startIndex = Math.max(0, input.size() - numLines);
        return input.subList(startIndex, input.size());
    }
}
