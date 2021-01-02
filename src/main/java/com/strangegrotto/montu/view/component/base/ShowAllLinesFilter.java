package com.strangegrotto.montu.view.component.base;

import java.util.List;

public class ShowAllLinesFilter implements DisplayLinesFilter {
    @Override
    public List<String> filterDisplayLines(List<String> input) {
        return input;
    }
}
