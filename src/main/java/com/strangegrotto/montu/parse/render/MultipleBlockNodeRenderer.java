package com.strangegrotto.montu.parse.render;

import org.commonmark.node.Block;

import java.util.List;

public class MultipleBlockNodeRenderer {
    // TODO Make this not a utils class!!!!!
    private MultipleBlockNodeRenderer() {}

    public static List<String> render(List<Block> blocks) {
        List<String> result = List.of();
        for (var block : blocks) {
            var visitor = new BlockNodeRenderVisitor();
            block.accept(visitor);
            // TODO put intelligent spacing between blocks
            for (var renderedLine : visitor.getRenderedLines()) {
                result.add(renderedLine);
            }
        }
        return result;
    }
}
