package com.strangegrotto.montu.parse.render;

import com.googlecode.lanterna.gui2.Component;
import org.commonmark.node.Block;
import org.commonmark.node.Node;

public class TextComponentProducer implements ComponentProducer {
    private final Block block;

    public TextComponentProducer(Block block) {
        this.block = block;
    }

    @Override
    public Component produceComponent() {
        // TODO
        return null;
    }
}
