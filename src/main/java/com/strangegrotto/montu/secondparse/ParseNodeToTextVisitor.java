package com.strangegrotto.montu.secondparse;
import com.google.common.base.Strings;
import org.commonmark.node.*;

public class ParseNodeToTextVisitor extends AbstractVisitor {
    private StringBuilder resultBuilder;

    public ParseNodeToTextVisitor() {
        this.resultBuilder = new StringBuilder();
    }

    public String getRenderedText() {
        return this.resultBuilder.toString();
    }

    // =============================================================================================================================
    //                                    Nodes that this renderer will handle
    // =============================================================================================================================
    @Override
    public void visit(BlockQuote blockQuote) {
        throw new ParseNodeToTextException("Not implemented yet");
    }

    @Override
    public void visit(Code code) {
        throw new ParseNodeToTextException("Not implemented yet");
    }

    @Override
    public void visit(Emphasis emphasis) {
        this.resultBuilder.append(emphasis.getOpeningDelimiter());

        this.visitChildren(emphasis);

        this.resultBuilder.append(emphasis.getClosingDelimiter());
    }

    @Override
    public void visit(FencedCodeBlock fencedCodeBlock) {
        throw new ParseNodeToTextException("Not implemented yet");
    }

    @Override
    public void visit(HardLineBreak hardLineBreak) {
        throw new ParseNodeToTextException("Not implemented yet");
    }

    @Override
    public void visit(Heading heading) {
        this.resultBuilder.append('\n');
        var prefix = Strings.repeat("#", heading.getLevel());
        this.resultBuilder.append(prefix + " ");
        this.visitChildren(heading);
        this.resultBuilder.append('\n');
    }

    @Override
    public void visit(ThematicBreak thematicBreak) {
        throw new ParseNodeToTextException("Not implemented yet");
    }

    @Override
    public void visit(HtmlInline htmlInline) {
        throw new ParseNodeToTextException("Not implemented yet");
    }

    @Override
    public void visit(HtmlBlock htmlBlock) {
        throw new ParseNodeToTextException("Not implemented yet");
    }

    @Override
    public void visit(Image image) {
        throw new ParseNodeToTextException("Not implemented yet");
    }

    @Override
    public void visit(IndentedCodeBlock indentedCodeBlock) {
        throw new ParseNodeToTextException("Not implemented yet");
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
        // TODO if the sibling before this was also a paragraph, add an extra newline
        this.visitChildren(paragraph);
        this.resultBuilder.append('\n');
    }

    @Override
    public void visit(SoftLineBreak softLineBreak) {
        throw new ParseNodeToTextException("Not implemented yet");
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
        throw new ParseNodeToTextException("Not implemented yet");
    }

    @Override
    public void visit(CustomBlock customBlock) {
        throw new ParseNodeToTextException("Not implemented yet");
    }

    @Override
    public void visit(CustomNode customNode) {
        throw new ParseNodeToTextException("Not implemented yet");
    }

    // =============================================================================================================================
    //                   Nodes that we should never see due to the parsing that occurred earlier
    // =============================================================================================================================
    @Override
    public void visit(Document document) {
        throw new ParseNodeToTextException("Parse node renderer should never see the document becasue only the intermediate parser will use it");
    }

    @Override
    public void visit(BulletList bulletList) {
        throw new ParseNodeToTextException("Parse node renderer should never see bullet lists because they should have been processed by the intermediate parser");
    }

    @Override
    public void visit(OrderedList orderedList) {
        throw new ParseNodeToTextException("Parse node renderer should never see ordered lists because they should have been processed by the intermediate parser");
    }

    @Override
    public void visit(ListItem listItem) {
        throw new ParseNodeToTextException("Parse node renderer should never see list items because they should have been processed by the intermediate parser");
    }
}
