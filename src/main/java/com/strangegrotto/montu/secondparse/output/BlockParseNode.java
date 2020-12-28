package com.strangegrotto.montu.secondparse.output;

import org.commonmark.node.Node;

public class BlockParseNode implements ParseNode {
    private final Node blockNode;

    public BlockParseNode(Node blockNode) {
        this.blockNode = blockNode;
    }
}
