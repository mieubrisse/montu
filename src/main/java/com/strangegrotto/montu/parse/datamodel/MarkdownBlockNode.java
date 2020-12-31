package com.strangegrotto.montu.parse.datamodel;

import com.strangegrotto.montu.parse.render.BlockNodeRenderVisitor;
import com.strangegrotto.montu.parse.secondparse.SecondParseVisitor;
import org.commonmark.node.Node;

import java.awt.*;
import java.util.List;

// NOTE: These nodes are kind of funny - when they're a child of the top-level RootParseNode, they
//  need to be treated as standalone things producting their own components but when they're the child
//  of a checklist item they need to become the checklist items description
public class MarkdownBlockNode implements ParseNode {
    private final Node blockNode;

    public MarkdownBlockNode(Node blockNode) {
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

    @Override
    public void addBlockChild(MarkdownBlockNode node) {
        throw new IllegalStateException("Adding a block child to a markdown block node is nonsensical and should never happen");
    }

    @Override
    public void addChecklistItemChild(ChecklistItemParseNode node) {
        throw new IllegalStateException("Adding a checklist item child to a markdown block node is nonsensical and should never happen");
    }

    @Override
    public List<ParseNode> getChildren() {
        return List.of();

    }

    @Override
    public void accept(SecondParseVisitor visitor) {
        visitor.visit(this);
    }
}
