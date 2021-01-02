package com.strangegrotto.montu.view.component.base;

import java.util.List;

public class ShowNoLinesFilter implements DisplayLinesFilter {
    @Override
    public List<String> filterDisplayLines(List<String> input) {
        return List.of();
    }
}
