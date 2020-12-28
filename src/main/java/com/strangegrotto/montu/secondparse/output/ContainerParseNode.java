package com.strangegrotto.montu.secondparse.output;

import java.util.ArrayList;
import java.util.List;

public abstract class ContainerParseNode implements ParseNode {
    // private final List<>

    private final List<ParseNode> children;

    public ContainerParseNode() {
        this.children = new ArrayList<>();
    }

    public void addChild(ParseNode node) {
        this.children.add(node);
    }
}
