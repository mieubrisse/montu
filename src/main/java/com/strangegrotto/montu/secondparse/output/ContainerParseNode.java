package com.strangegrotto.montu.secondparse.output;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public interface ContainerParseNode extends ParseNode {
    // private final List<>

    void addBlockChild(BlockParseNode node);

    void addChecklistItemChild(ChecklistItemParseNode node);
}
