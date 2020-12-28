package com.strangegrotto.montu.secondparse;

import org.commonmark.node.*;

public class BlockParseNodeRenderVisitor extends AbstractVisitor {

    public BlockParseNodeRenderVisitor() {
        super();
    }

    @Override
    public void visit(BlockQuote blockQuote) {
        super.visit(blockQuote);
    }

    @Override
    public void visit(BulletList bulletList) {
        super.visit(bulletList);
    }

    @Override
    public void visit(Code code) {
        super.visit(code);
    }

    @Override
    public void visit(Document document) {
        super.visit(document);
    }

    @Override
    public void visit(Emphasis emphasis) {
        super.visit(emphasis);
    }

    @Override
    public void visit(FencedCodeBlock fencedCodeBlock) {
        super.visit(fencedCodeBlock);
    }

    @Override
    public void visit(HardLineBreak hardLineBreak) {
        super.visit(hardLineBreak);
    }

    @Override
    public void visit(Heading heading) {
        super.visit(heading);
    }

    @Override
    public void visit(ThematicBreak thematicBreak) {
        super.visit(thematicBreak);
    }

    @Override
    public void visit(HtmlInline htmlInline) {
        super.visit(htmlInline);
    }

    @Override
    public void visit(HtmlBlock htmlBlock) {
        super.visit(htmlBlock);
    }

    @Override
    public void visit(Image image) {
        super.visit(image);
    }

    @Override
    public void visit(IndentedCodeBlock indentedCodeBlock) {
        super.visit(indentedCodeBlock);
    }

    @Override
    public void visit(Link link) {
        super.visit(link);
    }

    @Override
    public void visit(ListItem listItem) {
        super.visit(listItem);
    }

    @Override
    public void visit(OrderedList orderedList) {
        super.visit(orderedList);
    }

    @Override
    public void visit(Paragraph paragraph) {
        super.visit(paragraph);
    }

    @Override
    public void visit(SoftLineBreak softLineBreak) {
        super.visit(softLineBreak);
    }

    @Override
    public void visit(StrongEmphasis strongEmphasis) {
        super.visit(strongEmphasis);
    }

    @Override
    public void visit(Text text) {
        super.visit(text);
    }

    @Override
    public void visit(LinkReferenceDefinition linkReferenceDefinition) {
        super.visit(linkReferenceDefinition);
    }

    @Override
    public void visit(CustomBlock customBlock) {
        super.visit(customBlock);
    }

    @Override
    public void visit(CustomNode customNode) {
        super.visit(customNode);
    }
}
