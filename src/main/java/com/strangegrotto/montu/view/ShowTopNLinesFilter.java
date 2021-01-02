package com.strangegrotto.montu.view;

import java.util.List;

public class ShowTopNLinesFilter implements DisplayLinesFilter {
    private final int numLines;

    public ShowTopNLinesFilter(int numLines) {
        this.numLines = numLines;
    }

    @Override
    public List<String> filterDisplayLines(List<String> input) {
        int safeNumLines = Math.min(numLines, input.size());
        return input.subList(0, safeNumLines);
    }
}
