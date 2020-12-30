package com.strangegrotto.montu.secondparse.output;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RootParseNode implements ContainerParseNode {
    private final List<ParseNode> children;

    public RootParseNode() {
        this.children = new ArrayList<>();
    }

    @Override
    public List<String> getLines() {
        var result = new ArrayList<String>();

        for (var node : children) {
            var lines = node.getLines();
            for (var line : lines) {
                result.add(line);
            }
        }
        return result;
    }

    @Override
    public List<Component> getComponents() {
        // TODO
        return null;
    }

    @Override
    public void addBlockChild(BlockParseNode node) {
        this.children.add(node);
    }

    @Override
    public void addChecklistItemChild(ChecklistItemParseNode node) {
        this.children.add(node);
    }
}
