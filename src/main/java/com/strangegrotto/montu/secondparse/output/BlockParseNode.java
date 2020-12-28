package com.strangegrotto.montu.secondparse.output;

import com.strangegrotto.montu.secondparse.ParseNodeToTextVisitor;
import org.commonmark.node.Node;

import java.util.List;

public class BlockParseNode implements ParseNode {
    private final Node blockNode;

    public BlockParseNode(Node blockNode) {
        this.blockNode = blockNode;
    }

    @Override
    public List<String> getLines() {
        var visitor = new ParseNodeToTextVisitor();
        this.blockNode.accept(visitor);
        var result = visitor.getRenderedText();
        return List.of(result);
    }
}
