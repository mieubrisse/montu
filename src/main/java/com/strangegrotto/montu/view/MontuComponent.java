package com.strangegrotto.montu.view;

import com.googlecode.lanterna.gui2.Component;

import java.util.List;

public interface MontuComponent extends Component {
    GutterMarker getGutterMarker();

    List<String> getLines();
}
