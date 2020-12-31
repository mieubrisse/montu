package com.strangegrotto.montu.parse.render;
import com.google.common.base.Strings;
import org.commonmark.node.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Visitor to render a tree, rooted at a {@link Block} node, into a list of strings, one per line
 */
public class BlockNodeRenderVisitor extends AbstractVisitor {
    // TODO Switch this to an optional, which is safer
    // Will be empty when not inside a block
    private StringBuilder resultBuilder;

    private List<String> lines;

    public BlockNodeRenderVisitor() {
        this.resultBuilder = new StringBuilder();
        this.lines = new ArrayList<>();
    }

    public List<String> getRenderedLines() {
        return this.lines;
    }

    // =============================================================================================================================
    //                                    Nodes that this renderer will handle
    // =============================================================================================================================
    @Override
    public void visit(BlockQuote blockQuote) {
        throw new BlockNodeRenderException("Not implemented yet");
    }

    @Override
    public void visit(Code code) {
        throw new BlockNodeRenderException("Not implemented yet");
    }

    @Override
    public void visit(Emphasis emphasis) {
        this.resultBuilder.append(emphasis.getOpeningDelimiter());

        this.visitChildren(emphasis);

        this.resultBuilder.append(emphasis.getClosingDelimiter());
    }

    @Override
    public void visit(FencedCodeBlock fencedCodeBlock) {
        throw new BlockNodeRenderException("Not implemented yet");
    }

    @Override
    public void visit(HardLineBreak hardLineBreak) {
        throw new BlockNodeRenderException("Not implemented yet");
    }

    @Override
    public void visit(Heading heading) {
        this.lines.add("");
        var resultBuilder = new StringBuilder();
        var prefix = Strings.repeat("#", heading.getLevel());
        resultBuilder.append(prefix + " ");

        this.resultBuilder = resultBuilder;
        this.visitChildren(heading);
        var finishedStr = this.resultBuilder.toString();

        this.lines.add(finishedStr);
        this.resultBuilder = null;  // TODO use an optional, rather than this jankiness
    }

    @Override
    public void visit(ThematicBreak thematicBreak) {
        throw new BlockNodeRenderException("Not implemented yet");
    }

    @Override
    public void visit(HtmlInline htmlInline) {
        throw new BlockNodeRenderException("Not implemented yet");
    }

    @Override
    public void visit(HtmlBlock htmlBlock) {
        throw new BlockNodeRenderException("Not implemented yet");
    }

    @Override
    public void visit(Image image) {
        throw new BlockNodeRenderException("Not implemented yet");
    }

    @Override
    public void visit(IndentedCodeBlock indentedCodeBlock) {
        throw new BlockNodeRenderException("Not implemented yet");
    }

    @Override
    public void visit(Link link) {
        // TODO link.getTitle() is always null - probably something to do with destination ref links
        this.resultBuilder.append("[");
        this.visitChildren(link);
        this.resultBuilder.append("](" + link.getDestination() + ")");
    }

    @Override
    public void visit(Paragraph paragraph) {
        this.lines.add("");
        var resultBuilder = new StringBuilder();

        this.resultBuilder = resultBuilder;
        this.visitChildren(paragraph);
        var finishedStr = this.resultBuilder.toString();

        this.lines.add(finishedStr);
        this.lines.add("");
        this.resultBuilder = null;  // TODO use an optional, rather than this jankiness
    }

    @Override
    public void visit(SoftLineBreak softLineBreak) {
        throw new BlockNodeRenderException("Not implemented yet");
    }

    @Override
    public void visit(StrongEmphasis strongEmphasis) {
        this.resultBuilder.append(strongEmphasis.getOpeningDelimiter());

        this.visitChildren(strongEmphasis);

        this.resultBuilder.append(strongEmphasis.getClosingDelimiter());
    }

    @Override
    public void visit(Text text) {
        this.resultBuilder.append(text.getLiteral());
    }

    @Override
    public void visit(LinkReferenceDefinition linkReferenceDefinition) {
        throw new BlockNodeRenderException("Not implemented yet");
    }

    @Override
    public void visit(CustomBlock customBlock) {
        throw new BlockNodeRenderException("Not implemented yet");
    }

    @Override
    public void visit(CustomNode customNode) {
        throw new BlockNodeRenderException("Not implemented yet");
    }

    // =============================================================================================================================
    //                   Nodes that we should never see due to the parsing that occurred earlier
    // =============================================================================================================================
    @Override
    public void visit(Document document) {
        throw new BlockNodeRenderException("Parse node renderer should never see the document becasue only the intermediate parser will use it");
    }

    @Override
    public void visit(BulletList bulletList) {
        throw new BlockNodeRenderException("Parse node renderer should never see bullet lists because they should have been processed by the intermediate parser");
    }

    @Override
    public void visit(OrderedList orderedList) {
        throw new BlockNodeRenderException("Parse node renderer should never see ordered lists because they should have been processed by the intermediate parser");
    }

    @Override
    public void visit(ListItem listItem) {
        throw new BlockNodeRenderException("Parse node renderer should never see list items because they should have been processed by the intermediate parser");
    }
}
