package com.strangegrotto.montu.parse.render;

import com.googlecode.lanterna.gui2.Component;
import org.commonmark.node.Block;

import java.util.List;

public class ChecklistItemInteractableProducer implements ComponentProducer {
    private final List<Block> markdownBlocks;

    public ChecklistItemInteractableProducer(List<Block> markdownBlocks) {
        this.markdownBlocks = markdownBlocks;
    }

    @Override
    public Component produceComponent() {
        // TODO
        return null;
    }
}
