package com.strangegrotto.montu.view;

import com.googlecode.lanterna.gui2.Component;

import java.util.List;

public interface MontuComponent extends Component {

    int getIndentationLevel();

    List<String> getLines();

    DisplayLinesFilter getLinesFilter();

    void setLinesFilter(DisplayLinesFilter filter);
}
