package com.strangegrotto.montu.parse.render;

import com.google.common.collect.ImmutableList;
import org.commonmark.node.Block;

import java.util.ArrayList;
import java.util.List;

public class MultipleBlockNodeRenderer {
    // TODO Make this not a utils class!!!!!
    private MultipleBlockNodeRenderer() {}

    public static List<String> render(List<Block> blocks) {
        var resultBuilder = ImmutableList.<String>builder();
        for (int i = 0; i < blocks.size(); i++) {
            var block = blocks.get(i);
            var visitor = new BlockNodeRenderVisitor();
            block.accept(visitor);

            // Attempt to intelligently space out blocks
            if (i != 0) {
                resultBuilder.add("");
            }

            for (var renderedLine : visitor.getRenderedLines()) {
                resultBuilder.add(renderedLine);
            }
        }
        return resultBuilder.build();
    }
}
