package com.strangegrotto.montu.parse.datamodel;

import java.awt.*;
import java.util.List;

public interface ParseNode {
    // TODO function to get the Lanterna components

    List<Component> getComponents();

    // TODO kill this
    List<String> getLines();

    void addBlockChild(MarkdownBlockNode node);

    void addChecklistItemChild(ChecklistItemParseNode node);

    List<ParseNode> getChildren();
}
