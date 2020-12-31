package com.strangegrotto.montu.parse.datamodel;

import com.strangegrotto.montu.parse.secondparse.SecondParseVisitor;

import java.awt.*;
import java.util.List;

public interface ParseNode {
    void addBlockChild(MarkdownBlockNode node);

    void addChecklistItemChild(ChecklistItemParseNode node);

    List<ParseNode> getChildren();

    void accept(SecondParseVisitor visitor);
}
