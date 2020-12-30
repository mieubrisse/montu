package com.strangegrotto.montu.secondparse.output;

import java.awt.*;
import java.util.List;

public interface ParseNode {
    // TODO function to get the Lanterna components

    List<Component> getComponents();

    // TODO kill this
    List<String> getLines();
}
