package com.strangegrotto.montu.secondparse.output;

import com.strangegrotto.montu.secondparse.BlockNodeRenderVisitor;
import org.commonmark.node.Node;

import java.awt.*;
import java.util.List;

public class BlockParseNode implements ParseNode {
    private final Node blockNode;

    public BlockParseNode(Node blockNode) {
        this.blockNode = blockNode;
    }

    @Override
    public List<Component> getComponents() {
        // TODO TODO
        return null;
    }

    @Override
    public List<String> getLines() {
        var visitor = new BlockNodeRenderVisitor();
        this.blockNode.accept(visitor);
        return visitor.getRenderedLines();
    }
}
